package rlsocketed.config;

import net.minecraft.util.text.TextFormatting;
import rlsocketed.activator.DodgeActivator;
import rlsocketed.gemeffects.DodgeForceEffect;
import socketed.common.config.DefaultJsonConfig;
import socketed.common.socket.gem.GemType;
import socketed.common.socket.gem.effect.activatable.PotionGemEffect;
import socketed.common.socket.gem.effect.activatable.target.SelfAOETarget;
import socketed.common.socket.gem.effect.activatable.target.SelfTarget;
import socketed.common.socket.gem.effect.slot.SocketedSlotTypes;
import socketed.common.socket.gem.filter.ItemFilter;

import java.util.Arrays;
import java.util.Collections;

public abstract class DefaultJsonAddons {
    public static void initializeBuiltinEntries() {
        DefaultJsonConfig.registerDefaultGemType("sugar", new GemType("rlsocketed.tooltip.default.sugar", 0, TextFormatting.WHITE,
                Collections.singletonList(new PotionGemEffect(
                        SocketedSlotTypes.FEET,
                        new DodgeActivator(null),
                        Collections.singletonList(new SelfTarget(null)),
                        "minecraft:speed",
                        2,
                        200
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
                            1
                        ),
                        new DodgeForceEffect(
                            SocketedSlotTypes.FEET,
                            new DodgeActivator(null),
                            Collections.singletonList(new SelfTarget(null)),
                            1.5D
                        )
                ),
                Collections.singletonList(new ItemFilter("blaze_powder", 0, false))));
    }
}












