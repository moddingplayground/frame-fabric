package net.moddingplayground.frame.impl.bannerpatterns;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.event.registry.RegistryIdRemapCallback;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Block;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPattern;
import net.moddingplayground.frame.api.bannerpatterns.v0.FrameBannerPatterns;

import java.util.ArrayList;
import java.util.List;

public final class FrameBannerPatternsInternal {
    private static final List<FrameBannerPattern> nonSpecialPatterns = new ArrayList<>();
    private static final List<FrameBannerPattern> specialPatterns = new ArrayList<>();

    public static final List<Identifier> BANNER_LOOT_TABLES = Util.make(ImmutableList.<Identifier>builder(), list -> {
        for (Block block : Registry.BLOCK) {
            Identifier id = Registry.BLOCK.getId(block);
            if (Identifier.DEFAULT_NAMESPACE.equals(id.getNamespace())) list.add(block.getLootTableId());
        }
    }).build();

    static {
        RegistryIdRemapCallback.event(FrameBannerPatterns.REGISTRY).register(state -> FrameBannerPatternsInternal.remapLoomIndices());
        RegistryEntryAddedCallback.event(FrameBannerPatterns.REGISTRY).register((rawId, id, pattern) -> FrameBannerPatternsInternal.addPattern(pattern));
        FrameBannerPatterns.REGISTRY.forEach(FrameBannerPatternsInternal::addPattern);

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, lootTableId, supplier, setter) -> {
            if (BANNER_LOOT_TABLES.contains(lootTableId)) {
                supplier.withFunction(CopyNbtLootFunction
                    .builder(ContextLootNbtProvider.BLOCK_ENTITY)
                    .withOperation(FrameBannerPatternAccess.NBT_KEY, "BlockEntityTag.%s".formatted(FrameBannerPatternAccess.NBT_KEY))
                    .build()
                );
            }
        });
    }

    private FrameBannerPatternsInternal() {}

    /**
     * Returns the ID used to represent this Frame banner pattern in the loom
     * container and screen. Loom indices allow the loom to still
     * reference custom patterns by index without non-trivial calculations
     * in order to skip special patterns.
     */
    public static int indexOf(FrameBannerPattern pattern) {
        if (pattern.hasItem()) {
            return specialPatterns.indexOf(pattern) + nonSpecialPatterns.size();
        } else {
            return nonSpecialPatterns.indexOf(pattern);
        }
    }

    /**
     * Returns a {@link FrameBannerPattern} of the given loom index.
     */
    public static FrameBannerPattern get(int index) {
        if (index < nonSpecialPatterns.size()) {
            return nonSpecialPatterns.get(index);
        } else {
            return specialPatterns.get(index - nonSpecialPatterns.size());
        }
    }

    /**
     * Returns the number of dye (non-special) patterns. This number
     * is also the loom index of the first special Frame banner pattern.
     */
    public static int dyePatternCount() {
        return nonSpecialPatterns.size();
    }

    /**
     * Returns the total number of patterns. This number
     * is also the number of entries in the Frame banner pattern registry,
     * but it is easier to obtain using this method.
     */
    public static int size() {
        return nonSpecialPatterns.size() + specialPatterns.size();
    }

    /**
     * Called every time the registries are synced to rebuild the loom
     * index table. Frame pattern indices are based on the order in which
     * patterns appear in the registry, not on the raw IDs themselves,
     * hence why we don't use the remap context provided by Fabric API.
     * User code should not call this method, as it will have no effect
     * in normal gameplay.
     */
    public static void remapLoomIndices() {
        nonSpecialPatterns.clear();
        specialPatterns.clear();
        FrameBannerPatterns.REGISTRY.forEach(FrameBannerPatternsInternal::addPattern);
    }

    /**
     * Adds a single Frame banner pattern to the pattern index. Called in the registry entry addition callback,
     * so patterns are guaranteed to be added in raw ID order.
     */
    static void addPattern(FrameBannerPattern pattern) {
        if (!pattern.hasItem()) {
            nonSpecialPatterns.add(pattern);
        } else {
            specialPatterns.add(pattern);
        }
    }
}
