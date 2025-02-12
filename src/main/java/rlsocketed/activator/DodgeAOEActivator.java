package rlsocketed.activator;

import com.google.gson.annotations.SerializedName;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rlsocketed.RLSocketed;
import socketed.common.socket.gem.effect.activatable.ActivatableGemEffect;

import java.util.List;

public class DodgeAOEActivator extends DodgeActivator {
    public static final String TYPE_NAME = "Dodge AOE";

    @SerializedName("Block Range")
    protected final Integer blockRange;

    @SerializedName("Affects Self")
    protected Boolean affectsSelf;

    public DodgeAOEActivator(int blockRange, boolean affectsSelf){
        this.blockRange = blockRange;
        this.affectsSelf = affectsSelf;
    }

    public void attemptDodgeActivation(ActivatableGemEffect effect, EntityPlayer player) {
        if(this.getAffectsSelf()) effect.performEffect(player, player);
        List<EntityLivingBase> entitiesNearby = player.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getPosition()).grow(this.getBlockRange()));
        for(EntityLivingBase entity : entitiesNearby) {
            if(entity != player) effect.performEffect(player, entity);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTooltipString() {
        return I18n.format("rlsocketed.tooltip.activator.dodgeaoe", this.getBlockRange());
    }

    @Override
    public String getTypeName() {
        return TYPE_NAME;
    }

    /**
     * @return the radius in blocks this activator will check for entities to affect
     */
    public int getBlockRange() {
        return this.blockRange;
    }

    /**
     * @return if this activator should affect the player source of the effect
     */
    public boolean getAffectsSelf() {
        return this.affectsSelf;
    }

    /**
     * BlockRange: Required
     * AffectsSelf: Optional, default false
     */
    @Override
    public boolean validate() {
        if(this.affectsSelf == null) this.affectsSelf = false;

        if(super.validate()) {
            if(this.blockRange == null) RLSocketed.LOGGER.warn("Invalid " + this.getTypeName() + " Activator, block range must be defined");
            else if(this.blockRange < 1) RLSocketed.LOGGER.warn("Invalid " + this.getTypeName() + " Activator, block range must be greater than 0");
            else return true;
        }
        return false;
    }
}
