package rlsocketed.gemeffects;

import com.charles445.simpledifficulty.api.temperature.ITemperatureModifier;
import com.google.gson.annotations.SerializedName;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import rlsocketed.RLSocketed;
import socketed.Socketed;
import socketed.api.common.capabilities.effectscache.CapabilityEffectsCacheHandler;
import socketed.api.common.capabilities.effectscache.ICapabilityEffectsCache;
import socketed.api.socket.gem.effect.GenericGemEffect;
import socketed.api.socket.gem.effect.slot.ISlotType;

public class TemperatureEffect extends GenericGemEffect {
    public static final String TYPE_NAME = "Temperature";

    @SerializedName("Amount")
    public Integer amount;

    public TemperatureEffect(ISlotType slotType, int amount) {
        super(slotType);
        this.amount = amount;
    }

    @Override
    public String getTooltip(boolean b) {
        return I18n.format("rlsocketed.tooltip.effect.temperature." +(amount > 0 ? "pos" : "neg"), Math.abs(amount));
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    @Override
    public boolean validate() {
        if (!super.validate()) return false;
        if (this.amount == null) {
            Socketed.LOGGER.warn("Invalid {} Effect, amount is null", this.getTypeName());
            return false;
        } else {
            return true;
        }
    }

    // Sums all active temp gem effects
    public static class Modifier implements ITemperatureModifier {
        @Override
        public float getPlayerInfluence(EntityPlayer player) {
            ICapabilityEffectsCache cachedEffects = player.getCapability(CapabilityEffectsCacheHandler.CAP_EFFECTS_CACHE, null);
            if(cachedEffects == null) return 0;

            float sum = 0;
            for(GenericGemEffect effect : cachedEffects.getActiveEffects())
                if(effect instanceof TemperatureEffect)
                    sum += ((TemperatureEffect) effect).amount;
            return sum;
        }

        @Override
        public float getWorldInfluence(World world, BlockPos pos) {
            return 0;
        }

        @Override
        public String getName() {
            return RLSocketed.MODID + ":gems";
        }
    }
}
