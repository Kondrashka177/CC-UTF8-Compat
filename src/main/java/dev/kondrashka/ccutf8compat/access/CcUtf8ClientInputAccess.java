package dev.kondrashka.ccutf8compat.access;

/**
 * Exposes raw UTF-8 client input data used by keyboard handling.
 */

public interface CcUtf8ClientInputAccess {

    void ccUtf8$charTypedCodepoint(int codepoint);
}
