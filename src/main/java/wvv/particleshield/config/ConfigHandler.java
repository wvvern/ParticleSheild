package wvv.particleshield.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    private static Configuration config;

    public static int shieldSize;

    public static void loadConfig(File file) {
        config = new Configuration(file);
        config.load();
        shieldSize = config.get("ParticleShield", "shieldSize", 3, "Minimum distance between the player and any visible particle.").getInt();
    }

    public static void saveConfig() {
        config.get("ParticleShield", "shieldSize", 3, "Minimum distance between the player and any visible particle.").setValue(shieldSize);

        if (config.hasChanged()) {
            config.save();
        }
    }

}
