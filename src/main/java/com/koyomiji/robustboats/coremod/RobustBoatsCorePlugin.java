package com.koyomiji.robustboats.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"com.koyomiji.robustboats","com.koyomiji.asmine"})
public class RobustBoatsCorePlugin implements IFMLLoadingPlugin {
    public static File coremodLocation;
    public static File mcLocation;
    public static ArrayList<String> coremodList;
    public static Boolean runtimeDeobfuscationEnabled;
    public static Logger logger = LogManager.getLogger("RobustBoats");

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"com.koyomiji.robustboats.coremod.RobustBoatsClassTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        if (data.containsKey("coremodLocation")) {
            coremodLocation = (File) data.get("coremodLocation");
        }

        if (data.containsKey("mcLocation")) {
            mcLocation = (File) data.get("mcLocation");
        }

        if (data.containsKey("coremodList")) {
            coremodList = (ArrayList<String>) data.get("coremodList");
        }

        if (data.containsKey("runtimeDeobfuscationEnabled")) {
            runtimeDeobfuscationEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
