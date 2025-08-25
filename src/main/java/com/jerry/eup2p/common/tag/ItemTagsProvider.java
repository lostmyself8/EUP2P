package com.jerry.eup2p.common.tag;

import appeng.api.features.P2PTunnelAttunement;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.common.block.LaserPipeBlock;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterialBlocks;
import com.jerry.eup2p.EUP2P;
import com.jerry.eup2p.common.registry.EUP2PItem;
import com.tterrag.registrate.util.entry.BlockEntry;
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
        for (BlockEntry<LaserPipeBlock> laser : GTBlocks.LASER_PIPES) {
            tag(P2PTunnelAttunement.getAttunementTag(EUP2PItem.LASER_P2P_TUNNEL))
                    .add(laser.asItem());
        }
//        add(GTMachines.LASER_INPUT_HATCH_256);
//        add(GTMachines.LASER_OUTPUT_HATCH_256);
//        add(GTMachines.LASER_INPUT_HATCH_1024);
//        add(GTMachines.LASER_OUTPUT_HATCH_1024);
//        add(GTMachines.LASER_INPUT_HATCH_4096);
//        add(GTMachines.LASER_OUTPUT_HATCH_4096);
    }

//    private void add(MachineDefinition[] machineDefinitions) {
//        for (MachineDefinition hatch : machineDefinitions) {
//            tag(P2PTunnelAttunement.getAttunementTag(EUP2PItem.LASER_P2P_TUNNEL))
//                    .add(hatch.getItem());
//        }
//    }
}
