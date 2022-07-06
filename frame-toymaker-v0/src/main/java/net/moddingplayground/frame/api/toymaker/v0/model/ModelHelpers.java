package net.moddingplayground.frame.api.toymaker.v0.model;

import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.data.client.VariantSettings.*;

public interface ModelHelpers {
    static BlockStateVariant createVariant(Identifier model) {
        return BlockStateVariant.create().put(MODEL, model);
    }

    static VariantsBlockStateSupplier createVariants(Block block, List<BlockStateVariant> variants) {
        return VariantsBlockStateSupplier.create(block, variants.toArray(BlockStateVariant[]::new));
    }

    static void rotateAll(List<BlockStateVariant> list, Identifier model) {
        Collections.addAll(list,
            createVariant(model),
            createVariant(model).put(Y, Rotation.R90),
            createVariant(model).put(Y, Rotation.R180),
            createVariant(model).put(Y, Rotation.R270)
        );
    }

    static List<BlockStateVariant> rotateAll(Identifier model) {
        List<BlockStateVariant> list = new ArrayList<>();
        rotateAll(list, model);
        return list;
    }

    static Predicate<Block> namespacePredicate(String namespace) {
        return blockx -> {
            Identifier id = Registry.BLOCK.getId(blockx);
            return id.getNamespace().equals(namespace);
        };
    }
}
