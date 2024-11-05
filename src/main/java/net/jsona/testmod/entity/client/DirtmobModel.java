// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.jsona.testmod.entity.client;

import net.jsona.entity.custom.DirtmobEntity;
import net.jsona.testmod.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class DirtmobModel<T extends DirtmobEntity> extends SinglePartEntityModel<T> {
	private final ModelPart dirt;
	private final ModelPart body;
	private final ModelPart legs;
	public DirtmobModel(ModelPart root) {
		this.dirt = root.getChild("dirt");
		this.body = dirt.getChild("body");
		this.legs = body.getChild("legs");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData dirt = modelPartData.addChild("dirt", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData body = dirt.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -15.0F, -7.0F, 12.0F, 13.0F, 14.0F, new Dilation(0.0F))
		.uv(0, 27).cuboid(-7.0F, -16.0F, -8.0F, 14.0F, 1.0F, 16.0F, new Dilation(0.0F))
		.uv(0, 44).cuboid(6.0F, -15.0F, -8.0F, 1.0F, 1.0F, 16.0F, new Dilation(0.0F))
		.uv(34, 44).cuboid(-7.0F, -15.0F, -8.0F, 1.0F, 1.0F, 16.0F, new Dilation(0.0F))
		.uv(52, 0).cuboid(-6.0F, -15.0F, 7.0F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(52, 2).cuboid(-6.0F, -15.0F, -8.0F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData legs = body.addChild("legs", ModelPartBuilder.create().uv(52, 4).cuboid(-1.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(52, 8).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(DirtmobEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.getIdleAnimationState(), ModAnimations.dirtmobAnimation.idle, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		super.render(matrices, vertices, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return dirt;
	}
}