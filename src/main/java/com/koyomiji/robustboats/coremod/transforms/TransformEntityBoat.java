package com.koyomiji.robustboats.coremod.transforms;

import com.koyomiji.asmine.common.InsnStencils;
import com.koyomiji.asmine.query.ClassQuery;
import com.koyomiji.asmine.regex.compiler.Regexes;
import com.koyomiji.asmine.regex.compiler.code.CodeRegexes;
import com.koyomiji.asmine.stencil.Parameters;
import com.koyomiji.robustboats.coremod.IClassNodeTransformer;
import com.koyomiji.robustboats.coremod.MemberSymbol;
import com.koyomiji.robustboats.coremod.RobustBoatsCorePlugin;
import org.objectweb.asm.tree.ClassNode;

@IClassNodeTransformer.Target("net.minecraft.entity.item.EntityBoat")
public class TransformEntityBoat implements IClassNodeTransformer {
  private Object l0 = 0;

  private MemberSymbol updateFallState = RobustBoatsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("a", "(DZ)V")
          : new MemberSymbol("updateFallState", "(DZ)V");
  private MemberSymbol fallDistance = RobustBoatsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("xi", "R", "F")
          : new MemberSymbol("net/minecraft/entity/item/EntityBoat", "fallDistance", "F");
  private MemberSymbol onUpdate = RobustBoatsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("h", "()V")
          : new MemberSymbol("onUpdate", "()V");
  private MemberSymbol isCollidedHorizontally = RobustBoatsCorePlugin.runtimeDeobfuscationEnabled
          ? new MemberSymbol("xi", "E", "Z")
          : new MemberSymbol("net/minecraft/entity/item/EntityBoat", "isCollidedHorizontally", "Z");

  @Override
  public ClassNode transform(ClassNode classNode) {
    return ClassQuery.of(classNode)
            .selectMethod(updateFallState.name, updateFallState.desc)
            .selectCodeFragment(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.aload(Parameters.const_(0))),
                            CodeRegexes.stencil(InsnStencils.getfield(Parameters.const_(fallDistance.owner), Parameters.const_(fallDistance.name), Parameters.const_(fallDistance.desc))),
                            CodeRegexes.stencil(InsnStencils.ldc(Parameters.const_(3.0F))),
                            CodeRegexes.stencil(InsnStencils.fcmpl()),
                            CodeRegexes.stencil(InsnStencils.ifle(Parameters.bind(l0)))
                    )
            )
            .replaceWith(
                    InsnStencils.iconst_0(),
                    InsnStencils.ifeq(Parameters.bind(l0))
            )
            .done()
            .done()
            .selectMethod(onUpdate.name, onUpdate.desc)
            .selectCodeFragment(
                    Regexes.concatenate(
                            CodeRegexes.stencil(InsnStencils.aload(Parameters.const_(0))),
                            CodeRegexes.stencil(InsnStencils.getfield(Parameters.const_(isCollidedHorizontally.owner), Parameters.const_(isCollidedHorizontally.name), Parameters.const_(isCollidedHorizontally.desc))),
                            CodeRegexes.stencil(InsnStencils.ifeq(Parameters.bind(l0)))
                    )
            )
            .replaceWith(
                    InsnStencils.iconst_0(),
                    InsnStencils.ifeq(Parameters.bind(l0))
            )
            .done()
            .done()
            .done();
  }
}
