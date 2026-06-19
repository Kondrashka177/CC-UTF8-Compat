package dev.kondrashka.ccutf8compat.access;

/**
 * Exposes UTF-8 codepoints stored alongside CC:Tweaked's terminal text buffer
 */

public interface CcUtf8TextBufferAccess {

    int ccUtf8$codePointAt(int index);

    void ccUtf8$setCodePoint(int index, int codepoint);
}
