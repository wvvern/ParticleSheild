package wvv.particleshield;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wvv.particleshield.commands.CommandShieldSize;
import wvv.particleshield.commands.CommandShieldToggle;
import wvv.particleshield.config.ConfigHandler;
import wvv.particleshield.events.ParticleRenderEventHandler;
import wvv.particleshield.events.ParticleShieldKeybindListener;

@Mod(
        modid = ParticleShield.MODID,
        name = ParticleShield.MODNAME,
        version = ParticleShield.VERSION,
        guiFactory = "wvv.particleshield.config.ParticleShieldGuiFactory")
public class ParticleShield {

    public static final String MODID = "particleshield";
    public static final String MODNAME = "Particle Shield";
    public static final String VERSION = "1.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ParticleShieldKeybindListener());
        MinecraftForge.EVENT_BUS.register(new ParticleRenderEventHandler());

        ClientCommandHandler.instance.registerCommand(new CommandShieldSize());
        ClientCommandHandler.instance.registerCommand(new CommandShieldToggle());
    }
}
