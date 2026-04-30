package rlsocketed.compat;

import rlsocketed.config.ForgeConfig;

public class ReccomplexCompat {
    public static String currStructure = null;
    public static String currLootTable = null;

    public static float getChance() {
        if(currLootTable != null && ForgeConfig.ReccomplexConfig.chancesByLootTable.containsKey(currLootTable))
            return ForgeConfig.ReccomplexConfig.chancesByLootTable.get(currLootTable);
        return ForgeConfig.ReccomplexConfig.chancesByStructure.getOrDefault(currStructure, ForgeConfig.reccomplex.chance);
    }

    public static int getMaxSockets() {
        if(currLootTable != null && ForgeConfig.ReccomplexConfig.maxSocketsByLootTable.containsKey(currLootTable))
            return ForgeConfig.ReccomplexConfig.maxSocketsByLootTable.get(currLootTable);
        return ForgeConfig.ReccomplexConfig.maxSocketsByStructure.getOrDefault(currStructure, ForgeConfig.reccomplex.maxSockets);
    }
}
