package net.moddingplayground.frame.api.entrypoint;

import net.moddingplayground.frame.api.block.wood.WoodBlockSet;

/**
 * A Frame entrypoint, used for things like registering {@link WoodBlockSet wood sets}.
 */
@FunctionalInterface
public interface AfterInitializeFrameEntrypoint {
    void afterInitializeFrame();
}
