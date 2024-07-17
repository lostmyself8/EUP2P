package com.jerry.eup2p.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EUP2PConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder()
            .comment("EU P2P Tunnel Config")
            .push("EUP2PConfig");

    public static final ForgeConfigSpec.BooleanValue NEED_BALANCE = BUILDER
            .comment("Is the current evenly distributed based on the number of P2P output ports.")
            .comment("For Example:If the value is true, the system will only work properly if the input current can be evenly divided by the number of P2P output ports.")
            .comment("是否根据P2P输出端的数量均分电流")
            .comment("例如：如果该值为真，当输入电流大小可以整除P2P输出端口数量时系统才会正常工作")
            .define("needBalance", true);

    public static final ForgeConfigSpec SPEC = BUILDER.pop().build();
}
