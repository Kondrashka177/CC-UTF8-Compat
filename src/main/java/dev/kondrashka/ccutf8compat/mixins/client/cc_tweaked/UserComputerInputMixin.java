package dev.kondrashka.ccutf8compat.mixins.client.cc_tweaked;

import dan200.computercraft.core.input.ComputerInput;
import dan200.computercraft.core.input.UserComputerInput;
import dev.kondrashka.ccutf8compat.access.CcUtf8ClientInputAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = UserComputerInput.class, remap = false)
public class UserComputerInputMixin implements CcUtf8ClientInputAccess {

    @Shadow
    @Final
    private ComputerInput delegate;

    @Override
    public void ccUtf8$charTypedCodepoint(int codepoint) {
        if (delegate instanceof CcUtf8ClientInputAccess input) {
            input.ccUtf8$charTypedCodepoint(codepoint);
        }
    }
}