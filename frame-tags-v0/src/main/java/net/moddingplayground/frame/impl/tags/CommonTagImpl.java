package net.moddingplayground.frame.impl.tags;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.tags.v0.CommonTag;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class CommonTagImpl implements CommonTag {
    private final List<TagKey<Block>> blocks;
    private final List<TagKey<Item>> items;

    public CommonTagImpl(List<TagKey<Block>> blocks, List<TagKey<Item>> items) {
        this.blocks = blocks;
        this.items = items;
    }

    public CommonTagImpl(String... ids) {
        this(tags(CommonTag::block, ids), tags(CommonTag::item, ids));
    }

    @Override
    public List<TagKey<Block>> blockTags() {
        return this.blocks;
    }

    @Override
    public List<TagKey<Item>> itemTags() {
        return this.items;
    }

    @Override
    public void run(Consumer<TagKey<Block>> block, Consumer<TagKey<Item>> item) {
        this.blockTags().forEach(block);
        this.itemTags().forEach(item);
    }

    @Override
    public String toString() {
        return "CommonTagImpl[" + "blocks=" + blocks + ", " + "items=" + items + ']';
    }

    private static <T> List<TagKey<T>> tags(Function<String, TagKey<T>> factory, String... ids) {
        return Util.make(ImmutableList.<TagKey<T>>builder(), list -> {
            for (String id : ids) list.add(factory.apply(id));
        }).build();
    }
}
