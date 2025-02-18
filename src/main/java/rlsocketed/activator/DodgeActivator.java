package rlsocketed.activator;

import com.elenai.elenaidodge.api.DodgeEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rlsocketed.callback.GenericEventCallback;
import socketed.common.capabilities.effectscache.CapabilityEffectsCacheHandler;
import socketed.common.capabilities.effectscache.ICapabilityEffectsCache;
import socketed.common.socket.gem.effect.GenericGemEffect;
import socketed.common.socket.gem.effect.activatable.ActivatableGemEffect;
import socketed.common.socket.gem.effect.activatable.activator.GenericActivator;
import socketed.common.socket.gem.effect.activatable.callback.IEffectCallback;
import socketed.common.socket.gem.effect.activatable.condition.GenericCondition;

import javax.annotation.Nullable;

public class DodgeActivator extends GenericActivator {
    public static final String TYPE_NAME = "Dodge";

    public DodgeActivator(@Nullable GenericCondition condition) {
        super(condition);
    }

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

    public void attemptDodgeActivation(ActivatableGemEffect effect, IEffectCallback callback, EntityPlayer player) {
        if(this.testCondition(callback, player, player))
            effect.affectTargets(callback, player, player);
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {

        /**
         * Event handling for DodgeActivators
         */
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void onDodge(DodgeEvent.ServerDodgeEvent event) {
            //Dodges can and will get cancelled by the mod itself in various scenarios
            if(event.isCanceled()) return;

            EntityPlayer player = event.getPlayer();

            ICapabilityEffectsCache cachedEffects = player.getCapability(CapabilityEffectsCacheHandler.CAP_EFFECTS_CACHE, null);
            if(cachedEffects == null) return;

            for(GenericGemEffect effect : cachedEffects.getActiveEffects()) {
                if(effect instanceof ActivatableGemEffect) {
                    ActivatableGemEffect activatableGemEffect = (ActivatableGemEffect)effect;
                    if(activatableGemEffect.getActivator() instanceof DodgeActivator) {
                        DodgeActivator activator = (DodgeActivator)activatableGemEffect.getActivator();
                        GenericEventCallback callable = new GenericEventCallback(event);
                        activator.attemptDodgeActivation(activatableGemEffect, callable, player);
                    }
                }
            }
        }
    }
}
