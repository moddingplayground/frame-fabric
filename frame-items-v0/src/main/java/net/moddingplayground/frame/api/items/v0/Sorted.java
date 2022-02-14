package net.moddingplayground.frame.api.items.v0;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

/**
 * An interface that correctly sorts items in their vanilla item groups.
 */
public interface Sorted {
    void appendSortedStacks(ItemGroup group, DefaultedList<ItemStack> stacks, int index);
}
