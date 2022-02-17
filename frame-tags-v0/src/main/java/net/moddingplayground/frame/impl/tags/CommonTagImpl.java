package net.moddingplayground.frame.impl.tags;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Util;
import net.moddingplayground.frame.api.tags.v0.CommonTag;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public final class CommonTagImpl implements CommonTag {
    private final List<Tag.Identified<Block>> blocks;
    private final List<Tag.Identified<Item>> items;

    public CommonTagImpl(List<Tag.Identified<Block>> blocks, List<Tag.Identified<Item>> items) {
        this.blocks = blocks;
        this.items = items;
    }

    public CommonTagImpl(String... ids) {
        this(tags(CommonTag::block, ids), tags(CommonTag::item, ids));
    }

    @Override
    public List<Tag.Identified<Block>> blockTags() {
        return this.blocks;
    }

    @Override
    public List<Tag.Identified<Item>> itemTags() {
        return this.items;
    }

    @Override
    public void run(Consumer<Tag.Identified<Block>> block, Consumer<Tag.Identified<Item>> item) {
        this.blockTags().forEach(block);
        this.itemTags().forEach(item);
    }

    @Override
    public String toString() {
        return "CommonTagImpl[" + "blocks=" + blocks + ", " + "items=" + items + ']';
    }

    private static <T> List<Tag.Identified<T>> tags(Function<String, Tag.Identified<T>> factory, String... ids) {
        return Util.make(ImmutableList.<Tag.Identified<T>>builder(), list -> {
            for (String id : ids) list.add(factory.apply(id));
        }).build();
    }
}
