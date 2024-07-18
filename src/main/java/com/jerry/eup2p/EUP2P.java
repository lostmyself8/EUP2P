package com.jerry.eup2p;

import appeng.api.features.P2PTunnelAttunement;
import com.jerry.eup2p.config.EUP2PConfig;
import com.jerry.eup2p.registry.EUP2PItem;
import com.jerry.eup2p.tag.EUP2PDataGenerators;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(EUP2P.MOD_ID)
public class EUP2P {
    public static final String MOD_ID = "eup2p";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EUP2P() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        EUP2PItem.register(modEventBus);
        modEventBus.addListener(EUP2PDataGenerators::gatherData);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, EUP2PConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(EUP2P.MOD_ID, path);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(this::initializeAttunement);
    }

    private void initializeAttunement() {
        P2PTunnelAttunement.registerAttunementTag(EUP2PItem.EU_P2P_TUNNEL);
    }
}
