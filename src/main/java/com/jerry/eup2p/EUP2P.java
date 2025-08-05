package com.jerry.eup2p;

import appeng.api.parts.RegisterPartCapabilitiesEvent;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.jerry.eup2p.parts.p2p.EUP2PTunnelPart;
import com.jerry.eup2p.registries.EUP2PItem;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(EUP2P.MOD_ID)
public class EUP2P {
    public static final String MOD_ID = "eup2p";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EUP2P(IEventBus modEventBus, ModContainer modContainer) {
        partCapabilities(modEventBus);
        EUP2PItem.DR_ITEMS.register(modEventBus);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private void partCapabilities(IEventBus modEventBus) {
        modEventBus.addListener((RegisterPartCapabilitiesEvent event) -> event.register(GTCapability.CAPABILITY_ENERGY_CONTAINER, (part, context) -> part.getExposedApi(), EUP2PTunnelPart.class));
    }
}
