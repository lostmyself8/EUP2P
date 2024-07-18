package com.jerry.eup2p.tag;

import appeng.datagen.providers.IAE2DataProvider;
import com.jerry.eup2p.EUP2P;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BlockTagsProvider extends IntrinsicHolderTagsProvider<Block> implements IAE2DataProvider {
    public BlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries,
                             ExistingFileHelper existingFileHelper) {
        super(packOutput, Registries.BLOCK, registries, block -> block.builtInRegistryHolder().key(), EUP2P.MOD_ID,
                existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }
}
