package wvv.particleshield.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import wvv.particleshield.config.ConfigGui;

public class ParticleShieldKeybindListener {

    private final KeyBinding configKeybind = new KeyBinding("Open Particle Shield Config", Keyboard.KEY_J, "Particle Shield");

    public ParticleShieldKeybindListener() {
        ClientRegistry.registerKeyBinding(configKeybind);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (configKeybind.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(new ConfigGui(null));
            }
        }
    }

}
