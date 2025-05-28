package com.koyomiji.robustboats.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassTransformManager implements IClassTransformer {
    private final Map<String, ArrayList<IClassNodeTransformer>> transforms = new HashMap<>();

    public ClassTransformManager() {
    }

    public void addTransform(IClassNodeTransformer transform) {
        for (Annotation annotation : transform.getClass().getDeclaredAnnotations()) {
            if (annotation instanceof IClassNodeTransformer.Target) {
                IClassNodeTransformer.Target target = (IClassNodeTransformer.Target)annotation;
                ArrayList<IClassNodeTransformer> transforms = this.transforms.getOrDefault(target.value(), null);

                if (transforms == null) {
                    transforms = new ArrayList<>();
                }

                transforms.add(transform);
                this.transforms.put(target.value(), transforms);
            }
        }
    }

    private List<IClassNodeTransformer> getTransforms(String className) {
        return transforms.getOrDefault(className, new ArrayList<>());
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        List<IClassNodeTransformer> transforms = getTransforms(transformedName);

        if (transforms.size() == 0) {
            return basicClass;
        }

        try {
            ClassNode node = new ClassNode();
            ClassReader reader = new ClassReader(basicClass);
            reader.accept(node, ClassReader.EXPAND_FRAMES);
            System.out.println("Transforming class " + transformedName + " with " + transforms.size() + " transforms");

            for (IClassNodeTransformer transform : transforms) {
                node = transform.transform(node);
            }

            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            node.accept(writer);
            return writer.toByteArray();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
