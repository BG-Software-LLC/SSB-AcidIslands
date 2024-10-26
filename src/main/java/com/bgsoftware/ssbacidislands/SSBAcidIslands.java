package com.bgsoftware.ssbacidislands;

import com.bgsoftware.ssbacidislands.config.SettingsHandler;
import com.bgsoftware.ssbacidislands.listener.WaterListener;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import com.bgsoftware.superiorskyblock.api.modules.PluginModule;
import org.bukkit.event.Listener;

public final class SSBAcidIslands extends PluginModule {

    private static final SuperiorCommand[] EMPTY_COMMANDS = new SuperiorCommand[0];

    private static SSBAcidIslands instance;

    private SuperiorSkyblock plugin;
    private SettingsHandler settingsHandler;

    public SSBAcidIslands() {
        super("acidislands", "Ome_R");
        instance = this;
    }

    @Override
    public void onEnable(SuperiorSkyblock plugin) {
        this.plugin = plugin;
        settingsHandler = new SettingsHandler(this);
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
        return EMPTY_COMMANDS;
    }

    @Override
    public SuperiorCommand[] getSuperiorAdminCommands(SuperiorSkyblock superiorSkyblock) {
        return EMPTY_COMMANDS;
    }

    public SettingsHandler getSettings() {
        return settingsHandler;
    }

    public SuperiorSkyblock getPlugin() {
        return plugin;
    }

    public static SSBAcidIslands getModule() {
        return instance;
    }

}
