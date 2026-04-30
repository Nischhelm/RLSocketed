package rlsocketed;

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
import socketed.api.util.SocketedUtil;

@Mod(
        modid = RLSocketed.MODID,
        name = RLSocketed.MODNAME,
        version = RLSocketed.VERSION,
        dependencies = "required-after:socketed;" +
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
            SocketedUtil.registerActivator(DodgeActivator.TYPE_NAME, DodgeActivator.class, RLSocketed.MODID);
            SocketedUtil.registerEffectType(DodgeForceEffect.TYPE_NAME, DodgeForceEffect.class, RLSocketed.MODID);
        }
        if(Loader.isModLoaded("simpledifficulty")) {
            SocketedUtil.registerEffectType(TemperatureEffect.TYPE_NAME, TemperatureEffect.class, RLSocketed.MODID);
            SocketedUtil.registerEffectType(ThirstEffect.TYPE_NAME, ThirstEffect.class, RLSocketed.MODID);
        }

        new ItemTypeContainer("mujmajnkraftsbettersurvival","BS_WEAPON","mujmajnkraftsbettersurvival:item.*(dagger|nunchaku|hammer|)").register();
        new ItemTypeContainer("spartanweaponry","BS_WEAPON","spartan(defiled|fire|weaponry):crossbow_\\w+").register();
        new ItemTypeContainer("defiledlands","BS_WEAPON","defiledlands:(concussion_smasher|umbra_blaster|tears_flame|tears_shulker|the_ravager)").register();
        new ItemTypeContainer("oe","TRIDENT","oe:trident").register();

        DefaultJsonAddons.initializeBuiltinEntries();
    }

    public static class ItemTypeContainer {
        String typeName, regex, dependencyModid;
        public ItemTypeContainer(String typeName, String regex, String dependencyModid) {
            this.typeName = typeName;
            this.regex = regex;
            this.dependencyModid = dependencyModid;
        }

        public void register(){
            if(Loader.isModLoaded(dependencyModid) && ForgeConfig.itemTypeRolls.containsKey(typeName))
                SocketedUtil.registerForcedItemType(typeName, regex, ForgeConfig.itemTypeRolls.get(typeName));
        }
    }
}
