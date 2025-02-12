package rlsocketed.customactivators;

import com.elenai.elenaidodge.api.DodgeEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import socketed.common.capabilities.effectscache.CapabilityEffectsCacheHandler;
import socketed.common.capabilities.effectscache.ICapabilityEffectsCache;
import socketed.common.socket.gem.effect.GenericGemEffect;
import socketed.common.socket.gem.effect.activatable.ActivatableGemEffect;
import socketed.common.socket.gem.effect.activatable.activator.GenericActivator;

public class DodgeActivator extends GenericActivator {
    public static final String TYPE_NAME = "Dodge";

    @SideOnly(Side.CLIENT)
    @Override
    public String getTooltipString() {
        return I18n.format("rlsocketed.tooltip.activator.dodge");
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public boolean validate() {
        return true;
    }

    public void attemptDodgeActivation(ActivatableGemEffect effect, EntityPlayer player) {
        effect.performEffect(player, player);
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {

        /**
         * Event handling for DodgeActivators
         */
        @SubscribeEvent
        public static void onDodge(DodgeEvent.ServerDodgeEvent event) {
            EntityPlayer player = event.getPlayer();

            ICapabilityEffectsCache cachedEffects = player.getCapability(CapabilityEffectsCacheHandler.CAP_EFFECTS_CACHE, null);
            if(cachedEffects == null) return;

            for(GenericGemEffect effect : cachedEffects.getActiveEffects()) {
                if(effect instanceof ActivatableGemEffect) {
                    ActivatableGemEffect activatableGemEffect = (ActivatableGemEffect)effect;
                    if(activatableGemEffect.getActivatorType() instanceof DodgeActivator) {
                        DodgeActivator dodgeActivator = (DodgeActivator)activatableGemEffect.getActivatorType();
                        dodgeActivator.attemptDodgeActivation(activatableGemEffect, player);
                    }
                }
            }
        }
    }
}
