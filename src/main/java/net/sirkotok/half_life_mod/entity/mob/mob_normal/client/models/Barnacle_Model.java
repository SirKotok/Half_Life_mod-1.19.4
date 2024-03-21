package net.sirkotok.half_life_mod.entity.mob.mob_normal.client.models;
// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.sirkotok.half_life_mod.entity.mob.mob_normal.animations.ModAnimationDefenitions;
import net.sirkotok.half_life_mod.entity.mob.mob_normal.custom.Barnacle;
import org.joml.Vector3f;


import javax.annotation.Nullable;

public class Barnacle_Model<T extends Barnacle> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart tongue;
	@Nullable
	private Barnacle entity;
	public Barnacle_Model(ModelPart pRoot) {
		this.root = pRoot.getChild("root");
		this.tongue = this.root.getChild("tongue");
	}


	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(-0.5F, 10.0F, -3.5F));

		PartDefinition bottom = root.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 16).addBox(-5.5F, 13.0F, -2.5F, 12.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition middle = bottom.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 29).addBox(-4.5F, -4.0F, -5.5F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, 4.0F));

		PartDefinition top = middle.addOrReplaceChild("top", CubeListBuilder.create().texOffs(32, 35).addBox(-3.5F, -3.0F, -3.5F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -1.0F));

		PartDefinition tooth1 = top.addOrReplaceChild("tooth1", CubeListBuilder.create().texOffs(0, 11).addBox(-0.3F, -1.0F, -0.1F, 0.7F, 1.6F, 0.7F, new CubeDeformation(0.0F)), PartPose.offset(0.6F, -3.0F, -3.0F));

		PartDefinition cube_r1 = tooth1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2F, -1.9498F, -0.4939F, 0.4F, 1.6F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2F, 0.3F, -0.5061F, 0.0F, 0.0F));

		PartDefinition tooth2 = top.addOrReplaceChild("tooth2", CubeListBuilder.create().texOffs(0, 11).addBox(-0.3F, -1.0F, 0.1F, 0.7F, 1.6F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6F, -3.0F, 4.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r2 = tooth2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2F, -0.9003F, 0.0878F, 0.4F, 1.6F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4F, 0.5F, -0.5061F, 0.0F, 0.0F));

		PartDefinition tooth3 = top.addOrReplaceChild("tooth3", CubeListBuilder.create().texOffs(0, 11).addBox(-0.3F, -1.0F, -0.4F, 0.7F, 1.6F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6F, -3.0F, -1.0F, 0.0F, -1.0821F, 0.0F));

		PartDefinition cube_r3 = tooth3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2F, -0.9003F, 0.0878F, 0.4F, 1.6F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4F, 0.0F, -0.5061F, 0.0F, 0.0F));

		PartDefinition tooth4 = top.addOrReplaceChild("tooth4", CubeListBuilder.create().texOffs(0, 11).addBox(-0.3F, -1.0F, -0.4F, 0.7F, 1.6F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6F, -3.0F, 2.0F, 0.0F, -1.8675F, 0.0F));

		PartDefinition cube_r4 = tooth4.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2F, -0.9003F, 0.0878F, 0.4F, 1.6F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4F, 0.0F, -0.5061F, 0.0F, 0.0F));

		PartDefinition tooth5 = top.addOrReplaceChild("tooth5", CubeListBuilder.create().texOffs(0, 11).addBox(-0.3F, -1.0F, -0.4F, 0.7F, 1.6F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4F, -3.0F, 2.0F, 0.0F, 2.1031F, 0.0F));

		PartDefinition cube_r5 = tooth5.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2F, -0.9003F, 0.0878F, 0.4F, 1.6F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4F, 0.0F, -0.5061F, 0.0F, 0.0F));

		PartDefinition tooth6 = top.addOrReplaceChild("tooth6", CubeListBuilder.create().texOffs(0, 11).addBox(-0.3F, -1.0F, -0.4F, 0.7F, 1.6F, 0.7F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4F, -3.0F, -1.0F, 0.0F, 1.0996F, 0.0F));

		PartDefinition cube_r6 = tooth6.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2F, -0.9003F, 0.0878F, 0.4F, 1.6F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4F, 0.0F, -0.5061F, 0.0F, 0.0F));

		PartDefinition tongue = root.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, -21.9F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 24.0F, 3.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}




	public void prepareMobModel(Barnacle pEntity) {
		this.entity = pEntity;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(((Barnacle) entity).idleAnimationState, ModAnimationDefenitions.BARNACLE_IDLE, ageInTicks, 1f);
		this.animate(((Barnacle) entity).attackAnimationState, ModAnimationDefenitions.BARNACLE_BITE, ageInTicks, 1f);
		this.animate(((Barnacle) entity).dyingAnimationState, ModAnimationDefenitions.BARNACLE_DIE, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		if (this.entity != null) {
			float scale = this.entity.gettonguelength()*16+8;
			Vector3f vec3f = new Vector3f(0, scale , 0);
			Vector3f vec3mf = new Vector3f(-0.5f, 21.9f*(scale+1)-scale-5, -3.5f);
		this.tongue.offsetScale(vec3f);
		this.tongue.offsetPos(vec3mf);
		}

		this.tongue.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}

}