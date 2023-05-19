package com.bgsoftware.ssbacidislands.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;

import java.util.EnumSet;
import java.util.Optional;

public class PlayerUtils {

    private static final EnumSet<Material> WATER_TYPES = createWaterMaterials();

    private PlayerUtils() {

    }

    public static boolean isInWater(Player player) {
        Block playerBlock = player.getLocation().getBlock();

        if (player.getVehicle() instanceof Boat) {
            Block boatBlock = playerBlock.getRelative(BlockFace.DOWN);
            if (isBlockInWater(boatBlock))
                return true;
        }

        return isBlockInWater(playerBlock) || isBlockInWater(player.getEyeLocation().getBlock());
    }

    private static boolean isBlockInWater(Block block) {
        return WATER_TYPES.contains(block.getType());
    }

    private static EnumSet<Material> createWaterMaterials() {
        EnumSet<Material> waterMaterials = EnumSet.noneOf(Material.class);
        Optional.ofNullable(Material.matchMaterial("WATER")).ifPresent(waterMaterials::add);
        Optional.ofNullable(Material.matchMaterial("STATIONARY_WATER")).ifPresent(waterMaterials::add);
        return waterMaterials;
    }

}
