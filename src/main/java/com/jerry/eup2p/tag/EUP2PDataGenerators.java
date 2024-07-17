package com.jerry.eup2p.tag;

import net.minecraftforge.data.event.GatherDataEvent;

public class EUP2PDataGenerators {
    public static void gatherData(GatherDataEvent event) {
        var generators = event.getGenerator();
        var packOutput = generators.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();
        var blockTagsProvider = new BlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generators.addProvider(true, new ItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
    }
}
