package com.bgsoftware.ssbacidislands;

import org.bukkit.plugin.java.JavaPlugin;

public final class SSBAcidIslands extends JavaPlugin {

    private static SSBAcidIslands plugin;

    private Settings settings;

    @Override
    public void onEnable() {
        plugin = this;

        settings = new Settings(this);

        getServer().getPluginManager().registerEvents(new WaterListener(), this);
    }

    public Settings getSettings() {
        return settings;
    }

    public static SSBAcidIslands getPlugin() {
        return plugin;
    }

}
