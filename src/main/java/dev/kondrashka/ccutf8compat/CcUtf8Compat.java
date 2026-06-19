package dev.kondrashka.ccutf8compat;

import com.mojang.logging.LogUtils;
import dev.kondrashka.ccutf8compat.config.CcUtf8CompatConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(CcUtf8Compat.MOD_ID)
public final class CcUtf8Compat {

    public static final String MOD_ID = "cc_utf8_compat";
    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public CcUtf8Compat() {
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.COMMON,
                CcUtf8CompatConfig.SPEC
        );

        LOGGER.info("CC UTF-8 Compat loaded");
    }
}