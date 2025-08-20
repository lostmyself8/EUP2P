package com.jerry.eup2p.common.init.internal;

import appeng.api.features.P2PTunnelAttunement;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.jerry.eup2p.common.GuiLang;
import com.jerry.eup2p.common.registries.EUP2PItem;

public class InitP2PAttunements {

    private InitP2PAttunements() {
    }

    public static void init() {
        P2PTunnelAttunement.registerAttunementTag(EUP2PItem.EU_P2P_TUNNEL);
        P2PTunnelAttunement.registerAttunementApi(EUP2PItem.EU_P2P_TUNNEL,
                GTCapability.CAPABILITY_ELECTRIC_ITEM,
                GuiLang.P2P_ATTUNEMENT_EU.text());
    }
}
