package dev.kondrashka.ccutf8compat.access;

/**
 * Exposes UTF-8 terminal state data used by network synchronization.
 */

public interface CcUtf8TerminalStateAccess {

    int[] ccUtf8$getUtf8Text();

    byte[] ccUtf8$getUtf8Colours();

    byte[] ccUtf8$getUtf8Palette();

    void ccUtf8$setUtf8Data(int[] text, byte[] colours, byte[] palette);
}
