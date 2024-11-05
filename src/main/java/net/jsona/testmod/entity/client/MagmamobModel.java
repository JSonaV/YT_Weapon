package net.jsona.testmod.entity.client;

import net.jsona.entity.custom.MagmamobEntity;
import net.jsona.testmod.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MagmamobModel<T extends MagmamobEntity> extends SinglePartEntityModel<T> {
	private final ModelPart dispenser;
	private final ModelPart body;
	private final ModelPart legs;
	private final ModelPart right;
	private final ModelPart left;
	public MagmamobModel(ModelPart root) {
		this.dispenser = root.getChild("dispenser");
		this.body = this.dispenser.getChild("body");
		this.legs = this.dispenser.getChild("legs");
		this.right = this.legs.getChild("right");
		this.left = this.legs.getChild("left");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData dispenser = modelPartData.addChild("dispenser", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = dispenser.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -15.0F, -7.0F, 14.0F, 7.0F, 14.0F, new Dilation(0.0F))
				.uv(0, 21).cuboid(-7.0F, -6.0F, -7.0F, 14.0F, 4.0F, 14.0F, new Dilation(0.0F))
				.uv(24, 39).cuboid(-1.0F, -8.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData legs = dispenser.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right = legs.addChild("right", ModelPartBuilder.create().uv(12, 39).cuboid(7.0F, -10.0F, -3.0F, 1.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left = legs.addChild("left", ModelPartBuilder.create().uv(0, 39).cuboid(-8.0F, -10.0F, -3.0F, 1.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(MagmamobEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.getIdleAnimationState(), ModAnimations.magmamobAnimation.idle, ageInTicks, 1f);
		this.updateAnimation(entity.getAttackAnimationState(), ModAnimations.magmamobAnimation.attack, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		super.render(matrices, vertices, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return dispenser;
	}


}