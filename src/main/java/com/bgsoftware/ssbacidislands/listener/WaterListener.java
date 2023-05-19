package com.bgsoftware.ssbacidislands.listener;

import com.bgsoftware.ssbacidislands.damage.AcidDamageTask;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class WaterListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e){
        Location from = e.getFrom(), to = e.getTo();

        if(to == null || (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()))
            return;

        GameMode gameMode = e.getPlayer().getGameMode();

        if(gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR)
            return;

        checkForWater(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e){
        GameMode gameMode = e.getPlayer().getGameMode();

        if(gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR)
            return;

        checkForWater(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent e){
        AcidDamageTask.stopTask(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent e){
        AcidDamageTask.stopTask(e.getEntity());
    }

    private void checkForWater(Player player){
        Block playerBlock = player.getLocation().getBlock();
        if(playerBlock.getType().name().contains("WATER") || playerBlock.getRelative(BlockFace.DOWN).getType().name().contains("WATER"))
            AcidDamageTask.createTask(player);
    }

}
