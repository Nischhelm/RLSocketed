package rlsocketed;

import com.charles445.simpledifficulty.api.temperature.TemperatureRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rlsocketed.activator.DodgeActivator;
import rlsocketed.config.DefaultJsonAddons;
import rlsocketed.config.ForgeConfig;
import rlsocketed.gemeffects.DodgeForceEffect;
import rlsocketed.gemeffects.TemperatureEffect;
import rlsocketed.gemeffects.ThirstEffect;
import socketed.api.util.ItemMatcherType;
import socketed.api.util.SocketedUtil;

@Mod(
        modid = RLSocketed.MODID,
        name = RLSocketed.MODNAME,
        version = RLSocketed.VERSION,
        dependencies = "required-after:socketed;" +
                       "after:socketedstandardexpansion;" +
                       "required-after:fermiumbooter;"
)
public class RLSocketed {

    public static final String MODID = "rlsocketed";
    public static final String MODNAME = "RLSocketed";
    public static final String VERSION = "1.0.0"; 
    public static Logger LOGGER = LogManager.getLogger();

    @Mod.Instance(MODID)
    public static RLSocketed instance;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        ForgeConfig.init();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        if(Loader.isModLoaded("elenaidodge")) {
            MinecraftForge.EVENT_BUS.register(DodgeActivator.EventHandler.class);
            SocketedUtil.registerActivator(DodgeActivator.TYPE_NAME, DodgeActivator.class, RLSocketed.MODID);
            SocketedUtil.registerEffectType(DodgeForceEffect.TYPE_NAME, DodgeForceEffect.class, RLSocketed.MODID);
        }
        if(Loader.isModLoaded("simpledifficulty")) {
            SocketedUtil.registerEffectType(TemperatureEffect.TYPE_NAME, TemperatureEffect.class, RLSocketed.MODID);
            SocketedUtil.registerEffectType(ThirstEffect.TYPE_NAME, ThirstEffect.class, RLSocketed.MODID);
            TemperatureRegistry.registerModifier(new TemperatureEffect.Modifier());
        }

        new ItemTypeContainer("mujmajnkraftsbettersurvival", "BS_WEAPON",
                "com.mujmajnkraft.bettersurvival.items.ItemDagger",
                "com.mujmajnkraft.bettersurvival.items.ItemNunchaku",
                "com.mujmajnkraft.bettersurvival.items.ItemHammer").register();
        new ItemTypeContainer("spartanweaponry", "SW_CROSSBOW",
                "com.oblivioussp.spartanweaponry.item.ItemCrossbow").register();
        new ItemTypeContainer("defiledlands", "DEFILED",
                "lykrast.defiledlands.common.item.ItemConcussionSmasher",
                "lykrast.defiledlands.common.item.ItemUmbraBlaster",
                "lykrast.defiledlands.common.item.ItemTears",
                "lykrast.defiledlands.common.item.ItemRavager").register();
        new ItemTypeContainer("oe", "TRIDENT",
                "com.sirsquidly.oe.items.ItemTrident").register();

        DefaultJsonAddons.initializeBuiltinEntries();
    }

    public static class ItemTypeContainer {
        String typeName, dependencyModid;
        String[] classNames;
        public ItemTypeContainer(String dependencyModid, String typeName, String... classNames) {
            this.typeName = typeName;
            this.classNames = classNames;
            this.dependencyModid = dependencyModid;
        }

        public void register(){
            if(!Loader.isModLoaded(dependencyModid) || !ForgeConfig.itemTypeRolls.containsKey(typeName)) return;
            try {
                SocketedUtil.registerForcedItemType(typeName, ItemMatcherType.CLASS.createMatcher(String.join(",", classNames)), ForgeConfig.itemTypeRolls.get(typeName));
            } catch(IllegalArgumentException e) {
                LOGGER.warn("Could not register socketable item type {}, none of its item classes were found", typeName);
            }
        }
    }
}
