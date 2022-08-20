package com.bgsoftware.ssbacidislands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SSBAcidIslands extends PluginModule {

    private static SSBAcidIslands instance;
    private static JavaPlugin javaPlugin;

    private Settings settings;

    public SSBAcidIslands() {
        super("acidislands", "Ome_R");
        instance = this;
    }

    @Override
    public void onEnable(SuperiorSkyblock plugin) {
        javaPlugin = (JavaPlugin) plugin;
        settings = new Settings(this);
    }

    @Override
    public void onReload(SuperiorSkyblock plugin) {

    }

    @Override
    public void onDisable(SuperiorSkyblock plugin) {

    }

    @Override
    public Listener[] getModuleListeners(SuperiorSkyblock superiorSkyblock) {
        return new Listener[]{new WaterListener()};
    }

    @Override
    public SuperiorCommand[] getSuperiorCommands(SuperiorSkyblock superiorSkyblock) {
        return new SuperiorCommand[0];
    }

    @Override
    public SuperiorCommand[] getSuperiorAdminCommands(SuperiorSkyblock superiorSkyblock) {
        return new SuperiorCommand[0];
    }

    public Settings getSettings() {
        return settings;
    }

    public static SSBAcidIslands getPlugin() {
        return instance;
    }

    public static JavaPlugin getJavaPlugin() {
        return javaPlugin;
    }

}
