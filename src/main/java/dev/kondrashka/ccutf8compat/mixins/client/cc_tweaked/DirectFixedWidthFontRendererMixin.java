package dev.kondrashka.ccutf8compat.mixins.client.cc_tweaked;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Invoker;

import dan200.computercraft.client.render.text.DirectFixedWidthFontRenderer;
import dan200.computercraft.core.terminal.Palette;
import dan200.computercraft.core.terminal.Terminal;
import dan200.computercraft.core.terminal.TextBuffer;
import dan200.computercraft.core.util.Colour;

import dev.kondrashka.ccutf8compat.config.CcUtf8CompatConfig;
import dev.kondrashka.ccutf8compat.access.CcUtf8TextBufferAccess;

/**
 * Prevents direct fixed-width rendering from drawing non-ASCII placeholder glyphs.
 */

@Mixin(value = DirectFixedWidthFontRenderer.class, remap = false)
public class DirectFixedWidthFontRendererMixin {

    @Overwrite(remap = false)
    public static void drawString(DirectFixedWidthFontRenderer.QuadEmitter emitter, float x, float y, TextBuffer text, TextBuffer textColour, Palette palette) {
        var enabled = CcUtf8CompatConfig.ENABLE_CC_UTF8_COMPAT.get();
        var textAccess = (CcUtf8TextBufferAccess) (Object) text;

        for (var i = 0; i < text.length(); i++) {
            var colour = palette.getRenderColours(15 - Terminal.getColour(textColour.charAt(i), Colour.BLACK));
            var codepoint = enabled ? textAccess.tfg$codePointAt(i) : text.charAt(i);

            if (enabled && (codepoint < 0 || codepoint > 255)) {
                continue;
            }

            tfg$drawChar(emitter, x + i * 6, y, codepoint, colour);
        }
    }

    @Invoker("drawChar")
    private static void tfg$drawChar(DirectFixedWidthFontRenderer.QuadEmitter emitter, float x, float y, int index, int colour) {
        throw new AssertionError();
    }
}
