package com.jerry.eup2p;

import appeng.api.features.P2PTunnelAttunement;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.jerry.eup2p.common.GuiLang;
import com.jerry.eup2p.common.registry.EUP2PItem;
import com.jerry.eup2p.common.tag.EUP2PDataGenerators;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(EUP2P.MOD_ID)
public class EUP2P {
    public static final String MOD_ID = "eup2p";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EUP2P() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        EUP2PItem.init(ForgeRegistries.ITEMS);
        modEventBus.addListener(EUP2PDataGenerators::gatherData);
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
        P2PTunnelAttunement.registerAttunementTag(EUP2PItem.LASER_P2P_TUNNEL);

        P2PTunnelAttunement.registerAttunementApi(EUP2PItem.EU_P2P_TUNNEL,
                GTCapability.CAPABILITY_ELECTRIC_ITEM,
                GuiLang.P2P_ATTUNEMENT_EU.text());
        P2PTunnelAttunement.registerAttunementApi(EUP2PItem.LASER_P2P_TUNNEL,
                GTCapability.CAPABILITY_LASER,
                GuiLang.P2P_ATTUNEMENT_LASER.text());
    }
}
