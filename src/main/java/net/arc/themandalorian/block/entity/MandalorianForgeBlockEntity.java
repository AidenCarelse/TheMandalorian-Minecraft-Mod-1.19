package net.arc.themandalorian.block.entity;

import net.arc.themandalorian.item.ModItems;
import net.arc.themandalorian.networking.ModMessages;
import net.arc.themandalorian.networking.packet.FluidSyncS2CPacket;
import net.arc.themandalorian.networking.packet.ItemStackSyncS2CPacket;
import net.arc.themandalorian.recipe.MandalorianForgeRecipe;
import net.arc.themandalorian.screen.MandalorianForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MandalorianForgeBlockEntity extends BlockEntity implements MenuProvider
{
    private final ItemStackHandler itemHandler = new ItemStackHandler(3)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                //case 0 -> stack.getItem() == Items.LAVA_BUCKET;
                case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 1 -> stack.getItem() == ModItems.BESKAR_BAR.get();
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(1000)
    {
        @Override
        protected void onContentsChanged()
        {
            setChanged();
            if(!level.isClientSide())
            {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack)
        {
            return stack.getFluid() == Fluids.LAVA;
        }
    };

    public void setFluid(FluidStack stack)
    {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack()
    {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected  final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    public MandalorianForgeBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.MANDALORIAN_FORGE.get(), pos, state);
        this.data = new ContainerData()
        {
            @Override
            public int get(int index)
            {
                return switch (index)
                {
                    case 0 -> MandalorianForgeBlockEntity.this.progress;
                    case 1 -> MandalorianForgeBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value)
            {
                switch (index)
                {
                    case 0 -> MandalorianForgeBlockEntity.this.progress = value;
                    case 1 -> MandalorianForgeBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount()
            {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("Mandalorian Forge");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));

        return new MandalorianForgeMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if(cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return lazyItemHandler.cast();
        }

        if(cap == ForgeCapabilities.FLUID_HANDLER)
        {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt)
    {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("mandalorian_forge.progress", this.progress);
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt)
    {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("mandalorian_forge.prorgess");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i=0; i<itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, MandalorianForgeBlockEntity pEntity)
    {
        if(level.isClientSide())
        {
            return;
        }

        if(hasRecipe(pEntity))
        {
            pEntity.progress++;
            setChanged(level, blockPos, blockState);

            if(pEntity.progress >= pEntity.maxProgress)
            {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, blockPos, blockState);
        }

        if(hasLavaBucketInSourceSlot(pEntity))
        {
            transferLavaToFluidTank(pEntity);
        }
    }

    private static boolean hasEnoughFluid(MandalorianForgeBlockEntity pEntity)
    {
        return  pEntity.FLUID_TANK.getFluidAmount() >= 200;
    }

    private static void transferLavaToFluidTank(MandalorianForgeBlockEntity pEntity)
    {
        pEntity.itemHandler.getStackInSlot(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler ->
        {
            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(pEntity.FLUID_TANK.isFluidValid(stack))
            {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithLava(pEntity, stack, handler.getContainer());
            }
        });
    }

    private static void fillTankWithLava(MandalorianForgeBlockEntity pEntity, FluidStack stack, ItemStack container)
    {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    private static boolean hasLavaBucketInSourceSlot(MandalorianForgeBlockEntity pEntity)
    {
        return pEntity.itemHandler.getStackInSlot(0).getCount() > 0;
    }

    private void resetProgress()
    {
        this.progress = 0;
    }

    private static void craftItem(MandalorianForgeBlockEntity pEntity)
    {
        Level level = pEntity.level;

        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for(int i=0; i< pEntity.itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<MandalorianForgeRecipe> recipe = level.getRecipeManager().getRecipeFor(MandalorianForgeRecipe.Type.INSTANCE, inventory, level);

        if(hasRecipe(pEntity))
        {
            pEntity.FLUID_TANK.drain(recipe.get().getFluid().getAmount(), IFluidHandler.FluidAction.EXECUTE);
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem().getItem(),
                    pEntity.itemHandler.getStackInSlot(2).getCount() + 1));

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(MandalorianForgeBlockEntity entity)
    {
        Level level = entity.level;

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for(int i=0; i< entity.itemHandler.getSlots(); i++)
        {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<MandalorianForgeRecipe> recipe = level.getRecipeManager().getRecipeFor(MandalorianForgeRecipe.Type.INSTANCE, inventory, level);

        return  recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem())
                && recipe.get().getFluid().equals(entity.FLUID_TANK.getFluid())
                && hasEnoughFluid(entity);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack itemStack)
    {
        return inventory.getItem(2).getItem() == itemStack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory)
    {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }

    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(2).isEmpty()) {
            stack = itemHandler.getStackInSlot(2);
        } else {
            stack = itemHandler.getStackInSlot(1);
        }

        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
}