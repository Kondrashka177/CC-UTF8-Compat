package dev.kondrashka.ccutf8compat.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class CcUtf8CompatConfig {

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue ENABLE_CC_UTF8_COMPAT;

    static {
        var builder = new ForgeConfigSpec.Builder();

        ENABLE_CC_UTF8_COMPAT = builder
                .comment("Enable UTF-8 compatibility patches for CC:Tweaked.")
                .define("ccUtf8Compat", true);

        SPEC = builder.build();
    }

    private CcUtf8CompatConfig() {
    }
}