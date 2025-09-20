package wvv.particleshield.events;

import wvv.particleshield.automation.TrenchingManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class ParticleShieldKeybindListener {

    private final KeyBinding toggleKeybind = new KeyBinding("Toggle", Keyboard.KEY_J, "Auto Trencher");

    public ParticleShieldKeybindListener() {
        // you need to register your keybind for it to show up in the settings menu
        ClientRegistry.registerKeyBinding(toggleKeybind);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (toggleKeybind.isPressed()) {
            TrenchingManager manager = TrenchingManager.getInstance();
            if (manager.isRunning()) {
                manager.stop();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("AutoTrencher: Stopped."));
            } else {
                manager.start();
                Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("AutoTrencher: Started."));
            }
        }
    }

}
