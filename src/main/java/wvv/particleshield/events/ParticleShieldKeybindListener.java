package wvv.particleshield.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import wvv.particleshield.config.ConfigHandler;

public class ParticleShieldKeybindListener {

    private final KeyBinding toggleKeybind = new KeyBinding("Toggle Particle Shield", Keyboard.KEY_J, "Particle Shield");

    public ParticleShieldKeybindListener() {
        ClientRegistry.registerKeyBinding(toggleKeybind);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (toggleKeybind.isPressed()) {
            ConfigHandler.isShieldActive = !ConfigHandler.isShieldActive;
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Particle Shield: " + (ConfigHandler.isShieldActive ? "Activated" : "Deactivated")));
            ConfigHandler.saveConfig();
        }
    }

}
