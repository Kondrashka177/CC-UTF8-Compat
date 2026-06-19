package dev.kondrashka.ccutf8compat.mixins.common.cc_tweaked;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import dan200.computercraft.shared.computer.terminal.TerminalState;

/**
 * Exposes terminal state internals needed for UTF-8 synchronization.
 */

@Mixin(value = TerminalState.class, remap = false)
public interface TerminalStateAccessor {

    @Accessor("width")
    int ccUtf8$getWidth();

    @Accessor("height")
    int ccUtf8$getHeight();

    @Accessor("cursorX")
    int ccUtf8$getCursorX();

    @Accessor("cursorY")
    int ccUtf8$getCursorY();

    @Accessor("cursorBlink")
    boolean ccUtf8$getCursorBlink();

    @Accessor("cursorBgColour")
    int ccUtf8$getCursorBgColour();

    @Accessor("cursorFgColour")
    int ccUtf8$getCursorFgColour();
}
