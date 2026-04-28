package rlsocketed.loot;

import rlsocketed.config.ForgeConfig;
import socketed.common.loot.IItemCreationContext;

public enum InfernalMobsCreationContext implements IItemCreationContext {
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
    }
}