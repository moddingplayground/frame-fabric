package net.moddingplayground.frame.mixin.items;

import com.google.common.base.Suppliers;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.items.v0.Sorted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Supplier;

/**
 * @implNote defined suppliers are not method references
 *           to prevent premature static initialization
 */
@Mixin(ItemGroup.class)
public class ItemGroupMixin {
    private static @Unique final Supplier<List<Sorted>> FRAME_SORTED = Suppliers.memoize(() ->
        Registry.ITEM.stream()
                     .filter(Sorted.class::isInstance)
                     .map(Sorted.class::cast).toList()
    );

    private static @Unique final Supplier<List<Sorted>> FRAME_SORTED_SPAWN_EGGS = Suppliers.memoize(() ->
        FRAME_SORTED.get().stream()
                    .filter(SpawnEggItem.class::isInstance).toList()
    );

    /**
     * Appends registered instances of {@link SpawnEggItem} that implement {@link Sorted}.
     */
    @Inject(method = "appendStacks", at = @At("TAIL"))
    private void appendSortedSpawnEggs(DefaultedList<ItemStack> stacks, CallbackInfo ci) {
        ItemGroup that = (ItemGroup) (Object) this;
        if (that != ItemGroup.MISC) return;

        for (int i = 0, l = stacks.size(); i < l; i++) {
            if (i == l - 1) break;
            ItemStack stack = stacks.get(i);
            if (stack.getItem() instanceof SpawnEggItem) {
                ItemStack next = stacks.get(i + 1);
                if (next.getItem() instanceof SpawnEggItem) continue;
                final int index = i; // allow for usage inside of lambda
                FRAME_SORTED_SPAWN_EGGS.get().forEach(s -> s.appendSortedStacks(that, stacks, index));
                break;
            }
        }
    }
}
