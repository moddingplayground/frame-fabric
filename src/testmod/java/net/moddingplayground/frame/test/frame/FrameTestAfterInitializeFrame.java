package net.moddingplayground.frame.test.frame;

import net.moddingplayground.frame.api.block.wood.WoodBlockSet;
import net.moddingplayground.frame.api.entrypoint.AfterInitializeFrameEntrypoint;
import net.moddingplayground.frame.impl.Frame;
import net.moddingplayground.frame.test.FrameTest;

import static net.moddingplayground.frame.api.block.wood.WoodBlockSet.*;
import static net.moddingplayground.frame.test.FrameTest.*;

public class FrameTestAfterInitializeFrame implements AfterInitializeFrameEntrypoint {
    public static final WoodBlockSet TEST_WOOD_NOREGISTER = new WoodBlockSet(FrameTest.MOD_ID, "test_noregister", Settings.DEFAULT);
    public static final WoodBlockSet TEST_WOOD = new WoodBlockSet(MOD_ID, "test",
        Settings.builder(Settings.DEFAULT)
                .itemGroup(ITEM_GROUP_TABBED_ICON_TEXTURES).build()
    ).register();

    @Override
    public void afterInitializeFrame() {
        LOGGER.info("Initializing {}-{}", MOD_NAME, Frame.MOD_ID);

        //

        LOGGER.info("Initialized {}-{}", MOD_NAME, Frame.MOD_ID);
    }
}
