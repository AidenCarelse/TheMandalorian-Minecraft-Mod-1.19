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

    private void assignFluidRenderer()
    {
        // Capacity much match block entity!
        renderer = new FluidTankRenderer(10000, true, 6, 40);
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

        renderer.render(stack, x + 86, y + 18, menu.getFluidStack());
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
