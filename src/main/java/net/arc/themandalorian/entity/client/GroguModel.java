package net.arc.themandalorian.entity.client;

import net.arc.themandalorian.TheMandalorian;
import net.arc.themandalorian.entity.custom.GroguEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GroguModel extends AnimatedGeoModel<GroguEntity>
{
    @Override
    public ResourceLocation getModelResource(GroguEntity object) {
        return new ResourceLocation(TheMandalorian.MOD_ID, "geo/grogu.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GroguEntity object) {
        return new ResourceLocation(TheMandalorian.MOD_ID, "textures/entity/grogu_texture.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GroguEntity animatable) {
        return new ResourceLocation(TheMandalorian.MOD_ID, "animations/grogu.animation.json");
    }
}
