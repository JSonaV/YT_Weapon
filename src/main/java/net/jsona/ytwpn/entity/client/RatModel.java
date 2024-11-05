package net.jsona.ytwpn.entity.client;


import net.jsona.entity.custom.RatEntity;
import net.jsona.ytwpn.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class RatModel<T extends RatEntity> extends SinglePartEntityModel<T> {
	private final ModelPart rat;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart ears;
	private final ModelPart a8;
	private final ModelPart a9;
	private final ModelPart tail;
	private final ModelPart a1;
	private final ModelPart a2;
	private final ModelPart a3;
	private final ModelPart limbs;
	private final ModelPart a4;
	private final ModelPart a5;
	private final ModelPart a6;
	private final ModelPart a7;
	public RatModel(ModelPart root) {
		this.rat = root.getChild("rat");
		this.body = this.rat.getChild("body");
		this.head = this.body.getChild("head");
		this.ears = this.head.getChild("ears");
		this.a8 = this.ears.getChild("a8");
		this.a9 = this.ears.getChild("a9");
		this.tail = this.body.getChild("tail");
		this.a1 = this.tail.getChild("a1");
		this.a2 = this.a1.getChild("a2");
		this.a3 = this.a2.getChild("a3");
		this.limbs = this.body.getChild("limbs");
		this.a4 = this.limbs.getChild("a4");
		this.a5 = this.limbs.getChild("a5");
		this.a6 = this.limbs.getChild("a6");
		this.a7 = this.limbs.getChild("a7");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData rat = modelPartData.addChild("rat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = rat.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -4.0F, -2.0F, 2.0F, 4.0F, 5.0F, new Dilation(0.0F))
				.uv(0, 9).cuboid(-2.0F, -4.0F, -2.0F, 2.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -1.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(14, 10).cuboid(0.0F, -2.0F, -6.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(14, 0).cuboid(-2.0F, -3.0F, -4.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(14, 14).cuboid(-1.0F, -2.0F, -6.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(14, 5).cuboid(0.0F, -3.0F, -4.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData ears = head.addChild("ears", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData a8 = ears.addChild("a8", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = a8.addChild("cube_r1", ModelPartBuilder.create().uv(2, 2).cuboid(-0.5F, -2.0F, -1.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -3.0F, -2.0F, 0.0F, 0.0F, 0.6545F));

		ModelPartData a9 = ears.addChild("a9", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, 0.0F, 0.0F));

		ModelPartData cube_r2 = a9.addChild("cube_r2", ModelPartBuilder.create().uv(2, 2).cuboid(-0.5F, -2.0F, -1.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -3.0F, -2.0F, 0.0F, 0.0F, 0.6545F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData a1 = tail.addChild("a1", ModelPartBuilder.create().uv(0, 18).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 3.0F));

		ModelPartData a2 = a1.addChild("a2", ModelPartBuilder.create().uv(6, 18).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		ModelPartData a3 = a2.addChild("a3", ModelPartBuilder.create().uv(12, 18).cuboid(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));

		ModelPartData limbs = body.addChild("limbs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData a4 = limbs.addChild("a4", ModelPartBuilder.create().uv(18, 18).cuboid(-3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData a5 = limbs.addChild("a5", ModelPartBuilder.create().uv(20, 10).cuboid(-3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

		ModelPartData a6 = limbs.addChild("a6", ModelPartBuilder.create().uv(20, 13).cuboid(-3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 0.0F, 4.0F));

		ModelPartData a7 = limbs.addChild("a7", ModelPartBuilder.create().uv(0, 21).cuboid(-3.0F, -1.0F, -3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.getIdleAnimationState(), ModAnimations.ratAnimation.idle, ageInTicks, 1f);
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		super.render(matrices, vertices, light, overlay, color);
	}

	public ModelPart getPart() {
		return rat;
	}
}