package dev.kondrashka.ccutf8compat.mixins.common.cc_tweaked;

/**
 * Exposes UTF-8 codepoints stored alongside CC:Tweaked's text buffer.
 */

public interface CcUtf8TextBufferAccess {

    int tfg$codePointAt(int index);

    void tfg$setCodePoint(int index, int codepoint);
}