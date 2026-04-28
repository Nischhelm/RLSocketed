package rlsocketed.config;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import rlsocketed.activator.DodgeActivator;
import rlsocketed.gemeffects.DodgeForceEffect;
import rlsocketed.gemeffects.TemperatureEffect;
import rlsocketed.gemeffects.ThirstEffect;
import socketed.api.socket.gem.GemType;
import socketed.api.socket.gem.effect.slot.SocketedSlotTypes;
import socketed.common.config.DefaultJsonConfig;
import socketed.common.socket.gem.effect.activatable.PotionGemEffect;
import socketed.common.socket.gem.effect.activatable.activator.PassiveActivator;
import socketed.common.socket.gem.effect.activatable.condition.ChanceCondition;
import socketed.common.socket.gem.effect.activatable.target.SelfAOETarget;
import socketed.common.socket.gem.effect.activatable.target.SelfTarget;
import socketed.common.socket.gem.filter.ItemFilter;

import java.util.Arrays;
import java.util.Collections;

public abstract class DefaultJsonAddons {
    public static void initializeBuiltinEntries() {
        if(Loader.isModLoaded("elenaidodge")) {
            DefaultJsonConfig.registerDefaultGemType("sugar", new GemType("rlsocketed.tooltip.default.sugar", 0, TextFormatting.WHITE,
                    Collections.singletonList(new PotionGemEffect(
                            SocketedSlotTypes.FEET,
                            new DodgeActivator(null),
                            Collections.singletonList(new SelfTarget(null)),
                            "minecraft:speed",
                            2,
                            200,
                            null
                    )),
                    Collections.singletonList(new ItemFilter("minecraft:sugar", 0, false))));

            DefaultJsonConfig.registerDefaultGemType("blaze_powder", new GemType("rlsocketed.tooltip.default.blaze_powder", 0, TextFormatting.RED,
                    Arrays.asList(
                            new PotionGemEffect(
                                    SocketedSlotTypes.FEET,
                                    new DodgeActivator(null),
                                    Collections.singletonList(new SelfAOETarget(null, 3)),
                                    "potioncore:explode",
                                    0,
                                    1,
                                    null
                            ),
                            new DodgeForceEffect(
                                    SocketedSlotTypes.FEET,
                                    new DodgeActivator(null),
                                    Collections.singletonList(new SelfTarget(null)),
                                    1.5D,
                                    null
                            )
                    ),
                    Collections.singletonList(new ItemFilter("blaze_powder", 0, false))));
        }

        if(Loader.isModLoaded("simpledifficulty")) {
            DefaultJsonConfig.registerDefaultGemType("wool", new GemType("rlsocketed.tooltip.default.wool", 0, TextFormatting.GOLD,
                    Collections.singletonList(
                            new TemperatureEffect(
                                    SocketedSlotTypes.BODY,
                                    2
                            )
                    ),
                    Collections.singletonList(new ItemFilter("wool", 0, false))));

            DefaultJsonConfig.registerDefaultGemType("ice", new GemType("rlsocketed.tooltip.default.ice", 0, TextFormatting.AQUA,
                    Collections.singletonList(
                            new TemperatureEffect(
                                    SocketedSlotTypes.BODY,
                                    -2
                            )
                    ),
                    Collections.singletonList(new ItemFilter("ice", 0, false))));

            DefaultJsonConfig.registerDefaultGemType("water_bucket", new GemType("rlsocketed.tooltip.default.water", 0, TextFormatting.AQUA,
                    Arrays.asList(
                            new ThirstEffect(
                                    SocketedSlotTypes.BODY,
                                    new PassiveActivator(null, 100),
                                    Collections.singletonList(new SelfTarget(null)),
                                    1,
                                    "rlsocketed.tooltip.default.water.tooltip"
                            ),
                            new PotionGemEffect(
                                    SocketedSlotTypes.BODY,
                                    new PassiveActivator(new ChanceCondition(0.002F), 100),
                                    Collections.singletonList(new SelfTarget(null)),
                                    "simpledifficulty:parasites",
                                    0,
                                    600,
                                    "rlsocketed.tooltip.default.water.tooltip.2"
                            )
                    ),
                    Collections.singletonList(new ItemFilter("water_bucket", 0, false))));
        }
    }
}












