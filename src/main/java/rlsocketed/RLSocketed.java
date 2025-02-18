package rlsocketed;

import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.ItemEquipmentPart;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rlsocketed.config.DefaultJsonAddons;
import rlsocketed.activator.DodgeActivator;
import rlsocketed.gemeffects.DodgeForceEffect;
import socketed.common.util.SocketedUtil;

@Mod(
        modid = RLSocketed.MODID,
        name = RLSocketed.MODNAME,
        version = RLSocketed.VERSION,
        dependencies = "required-after:socketed"
)
public class RLSocketed {

    public static final String MODID = "rlsocketed";
    public static final String MODNAME = "RLSocketed";
    public static final String VERSION = "1.0.0";
    public static Logger LOGGER = LogManager.getLogger();

    @Mod.Instance(MODID)
    public static RLSocketed instance;

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        if(Loader.isModLoaded("elenaidodge")) {
            SocketedUtil.registerActivator(DodgeActivator.TYPE_NAME, DodgeActivator.class, RLSocketed.MODID);
            SocketedUtil.registerEffectType(DodgeForceEffect.TYPE_NAME, DodgeForceEffect.class, RLSocketed.MODID);
            DefaultJsonAddons.initializeBuiltinEntries();
        }
        if(Loader.isModLoaded("lycanitesmobs")) {
            SocketedUtil.registerForcedItemType("LYCANITETOOLPART", item -> item instanceof ItemEquipmentPart, 0); //Sockets rolls handled in BaseCreatureEntityMixin
            SocketedUtil.registerForcedItemType("LYCANITETOOL", item -> item instanceof ItemEquipment, 0);
        }
    }
}