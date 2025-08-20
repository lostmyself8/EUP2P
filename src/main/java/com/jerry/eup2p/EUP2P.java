package com.jerry.eup2p;

import appeng.api.parts.RegisterPartCapabilitiesEvent;
import appeng.core.AELog;
import com.gregtechceu.gtceu.api.capability.GTCapability;
import com.jerry.eup2p.common.init.internal.InitP2PAttunements;
import com.jerry.eup2p.common.parts.p2p.EUP2PTunnelPart;
import com.jerry.eup2p.common.registries.EUP2PItem;
import com.jerry.eup2p.common.tag.EUP2PDataGenerators;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(EUP2P.MOD_ID)
public class EUP2P {
    public static final String MOD_ID = "eup2p";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EUP2P(IEventBus modEventBus, ModContainer modContainer) {
        partCapabilities(modEventBus);
        modEventBus.addListener(EUP2PDataGenerators::gatherData);
        EUP2PItem.DR_ITEMS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(this::postRegistrationInitialization).whenComplete((res, err) -> {
            if (err != null) {
                AELog.warn(err);
            }
        });
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public void postRegistrationInitialization() {
        InitP2PAttunements.init();
    }

    private void partCapabilities(IEventBus modEventBus) {
        modEventBus.addListener((RegisterPartCapabilitiesEvent event) -> event.register(GTCapability.CAPABILITY_ENERGY_CONTAINER, (part, context) -> part.getExposedApi(), EUP2PTunnelPart.class));
    }
}
