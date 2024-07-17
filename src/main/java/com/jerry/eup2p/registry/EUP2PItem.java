package com.jerry.eup2p.registry;

import appeng.api.parts.PartModels;
import appeng.items.parts.PartItem;
import appeng.items.parts.PartModelsHelper;
import com.jerry.eup2p.EUP2P;
import com.jerry.eup2p.parts.p2p.EUP2PTunnelPart;
import net.minecraft.Util;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EUP2PItem {
    private static final DeferredRegister<Item> MOD_ITEMs = DeferredRegister.create(ForgeRegistries.ITEMS, EUP2P.MOD_ID);

    public static final RegistryObject<PartItem<EUP2PTunnelPart>> EU_P2P_TUNNEL = Util.make(() -> {
        PartModels.registerModels(PartModelsHelper.createModels(EUP2PTunnelPart.class));
        return MOD_ITEMs.register("eu_p2p_tunnel",
                () -> new PartItem<>(new Item.Properties(), EUP2PTunnelPart.class, EUP2PTunnelPart::new));
    });

    public static void register(IEventBus eventBus) {
        MOD_ITEMs.register(eventBus);
    }
}
