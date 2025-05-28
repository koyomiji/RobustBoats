package com.koyomiji.robustboats.coremod;

import org.objectweb.asm.tree.ClassNode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IClassNodeTransformer {
    ClassNode transform(ClassNode classNode);

    @Retention(RetentionPolicy.RUNTIME)
    @java.lang.annotation.Target({ElementType.TYPE})
    public @interface Target {
        String value();
    }
}
