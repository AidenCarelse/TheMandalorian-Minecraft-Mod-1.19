package net.arc.themandalorian.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.entity.custom.GroguEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class GroguRenderer extends GeoEntityRenderer<GroguEntity>
{
    public GroguRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GroguModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(GroguEntity instance) {
        return new ResourceLocation(TheMandalorian.MOD_ID, "textures/entity/grogu_texture.png");
    }

    @Override
    public RenderType getRenderType(GroguEntity animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.6f, 0.6f, 0.6f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
