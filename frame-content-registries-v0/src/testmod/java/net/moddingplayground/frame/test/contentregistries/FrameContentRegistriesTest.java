package net.moddingplayground.frame.test.contentregistries;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.moddingplayground.frame.api.contentregistries.v0.StateRegistry;

public class FrameContentRegistriesTest implements ModInitializer {
    @Override
    public void onInitialize() {
        StateRegistry.BOOKSHELVES.add(Blocks.ACACIA_LEAVES);
        StateRegistry.LADDERS.add(Blocks.ACACIA_BUTTON);
        StateRegistry.LADDERS_DEATH_MESSAGES.add(Blocks.BIRCH_BUTTON);
    }
}
