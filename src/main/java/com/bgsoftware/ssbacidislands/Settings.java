package com.bgsoftware.ssbacidislands;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public final class Settings {

    public final double firstDamage;
    public final double damageMultiplier;

    public Settings(SSBAcidIslands plugin){
        File file = new File(plugin.getDataFolder(), "config.yml");

        if(!file.exists())
            plugin.saveResource("config.yml");

        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        firstDamage = cfg.getDouble("first-damage");
        damageMultiplier = cfg.getDouble("damage-multiplier");
    }

}
