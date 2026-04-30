package rlsocketed;

import fermiumbooter.FermiumRegistryAPI;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class RLSocketedPlugin implements IFMLLoadingPlugin {

    public RLSocketedPlugin() {
        MixinBootstrap.init();
        FermiumRegistryAPI.enqueueMixin(true, "mixins.rlsocketed.lycanitesmobs.json", () -> Loader.isModLoaded("lycanitesmobs"));
        FermiumRegistryAPI.enqueueMixin(true, "mixins.rlsocketed.infernalmobs.json", () -> Loader.isModLoaded("infernalmobs"));
        FermiumRegistryAPI.enqueueMixin(true, "mixins.rlsocketed.dldungeons.json", () -> Loader.isModLoaded("dldungeonsjbg"));
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) { }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}