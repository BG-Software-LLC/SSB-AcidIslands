package com.bgsoftware.ssbacidislands.config;

import com.bgsoftware.ssbacidislands.SSBAcidIslands;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public final class SettingsHandler {

    public final double firstDamage;
    public final double damageMultiplier;
    public final boolean globalWaterDamage;

    public SettingsHandler(SSBAcidIslands module){
        File file = new File(module.getModuleFolder(), "config.yml");

        if(!file.exists())
            module.saveResource("config.yml");

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        firstDamage = cfg.getDouble("first-damage", 0.5D);
        damageMultiplier = cfg.getDouble("damage-multiplier", 1.2D);
        globalWaterDamage = cfg.getBoolean("global-water-damage", true);
    }

}
