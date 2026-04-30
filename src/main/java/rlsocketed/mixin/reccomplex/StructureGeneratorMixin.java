package rlsocketed.mixin.reccomplex;

import ivorius.reccomplex.world.gen.feature.StructureGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rlsocketed.compat.ReccomplexCompat;

import javax.annotation.Nullable;

@Mixin(StructureGenerator.class)
public abstract class StructureGeneratorMixin {
    @Shadow(remap = false) @Nullable public abstract String structureID();

    @Inject(method = "generate", at = @At("HEAD"), remap = false)
    private void rlsocketed_saveCurrentStructure(CallbackInfoReturnable<StructureGenerator.GenerationResult> cir){
        ReccomplexCompat.currStructure = this.structureID();
    }

    @Inject(method = "generate", at = @At("TAIL"), remap = false)
    private void rlsocketed_clearCurrentStructure(CallbackInfoReturnable<StructureGenerator.GenerationResult> cir){
        ReccomplexCompat.currStructure = null;
    }
}
