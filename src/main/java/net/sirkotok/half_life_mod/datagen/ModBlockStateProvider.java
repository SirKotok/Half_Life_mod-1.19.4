package net.sirkotok.half_life_mod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sirkotok.half_life_mod.HalfLifeMod;
import net.sirkotok.half_life_mod.block.HalfLifeBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, HalfLifeMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(HalfLifeBlocks.VOLTIGORE_NEST);
        blockWithItem(HalfLifeBlocks.XEN_ROCK);
        grassLikeBlock(HalfLifeBlocks.XEN_GROUND, HalfLifeBlocks.XEN_ROCK);


        blockWithItem(HalfLifeBlocks.HALF_LIFE_PORTAL);



    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }


    public void grassLikeBlock(RegistryObject<Block> grass, RegistryObject<Block> rock){
        simpleBlockWithItem(grass.get(), grassmodel(grass.get(), rock.get()));
    }
    private void blockWithItemBottomTop(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeBT(blockRegistryObject.get()));
    }

    public ModelFile cubeBT(Block block) {
        return models().cubeBottomTop(namefromblock(block), texture_block(block), bottomblocktexture(block), topblocktexture(block));
    }

    public ModelFile grassmodel(Block grass, Block rock) {
        return models().cubeBottomTop(namefromblock(grass), texture_block(grass), texture_block(rock), topblocktexture(grass));
    }


    public ResourceLocation texture_block(Block block) {
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }

    public ResourceLocation bottomblocktexture(Block block) {
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath()+"_bottom");
    }

    public ResourceLocation topblocktexture(Block block) {
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath()+"_top");
    }

    public  String namefromblock(Block block) {
        return key(block).getPath();
    }
    public ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

}
