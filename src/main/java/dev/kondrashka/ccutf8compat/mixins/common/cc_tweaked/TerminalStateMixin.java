package dev.kondrashka.ccutf8compat.mixins.common.cc_tweaked;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.network.FriendlyByteBuf;

import dan200.computercraft.shared.computer.terminal.TerminalState;

import dev.kondrashka.ccutf8compat.config.CcUtf8CompatConfig;
import dev.kondrashka.ccutf8compat.access.CcUtf8TerminalStateAccess;

/**
 * Saves and restores UTF-8 terminal data in terminal state packets.
 */

@Mixin(value = TerminalState.class, remap = false)
public class TerminalStateMixin implements CcUtf8TerminalStateAccess {

    @Unique
    private static final int ccUtf8$UTF8_MARKER = 0x54464755;

    @Unique
    private int[] ccUtf8$utf8Text;

    @Unique
    private byte[] ccUtf8$utf8Colours;

    @Unique
    private byte[] ccUtf8$utf8Palette;

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("RETURN"), remap = false)
    private void ccUtf8$readUtf8Data(FriendlyByteBuf buf, CallbackInfo ci) {
        if (!CcUtf8CompatConfig.ENABLE_CC_UTF8_COMPAT.get()) {
            return;
        }

        if (buf.readableBytes() < Integer.BYTES) {
            return;
        }

        var readerIndex = buf.readerIndex();

        if (buf.readInt() != ccUtf8$UTF8_MARKER) {
            buf.readerIndex(readerIndex);
            return;
        }

        var textLength = buf.readVarInt();
        var text = new int[textLength];

        for (var i = 0; i < textLength; i++) {
            text[i] = buf.readVarInt();
        }

        ccUtf8$utf8Text = text;
        ccUtf8$utf8Colours = buf.readByteArray();
        ccUtf8$utf8Palette = buf.readByteArray();
    }

    @Inject(method = "write", at = @At("TAIL"), remap = false)
    private void ccUtf8$writeUtf8Data(FriendlyByteBuf buf, CallbackInfo ci) {
        if (!CcUtf8CompatConfig.ENABLE_CC_UTF8_COMPAT.get()) {
            return;
        }

        if (ccUtf8$utf8Text == null || ccUtf8$utf8Colours == null || ccUtf8$utf8Palette == null) {
            return;
        }

        buf.writeInt(ccUtf8$UTF8_MARKER);

        buf.writeVarInt(ccUtf8$utf8Text.length);
        for (var codepoint : ccUtf8$utf8Text) {
            buf.writeVarInt(codepoint);
        }

        buf.writeByteArray(ccUtf8$utf8Colours);
        buf.writeByteArray(ccUtf8$utf8Palette);
    }

    @Inject(method = "size", at = @At("RETURN"), cancellable = true, remap = false)
    private void ccUtf8$size(CallbackInfoReturnable<Integer> cir) {
        if (!CcUtf8CompatConfig.ENABLE_CC_UTF8_COMPAT.get()) {
            return;
        }

        if (ccUtf8$utf8Text == null || ccUtf8$utf8Colours == null || ccUtf8$utf8Palette == null) {
            return;
        }

        cir.setReturnValue(cir.getReturnValue() + ccUtf8$utf8Text.length * Integer.BYTES + ccUtf8$utf8Colours.length + ccUtf8$utf8Palette.length + 8);
    }

    @Override
    public int[] ccUtf8$getUtf8Text() {
        return ccUtf8$utf8Text;
    }

    @Override
    public byte[] ccUtf8$getUtf8Colours() {
        return ccUtf8$utf8Colours;
    }

    @Override
    public byte[] ccUtf8$getUtf8Palette() {
        return ccUtf8$utf8Palette;
    }

    @Override
    public void ccUtf8$setUtf8Data(int[] text, byte[] colours, byte[] palette) {
        ccUtf8$utf8Text = text;
        ccUtf8$utf8Colours = colours;
        ccUtf8$utf8Palette = palette;
    }
}
