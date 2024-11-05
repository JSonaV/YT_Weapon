package net.jsona.testmod.entity.client;

import net.jsona.entity.custom.BrewermobEntity;
import net.jsona.entity.custom.DirtmobEntity;
import net.jsona.testmod.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BrewermobModel<T extends BrewermobEntity> extends SinglePartEntityModel<T> {
	private final ModelPart brewer;
	private final ModelPart body;
	private final ModelPart foot;
	private final ModelPart one;
	private final ModelPart two;
	private final ModelPart three;

	public BrewermobModel(ModelPart root) {
		this.brewer = root.getChild("brewer");
		this.body = this.brewer.getChild("body");
		this.foot = this.body.getChild("foot");
		this.one = this.foot.getChild("one");
		this.two = this.foot.getChild("two");
		this.three = this.foot.getChild("three");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData brewer = modelPartData.addChild("brewer", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = brewer.addChild("body", ModelPartBuilder.create().uv(0, 18).cuboid(-1.0F, -12.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F))
				.uv(16, 18).cuboid(-1.0F, -14.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(20, 0).cuboid(1.0F, -14.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(20, 4).cuboid(-3.0F, -14.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(20, 8).cuboid(-1.0F, -14.0F, 1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(8, 18).cuboid(0.0F, -11.0F, 1.0F, 0.0F, 7.0F, 2.0F, new Dilation(0.0F))
				.uv(12, 18).cuboid(0.0F, -11.0F, -3.0F, 0.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData foot = body.addChild("foot", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData one = foot.addChild("one", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = one.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -4.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, 0.0F, 5.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData two = foot.addChild("two", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r2 = two.addChild("cube_r2", ModelPartBuilder.create().uv(0, 6).cuboid(-1.0F, -2.0F, -4.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		ModelPartData three = foot.addChild("three", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r3 = three.addChild("cube_r3", ModelPartBuilder.create().uv(0, 12).cuboid(-1.0F, -2.0F, -4.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, -1.0F, -2.0F, 0.1289F, 0.0227F, -0.1731F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	public void setAngles(BrewermobEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.getIdleAnimationState(), ModAnimations.brewermobModelAnimation.idle, ageInTicks, 1f);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		super.render(matrices, vertices, light, overlay, color);
	}

	@Override
	public ModelPart getPart() {
		return brewer;
	}
}