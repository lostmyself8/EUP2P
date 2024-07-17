package com.jerry.eup2p.api.id;

import appeng.api.ids.AEConstants;
import com.jerry.eup2p.EUP2P;
import net.minecraft.resources.ResourceLocation;

public final class ModPartIDs {
    public static final ResourceLocation EU_P2P_TUNNEL = id("eu_p2p_tunnel");

    private static ResourceLocation id(String id) {
        return new ResourceLocation(EUP2P.MOD_ID, id);
    }
}
