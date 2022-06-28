package net.moddingplayground.frame.mixin.toymaker;

import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.tag.TagBuilder;
import net.minecraft.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractTagProvider.class)
public interface AbstractTagProviderInvoker<T> {
    @Invoker TagBuilder invokeGetTagBuilder(TagKey<T> tag);
}
