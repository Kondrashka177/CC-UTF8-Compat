package dev.kondrashka.ccutf8compat.mixins.common.cc_tweaked;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dan200.computercraft.shared.peripheral.diskdrive.DiskDrivePeripheral;

import dev.kondrashka.ccutf8compat.config.CcUtf8CompatConfig;

/**
 * Converts disk labels between Lua UTF-8 byte strings and Java Unicode strings.
 */

@Mixin(value = DiskDrivePeripheral.class, remap = false)
public class DiskDrivePeripheralMixin {

    @Redirect(method = "setDiskLabel", at = @At(value = "INVOKE", target = "Ljava/util/Optional;map(Ljava/util/function/Function;)Ljava/util/Optional;"), remap = false)
    private Optional<String> ccUtf8$normaliseDiskLabelUtf8(
            Optional<String> label,
            Function<? super String, ? extends String> mapper) {
        if (!CcUtf8CompatConfig.ENABLE_CC_UTF8_COMPAT.get()) {
            return label.map(mapper);
        }

        return label.map(value -> ccUtf8$normaliseLabelUtf8(ccUtf8$decodeUtf8OrLegacy(value)));
    }

    @Inject(method = "getDiskLabel", at = @At("RETURN"), cancellable = true, remap = false)
    private void ccUtf8$getDiskLabelUtf8(CallbackInfoReturnable<Object[]> cir) {
        if (!CcUtf8CompatConfig.ENABLE_CC_UTF8_COMPAT.get()) {
            return;
        }

        var result = cir.getReturnValue();

        if (result == null || result.length == 0 || !(result[0] instanceof String label)) {
            return;
        }

        cir.setReturnValue(new Object[] { ccUtf8$encodeUtf8ForLua(label) });
    }

    @Unique
    private static String ccUtf8$decodeUtf8OrLegacy(String text) {
        var bytes = new byte[text.length()];

        for (var i = 0; i < text.length(); i++) {
            var c = text.charAt(i);

            if (c > 255) {
                return text;
            }

            bytes[i] = (byte) c;
        }

        var decoder = StandardCharsets.UTF_8
                .newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT);

        try {
            return decoder.decode(ByteBuffer.wrap(bytes)).toString();
        } catch (CharacterCodingException ignored) {
            return text;
        }
    }

    @Unique
    private static String ccUtf8$encodeUtf8ForLua(String text) {
        var bytes = text.getBytes(StandardCharsets.UTF_8);
        var out = new StringBuilder(bytes.length);

        for (var b : bytes) {
            out.append((char) (b & 0xFF));
        }

        return out.toString();
    }

    @Unique
    private static String ccUtf8$normaliseLabelUtf8(String label) {
        var out = new StringBuilder();
        var count = 0;

        var iterator = label.codePoints().iterator();

        while (iterator.hasNext() && count < 32) {
            var codepoint = iterator.nextInt();

            if (codepoint == 0 || codepoint == '\r' || codepoint == '\n') {
                break;
            }

            if (codepoint != 167) {
                out.appendCodePoint(codepoint);
                count++;
            }
        }

        return out.toString();
    }
}
