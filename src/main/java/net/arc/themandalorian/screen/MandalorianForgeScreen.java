package net.arc.themandalorian.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.screen.renderer.FluidTankRenderer;
import net.arc.themandalorian.util.MouseUtil;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;

import java.util.Optional;

public class MandalorianForgeScreen extends AbstractContainerScreen<MandalorianForgeMenu>
{
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TheMandalorian.MOD_ID, "textures/gui/mandalorian_forge_gui.png");

    private FluidTankRenderer renderer;

    public MandalorianForgeScreen(MandalorianForgeMenu menu, Inventory inventory, Component component)
    {
        super(menu, inventory, component);
    }

    @Override
    protected void init()
    {
        super.init();
        assignFluidRenderer();
    }

    private void test() {}

    private void assignFluidRenderer()
    {
        renderer = new FluidTankRenderer(1000, true, 6, 40);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY)
    {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderFluidAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderFluidAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y)
    {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 86, 18)) {
            renderTooltip(pPoseStack, renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY)
    {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    @Override
    protected void renderBg(PoseStack stack, float pPartialTick, int pMouseX, int pMouseY)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(stack, x, y, 0, 0, imageWidth, imageHeight);
        renderProgressArrow(stack, x, y);

        renderForgeButton(stack, x, y, pMouseX, pMouseY);

        renderer.render(stack, x + 86, y + 18, menu.getFluidStack());
    }

    private void renderForgeButton(PoseStack pPoseStack, int x, int y, int pMouseX, int pMouseY)
    {
        boolean notInUse = !menu.isCrafting();
        boolean sufficientFluid = menu.getFluidStack().getAmount() >= 200;
        boolean outputSpace = !menu.getSlot(MandalorianForgeMenu.TE_INVENTORY_FIRST_SLOT_INDEX + 2).hasItem();
        boolean validInput = menu.getSlot(MandalorianForgeMenu.TE_INVENTORY_FIRST_SLOT_INDEX + 1).getItem().getCount() > 0; //TEMP

        boolean enabled = notInUse && sufficientFluid && outputSpace && validInput;

        int offset = 2;
        if (!enabled) {
            offset = 34;
        }
        else if (MouseUtil.isMouseOver(pMouseX, pMouseY, x + 102, y + 45, 35, 15)) {
            offset = 18;
        }

        blit(pPoseStack, x + 102, y + 45, 197, offset, 35, 15);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton)
    {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (MouseUtil.isMouseOver(pMouseX, pMouseY, x + 102, y + 45, 35, 15))
        {
            menu.startForging();
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    private void renderProgressArrow(PoseStack pPoseStack, int x, int y)
    {
        if(menu.isCrafting())
        {
            blit(pPoseStack, x + 163, y + 4, 177, 0, 7, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta)
    {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
