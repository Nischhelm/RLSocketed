package rlsocketed.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rlsocketed.RLSocketed;

import java.util.HashMap;
import java.util.Map;

@Config(modid = RLSocketed.MODID, name = RLSocketed.MODNAME)
public class ForgeConfig {

    @Config.Comment({
            "Remove or modify rolls of these socketable item types.",
            "Adding entries won't do anything.",
            "Item Types are implemented internally using regex as follows.",
            " BS_WEAPON: mujmajnkraftsbettersurvival:item.*(dagger|nunchaku|hammer|)",
            " SW_CROSSBOW: spartan(defiled|fire|weaponry):crossbow_\\w+",
            " DEFILED: defiledlands:(concussion_smasher|umbra_blaster|tears_flame|tears_shulker|the_ravager)",
            " TRIDENT: oe:trident"
    })
    @Config.Name("Custom Socketable Item Type Rolls")
    public static Map<String, Integer> itemTypeRolls = new HashMap<String, Integer>(){{
        put("BS_WEAPON", 5);
        put("SW_CROSSBOW", 3);
        put("TRIDENT", 2);
        put("DEFILED", 1);
    }};

    @Config.Name("Infernal Mobs")
    public static InfernalMobsConfig infernal = new InfernalMobsConfig();

    public static class InfernalMobsConfig {
        @Config.Comment("Base chance for items dropped by elite tier infernalmobs to attempt to roll with sockets")
        @Config.Name("Elite Socket Chance")
        @Config.RangeDouble(min = 0, max = 1)
        public float chanceElite = 0.2F;

        @Config.Comment("How many sockets an item dropped by elite tier infernalmobs can roll at maximum")
        @Config.Name("Elite Max Sockets")
        @Config.RangeInt(min = 0)
        public int maxSocketsOnElite = 2;

        @Config.Comment("Base chance for items dropped by ultra tier infernalmobs to attempt to roll with sockets")
        @Config.Name("Ultra Socket Chance")
        @Config.RangeDouble(min = 0, max = 1)
        public float chanceUltra = 0.4F;

        @Config.Comment("How many sockets an item dropped by ultra tier infernalmobs can roll at maximum")
        @Config.Name("Ultra Max Sockets")
        @Config.RangeInt(min = 0)
        public int maxSocketsOnUltra = 3;

        @Config.Comment("Base chance for items dropped by infernal tier infernalmobs to attempt to roll with sockets")
        @Config.Name("Infernal Socket Chance")
        @Config.RangeDouble(min = 0, max = 1)
        public float chanceInfernal = 0.6F;

        @Config.Comment("How many sockets an item dropped by infernal tier infernalmobs can roll at maximum")
        @Config.Name("Infernal Max Sockets")
        @Config.RangeInt(min = 0)
        public int maxSocketsOnInfernal = 4;

        public int getMaxSockets(int tier){
            return tier == 0 ? maxSocketsOnElite :
                   tier == 1 ? maxSocketsOnUltra :
                               maxSocketsOnInfernal;
        }
        public float getChance(int tier){
            return tier == 0 ? chanceElite :
                   tier == 1 ? chanceUltra :
                               chanceInfernal;
        }
    }

    @Config.Name("Doomlike Dungeons")
    public static DoomlikeConfig dldungeons = new DoomlikeConfig();

    public static class DoomlikeConfig {
        @Config.Comment("Base chance for normal Doomlike Dungeon loot to attempt to roll with sockets")
        @Config.Name("Socket Chance")
        @Config.RangeDouble(min = 0, max = 1)
        public float chance = 0.2F;

        @Config.Comment("How many sockets normal Doomlike Dungeon loot can roll at maximum")
        @Config.Name("Max Sockets")
        @Config.RangeInt(min = 0)
        public int maxSockets = 4;

        @Config.Comment("Base chance for boss and special Doomlike Dungeon loot to attempt to roll with sockets")
        @Config.Name("Socket Chance Special")
        @Config.RangeDouble(min = 0, max = 1)
        public float chanceSpecial = 0.2F;

        @Config.Comment("How many sockets boss and special Doomlike Dungeon loot can roll at maximum")
        @Config.Name("Max Sockets Special")
        @Config.RangeInt(min = 0)
        public int maxSocketsSpecial = 4;
    }

    public static void reset() {
    }

    @Mod.EventBusSubscriber(modid = socketed.Socketed.MODID)
    private static class EventHandler {
        
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(RLSocketed.MODID)) {
                ConfigManager.sync(RLSocketed.MODID, Config.Type.INSTANCE);
                ForgeConfig.reset();
            }
        }
    }
}