package net.moddingplayground.frame.api.registry.set;

import net.minecraft.block.Block;

public class BlockSet extends NamespacedRegistrySet {
    public BlockSet(String namespace) {
        super(namespace);
    }

    @SuppressWarnings("unchecked")
    public Block getBlock(RegistrableObject<?> object) {
        return this.get((RegistrableObject<Block>) object, Block.class);
    }
}
