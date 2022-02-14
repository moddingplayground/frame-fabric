package net.moddingplayground.frame.api.items.v0;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.collection.DefaultedList;

public class SortedSpawnEggItem extends SpawnEggItem implements Sorted {
    public SortedSpawnEggItem(EntityType<? extends MobEntity> type, int primaryColor, int secondaryColor, Settings settings) {
        super(type, primaryColor, secondaryColor, settings);
    }

    @Override
    public void appendSortedStacks(ItemGroup group, DefaultedList<ItemStack> stacks, int index) {
        stacks.add(index + 1, new ItemStack(this));
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {}
}
