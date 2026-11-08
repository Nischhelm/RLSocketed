package rlsocketed.mixin.reccomplex;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import ivorius.reccomplex.world.gen.feature.StructureGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import rlsocketed.compat.ReccomplexCompat;

import javax.annotation.Nullable;

@Mixin(StructureGenerator.class)
public abstract class StructureGeneratorMixin {
    @Shadow(remap = false) @Nullable public abstract String structureID();

    @WrapMethod(method = "generate", remap = false)
    private StructureGenerator.GenerationResult rlsocketed_trackCurrentStructure(Operation<StructureGenerator.GenerationResult> original) {
        String previous = ReccomplexCompat.currStructure;
        ReccomplexCompat.currStructure = this.structureID();
        try {
            return original.call();
        } finally {
            // Make sure this gets set back for nested structs and crashes during gen
            ReccomplexCompat.currStructure = previous;
        }
    }
}
