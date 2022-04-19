package net.moddingplayground.frame.impl.woodtypes.client.render.entity;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.moddingplayground.frame.api.woodtypes.v0.WoodType;
import net.moddingplayground.frame.api.woodtypes.v0.client.model.FrameWoodTypesEntityModelLayers;
import net.moddingplayground.frame.api.woodtypes.v0.entity.FrameBoatEntity;
import net.moddingplayground.frame.mixin.woodtypes.client.BoatEntityRendererAccessor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class FrameBoatEntityRenderer<T extends FrameBoatEntity> extends EntityRenderer<T> {
    private final Function<WoodType, Identifier> textures;
    private final Function<WoodType, BoatEntityModel> models;

    public FrameBoatEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.8f;

        this.models = Util.memoize(type -> new BoatEntityModel(context.getPart(
            Optional.ofNullable(type)
                    .map(FrameWoodTypesEntityModelLayers.BOAT_LAYERS)
                    .orElseGet(() -> EntityModelLayers.createBoat(BoatEntity.Type.OAK))
        )));

        this.textures = Util.memoize(type ->
            Optional.ofNullable(type)
                    .map(t -> {
                        Identifier id = t.getId();
                        return new Identifier(id.getNamespace(), "textures/frame/entity/boat/%s.png".formatted(id.getPath()));
                    })
                    .orElseGet(() -> {
                        BoatEntityRendererAccessor access = (BoatEntityRendererAccessor) new BoatEntityRenderer(context);
                        Map<BoatEntity.Type, Pair<Identifier, BoatEntityModel>> tms = access.getTexturesAndModels();
                        return tms.get(BoatEntity.Type.OAK).getFirst();
                    })
        );
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
        matrices.push();
        matrices.translate(0.0D, 0.375D, 0.0D);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - yaw));

        float wobble = (float)entity.getDamageWobbleTicks() - tickDelta;
        float wobbleStrength = Math.max(entity.getDamageWobbleStrength() - tickDelta, 0);
        if (wobble > 0.0f) {
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(wobble) * wobble * wobbleStrength / 10.0f * entity.getDamageWobbleSide()));
        }
        if (!MathHelper.approximatelyEquals(entity.interpolateBubbleWobble(tickDelta), 0.0f)) {
            matrices.multiply(new Quaternion(new Vec3f(1.0f, 0.0f, 1.0f), entity.interpolateBubbleWobble(tickDelta), true));
        }

        matrices.scale(-1.0f, -1.0f, 1.0f);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f));

        BoatEntityModel model = this.getModelLayer(entity);
        model.setAngles(entity, tickDelta, 0.0f, -0.1f, 0.0f, 0.0f);

        Identifier texture = this.getTexture(entity);
        VertexConsumer vertex = vertices.getBuffer(model.getLayer(texture));
        model.render(matrices, vertex, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

        if (!entity.isSubmergedInWater()) {
            VertexConsumer vertex2 = vertices.getBuffer(RenderLayer.getWaterMask());
            model.getWaterPatch().render(matrices, vertex2, light, OverlayTexture.DEFAULT_UV);
        }

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertices, light);
    }

    /* Cached Getters */

    public BoatEntityModel getModelLayer(T entity) {
        return this.models.apply(entity.getWoodType());
    }

    @Override
    public Identifier getTexture(T entity) {
        return this.textures.apply(entity.getWoodType());
    }
}
