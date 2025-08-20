package com.jerry.eup2p.common.registries;

import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import appeng.api.parts.PartModels;
import appeng.core.MainCreativeTab;
import appeng.core.definitions.ItemDefinition;
import appeng.items.parts.PartItem;
import appeng.items.parts.PartModelsHelper;
import com.jerry.eup2p.EUP2P;
import com.jerry.eup2p.common.parts.p2p.EUP2PTunnelPart;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class EUP2PItem {

    public static final DeferredRegister.Items DR_ITEMS = DeferredRegister.createItems(EUP2P.MOD_ID);

    public static final List<ItemDefinition<?>> ITEMS = new ArrayList<>();

    public static List<ItemDefinition<?>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    public static final ItemDefinition<PartItem<EUP2PTunnelPart>> EU_P2P_TUNNEL = part("EU P2P Tunnel", "eu_p2p_tunnel", EUP2PTunnelPart.class, EUP2PTunnelPart::new);

    private static <T extends IPart> ItemDefinition<PartItem<T>> part(String englishName, String id, Class<T> partClass, Function<IPartItem<T>, T> factory) {
        PartModels.registerModels(PartModelsHelper.createModels(partClass));
        return item(englishName, id, p -> new PartItem<>(p, partClass, factory));
    }

    private static <T extends Item> ItemDefinition<T> item(String englishName, String id, Function<Item.Properties, T> factory) {
        new Item.Properties();
        ItemDefinition<T> definition = new ItemDefinition<>(englishName, DR_ITEMS.registerItem(id, factory));
        MainCreativeTab.add(definition);
        ITEMS.add(definition);
        return definition;
    }

//    public static void init(ResourceKey<Registry<Item>> registry) {
//        for (ItemDefinition<?> definition : EUP2PItem.getItems()) {
//            registry.register(definition.id(), definition.asItem());
//        }
//    }

//    public static void register(IEventBus eventBus) {
//        EUP2P_ITEMS.register(eventBus);
//        ForgeRegistries.ITEMS.register(EU_P2P_TUNNEL.id(), EU_P2P_TUNNEL.asItem());
//    }
}
