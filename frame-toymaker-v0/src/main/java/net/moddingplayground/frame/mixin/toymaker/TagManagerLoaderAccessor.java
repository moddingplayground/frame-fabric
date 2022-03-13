package net.moddingplayground.frame.mixin.toymaker;

import net.minecraft.tag.TagManagerLoader;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(TagManagerLoader.class)
public interface TagManagerLoaderAccessor {
    @Accessor static Map<RegistryKey<? extends Registry<?>>, String> getDIRECTORIES() { throw new AssertionError(); }
}
