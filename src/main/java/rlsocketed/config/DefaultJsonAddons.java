package rlsocketed.config;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.Loader;
import rlsocketed.activator.DodgeActivator;
import rlsocketed.gemeffects.DodgeForceEffect;
import rlsocketed.gemeffects.TemperatureEffect;
import rlsocketed.gemeffects.ThirstEffect;
import socketed.api.socket.gem.GemType;
import socketed.api.socket.gem.effect.GenericGemEffect;
import socketed.api.socket.gem.effect.activatable.activator.GenericActivator;
import socketed.api.socket.gem.effect.activatable.target.GenericTarget;
import socketed.api.socket.gem.effect.slot.ISlotType;
import socketed.api.socket.gem.effect.slot.SocketedSlotTypes;
import socketed.common.config.DefaultJsonConfig;
import socketed.common.socket.gem.effect.activatable.activator.PassiveActivator;
import socketed.common.socket.gem.effect.activatable.condition.ChanceCondition;
import socketed.common.socket.gem.effect.activatable.target.SelfAOETarget;
import socketed.common.socket.gem.effect.activatable.target.SelfTarget;
import socketed.common.socket.gem.filter.ItemFilter;
import socketedstandardexpansion.common.socket.gem.effect.activatable.PotionGemEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DefaultJsonAddons {
    public static void initializeBuiltinEntries() {
        boolean hasExpansion = Loader.isModLoaded("socketedstandardexpansion");

        if(Loader.isModLoaded("elenaidodge")) {
            if(hasExpansion) {
                DefaultJsonConfig.registerDefaultGemType("sugar", new GemType("rlsocketed.tooltip.default.sugar", 0, TextFormatting.WHITE,
                        Collections.singletonList(StandardExpansion.potion(
                                SocketedSlotTypes.FEET,
                                new DodgeActivator(null),
                                Collections.singletonList(new SelfTarget(null)),
                                "minecraft:speed",
                                2,
                                200,
                                null
                        )),
                        Collections.singletonList(new ItemFilter("minecraft:sugar", 0, false))));
            }

            List<GenericGemEffect> blazeEffects = new ArrayList<>();
            if(hasExpansion) blazeEffects.add(StandardExpansion.potion(
                    SocketedSlotTypes.FEET,
                    new DodgeActivator(null),
                    Collections.singletonList(new SelfAOETarget(null, 3)),
                    "potioncore:explode",
                    0,
                    1,
                    null
            ));
            blazeEffects.add(new DodgeForceEffect(
                    SocketedSlotTypes.FEET,
                    new DodgeActivator(null),
                    Collections.singletonList(new SelfTarget(null)),
                    1.5D,
                    null
            ));
            DefaultJsonConfig.registerDefaultGemType("blaze_powder", new GemType("rlsocketed.tooltip.default.blaze_powder", 0, TextFormatting.RED,
                    blazeEffects,
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

            List<GenericGemEffect> waterEffects = new ArrayList<>();
            waterEffects.add(new ThirstEffect(
                    SocketedSlotTypes.BODY,
                    new PassiveActivator(null, 100),
                    Collections.singletonList(new SelfTarget(null)),
                    1,
                    "rlsocketed.tooltip.default.water.tooltip"
            ));
            if(hasExpansion) waterEffects.add(StandardExpansion.potion(
                    SocketedSlotTypes.BODY,
                    new PassiveActivator(new ChanceCondition(0.002F), 100),
                    Collections.singletonList(new SelfTarget(null)),
                    "simpledifficulty:parasites",
                    0,
                    600,
                    "rlsocketed.tooltip.default.water.tooltip.2"
            ));
            DefaultJsonConfig.registerDefaultGemType("water_bucket", new GemType("rlsocketed.tooltip.default.water", 0, TextFormatting.AQUA,
                    waterEffects,
                    Collections.singletonList(new ItemFilter("water_bucket", 0, false))));
        }
    }

    /**
     * Only loaded if socketedstandardexpansion is present, keeps its classes out of DefaultJsonAddons
     */
    private static class StandardExpansion {
        private static GenericGemEffect potion(ISlotType slotType, GenericActivator activator, List<GenericTarget> targets, String potionName, int amplifier, int duration, String tooltipKey) {
            return new PotionGemEffect(slotType, activator, targets, potionName, amplifier, duration, tooltipKey);
        }
    }
}
