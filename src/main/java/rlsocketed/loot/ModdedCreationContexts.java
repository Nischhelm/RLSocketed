package rlsocketed.loot;

import rlsocketed.config.ForgeConfig;
import socketed.common.loot.IItemCreationContext;

public enum ModdedCreationContexts implements IItemCreationContext {
    INFERNALMOB_ELITE {
        @Override
        public float getChance() {
            return ForgeConfig.infernal.getChance(0);
        }

        @Override
        public int getMaxSockets() {
            return ForgeConfig.infernal.getMaxSockets(1);
        }
    },
    INFERNALMOB_ULTRA {
        @Override
        public float getChance() {
            return ForgeConfig.infernal.getChance(1);
        }

        @Override
        public int getMaxSockets() {
            return ForgeConfig.infernal.getMaxSockets(1);
        }
    },
    INFERNALMOB_INFERNAL {
        @Override
        public float getChance() {
            return ForgeConfig.infernal.getChance(2);
        }

        @Override
        public int getMaxSockets() {
            return ForgeConfig.infernal.getMaxSockets(2);
        }
    },
    DLDUNGEON_LOOT {
        @Override
        public float getChance() {
            return ForgeConfig.dldungeons.chance;
        }

        @Override
        public int getMaxSockets() {
            return ForgeConfig.dldungeons.maxSockets;
        }
    },
    DLDUNGEON_LOOT_SPECIAL {
        @Override
        public float getChance() {
            return ForgeConfig.dldungeons.chanceSpecial;
        }

        @Override
        public int getMaxSockets() {
            return ForgeConfig.dldungeons.maxSocketsSpecial;
        }
    }
}