package net.sirkotok.half_life_mod.entity.mob.mob_geckolib.client.renderers.renderlayer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.entity.mob.mob_geckolib.custom.HLZombieVillager;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
/*
public class VZombieProffesionLayer extends GeoRenderLayer<HLZombieVillager> {
}
 public VZombieProffesionLayer(GeoRenderer<HLZombieVillager> entityRenderer) {
        super(entityRenderer);
        }

        private final String path = "villager";

        private ResourceLocation getResourceLocatione(String p_117669_, ResourceLocation p_117670_) {
                return p_117670_.withPath((p_247944_) -> "textures/entity/" + this.path + "/" + p_117669_ + "/" + p_247944_ + ".png");
        }




// Apply the glasses texture layer to the existing geo model, and render it over the top of the existing model
@Override
public void render(PoseStack poseStack, HLZombieVillager animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {


        VillagerData villagerdata = animatable.getVillagerData();
        VillagerType villagertype = villagerdata.getType();
        VillagerProfession villagerprofession = villagerdata.getProfession();

        ResourceLocation TEST = new ResourceLocation(HalfLifeMod.MOD_ID, "textures/entity/whitescreen.png");

        ResourceLocation TYPE = this.getResourceLocatione("type", BuiltInRegistries.VILLAGER_TYPE.getKey(villagertype));

        RenderType renderType1 = RenderType.armorCutoutNoCull(TEST);


        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, renderType1,
                bufferSource.getBuffer(renderType1), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);

       // if (villagerprofession != VillagerProfession.NONE) {
           //     ResourceLocation PROFFESSION = this.getResourceLocatione("profession", BuiltInRegistries.VILLAGER_PROFESSION.getKey(villagerprofession));
               // RenderType renderType2 = RenderType.entityCutout(PROFFESSION);

            //    getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, renderType2,
            //            bufferSource.getBuffer(renderType1), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
           //             1, 1, 1, 1);
     //   }



        }

        }


        /*

           public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      if (!pLivingEntity.isInvisible()) {
         VillagerData villagerdata = pLivingEntity.getVillagerData();
         VillagerType villagertype = villagerdata.getType();
         VillagerProfession villagerprofession = villagerdata.getProfession();
         VillagerMetaDataSection.Hat villagermetadatasection$hat = this.getHatData(this.typeHatCache, "type", BuiltInRegistries.VILLAGER_TYPE, villagertype);
         VillagerMetaDataSection.Hat villagermetadatasection$hat1 = this.getHatData(this.professionHatCache, "profession", BuiltInRegistries.VILLAGER_PROFESSION, villagerprofession);
         M m = this.getParentModel();
         m.hatVisible(villagermetadatasection$hat1 == VillagerMetaDataSection.Hat.NONE || villagermetadatasection$hat1 == VillagerMetaDataSection.Hat.PARTIAL && villagermetadatasection$hat != VillagerMetaDataSection.Hat.FULL);
         ResourceLocation resourcelocation = this.getResourceLocation("type", BuiltInRegistries.VILLAGER_TYPE.getKey(villagertype));
         renderColoredCutoutModel(m, resourcelocation, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0F, 1.0F, 1.0F);
         m.hatVisible(true);
         if (villagerprofession != VillagerProfession.NONE && !pLivingEntity.isBaby()) {
            ResourceLocation resourcelocation1 = this.getResourceLocation("profession", BuiltInRegistries.VILLAGER_PROFESSION.getKey(villagerprofession));
            renderColoredCutoutModel(m, resourcelocation1, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0F, 1.0F, 1.0F);
            if (villagerprofession != VillagerProfession.NITWIT) {
               ResourceLocation resourcelocation2 = this.getResourceLocation("profession_level", LEVEL_LOCATIONS.get(Mth.clamp(villagerdata.getLevel(), 1, LEVEL_LOCATIONS.size())));
               renderColoredCutoutModel(m, resourcelocation2, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, 1.0F, 1.0F, 1.0F);
            }
         }

      }
   }

   private ResourceLocation getResourceLocation(String p_117669_, ResourceLocation p_117670_) {
      return p_117670_.withPath((p_247944_) -> {
         return "textures/entity/" + this.path + "/" + p_117669_ + "/" + p_247944_ + ".png";
      });
   }

   public <K> VillagerMetaDataSection.Hat getHatData(Object2ObjectMap<K, VillagerMetaDataSection.Hat> p_117659_, String p_117660_, DefaultedRegistry<K> pVillagerTypeRegistry, K p_117662_) {
      return p_117659_.computeIfAbsent(p_117662_, (p_258159_) -> {
         return this.resourceManager.getResource(this.getResourceLocation(p_117660_, pVillagerTypeRegistry.getKey(p_117662_))).flatMap((p_234875_) -> {
            try {
               return p_234875_.metadata().getSection(VillagerMetaDataSection.SERIALIZER).map(VillagerMetaDataSection::getHat);
            } catch (IOException ioexception) {
               return Optional.empty();
            }
         }).orElse(VillagerMetaDataSection.Hat.NONE);
      });
   }
}
         */