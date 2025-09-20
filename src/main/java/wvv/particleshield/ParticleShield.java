package wvv.particleshield;

import wvv.particleshield.config.ConfigHandler;
import wvv.particleshield.events.ParticleShieldKeybindListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = ParticleShield.MODID,
        name = ParticleShield.MODNAME,
        version = ParticleShield.VERSION)
public class ParticleShield { // select ExampleMod and hit shift+F6 to rename it

    public static final String MODID = "particleshield";    // the id of your mod, it should never change, it is used by forge and servers to identify your mods
    public static final String MODNAME = "Particle Shield"; // the name of your mod
    public static final String VERSION = "1.0";           // the current version of your mod

    // this method is one entry point of you mod
    // it is called by forge when minecraft is starting
    // it is called before the other methods below
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new ParticleShieldKeybindListener());


    }
}
