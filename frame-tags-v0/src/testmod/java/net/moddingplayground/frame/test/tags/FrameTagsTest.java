package net.moddingplayground.frame.test.tags;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.tags.v0.CommonTag;

import java.util.List;

public class FrameTagsTest implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, manager, success) -> {
            if (success) {
                List.of(CommonTag.DYES, CommonTag.CHESTS, CommonTag.WOODEN_STICKS).forEach(this::log);
                for (DyeColor dye : DyeColor.values()) CommonTag.INDIVIDUAL_DYES.apply(dye).run(this::log, this::log);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> void log(TagKey<T> tag) {
        System.out.printf("-- %s%n", tag.id());
        ((Registry<T>) Registry.REGISTRIES.get(tag.registry().getValue())).getOrCreateEntryList(tag)
                                                                          .forEach(System.out::println);
    }

    public void log(CommonTag tag) {
        tag.run(this::log, this::log);
    }
}
