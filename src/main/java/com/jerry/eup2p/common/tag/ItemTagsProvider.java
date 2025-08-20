package com.jerry.eup2p.common.tag;

import appeng.api.features.P2PTunnelAttunement;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.jerry.eup2p.EUP2P;
import com.jerry.eup2p.common.registry.EUP2PItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {
    public ItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries,
                            CompletableFuture<TagLookup<Block>> blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, registries, blockTagsProvider, EUP2P.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        GTMaterialBlocks.CABLE_BLOCKS.rowMap().forEach((prefix, map) -> {
            map.forEach((material, blockEntry) -> {
                tag(P2PTunnelAttunement.getAttunementTag(EUP2PItem.EU_P2P_TUNNEL))
                        .add(blockEntry.asItem());
            });
        });
        tag(P2PTunnelAttunement.getAttunementTag(EUP2PItem.LASER_P2P_TUNNEL))
                .add(GTBlocks.LASER_PIPES[1].asItem());
    }
}
