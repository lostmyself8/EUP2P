package com.jerry.eup2p.common;

import appeng.core.localization.LocalizationEnum;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public enum GuiLang implements LocalizationEnum {

    P2P_ATTUNEMENT_EU("Portable EU Energy Storage (i.e. Batteries)"),
    P2P_ATTUNEMENT_LASER("Items capable of storing or transmitting lasers (i.e. Normal Laser Pipe)");

    private final String root;

    @Nullable
    private final String englishText;

    private final Component text;

    GuiLang(@Nullable String englishText) {
        this.root = "gui.ae2";
        this.englishText = englishText;
        this.text = Component.translatable(getTranslationKey());
    }

    GuiLang(@Nullable String englishText, String r) {
        this.root = r;
        this.englishText = englishText;
        this.text = Component.translatable(getTranslationKey());
    }

    @Nullable
    public String getEnglishText() {
        return englishText;
    }

    @Override
    public String getTranslationKey() {
        return this.root + '.' + name();
    }

    public String getLocal() {
        return text.getString();
    }
}