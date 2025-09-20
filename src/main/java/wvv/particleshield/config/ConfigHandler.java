package wvv.particleshield.config;

import net.minecraftforge.common.config.Configuration;
import wvv.particleshield.ParticleShield;

import java.io.File;

public class ConfigHandler {

    public static Configuration config;

    public static int shieldSize;
    public static boolean isShieldActive;

    public static void loadConfig(File file) {
        config = new Configuration(file);
        config.load();
        shieldSize = config.get(ParticleShield.MODID, "shieldSize", 2, "Minimum distance between the player and any visible particle.").getInt();
        isShieldActive = config.get(ParticleShield.MODID, "isShieldActive", true, "Whether the particle shield is active.").getBoolean();
        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void saveConfig() {
        config.get(ParticleShield.MODID, "shieldSize", 2, "Minimum distance between the player and any visible particle.").setValue(shieldSize);
        config.get(ParticleShield.MODID, "isShieldActive", true, "Whether the particle shield is active.").setValue(isShieldActive);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void syncConfigValues() {
        if (config != null) {
            config.load();
            shieldSize = config.get(ParticleShield.MODID, "shieldSize", 2, "Minimum distance between the player and any visible particle.").getInt();
            isShieldActive = config.get(ParticleShield.MODID, "isShieldActive", true, "Whether the particle shield is active.").getBoolean();
        }
    }

}
