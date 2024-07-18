package com.jerry.eup2p.tag;

import net.minecraftforge.data.event.GatherDataEvent;

public class EUP2PDataGenerators {

    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var pack = generator.getVanillaPack(true);
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProvider = pack.addProvider(packOutput -> new BlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        pack.addProvider(packOutput -> new ItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
    }
}
