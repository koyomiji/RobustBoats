package com.koyomiji.robustboats.coremod;

import com.koyomiji.robustboats.coremod.transforms.TransformEntityBoat;
import net.minecraft.launchwrapper.IClassTransformer;

public class RobustBoatsClassTransformer implements IClassTransformer {
    private boolean initialized = false;
    private ClassTransformManager applier;

    private void initialize() {
        applier = new ClassTransformManager();
        applier.addTransform(new TransformEntityBoat());
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!initialized) {
            initialize();
            initialized = true;
        }

        return applier.transform(name, transformedName, basicClass);
    }
}
