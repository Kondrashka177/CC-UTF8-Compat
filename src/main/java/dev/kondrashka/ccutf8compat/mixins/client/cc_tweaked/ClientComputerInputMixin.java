package dev.kondrashka.ccutf8compat.mixins.client.cc_tweaked;

import dan200.computercraft.client.gui.ClientComputerInput;
import dan200.computercraft.client.network.ClientNetworking;
import dan200.computercraft.shared.network.server.KeyEventServerMessage;
import dev.kondrashka.ccutf8compat.access.CcUtf8ClientInputAccess;
import dev.kondrashka.ccutf8compat.config.CcUtf8CompatConfig;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ClientComputerInput.class, remap = false)
public class ClientComputerInputMixin implements CcUtf8ClientInputAccess {

    @Shadow
    @Final
    private AbstractContainerMenu menu;

    @Override
    public void ccUtf8$charTypedCodepoint(int codepoint) {
        if (!CcUtf8CompatConfig.ENABLE_CC_UTF8_COMPAT.get()) {
            return;
        }

        ClientNetworking.sendToServer(new KeyEventServerMessage(
                menu,
                KeyEventServerMessage.Action.CHAR,
                codepoint
        ));
    }
}