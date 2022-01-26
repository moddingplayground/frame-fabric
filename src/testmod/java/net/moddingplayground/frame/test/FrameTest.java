package net.moddingplayground.frame.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.Frame;
import net.moddingplayground.frame.api.util.FrameUtil;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.moddingplayground.frame.api.gui.itemgroup.Tab;
import net.moddingplayground.frame.api.gui.itemgroup.TabbedItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.moddingplayground.frame.Frame.*;
import static net.moddingplayground.frame.api.gui.itemgroup.Tab.*;
import static net.moddingplayground.frame.api.gui.itemgroup.Tab.Predicate.*;

public class FrameTest implements ModInitializer {
    public static final String MOD_ID   = Frame.MOD_ID + "-test";
    public static final Logger LOGGER   = LoggerFactory.getLogger(MOD_ID);

    public static final ItemGroup ITEM_GROUP_DEFAULT = FabricItemGroupBuilder.build(id("default"), () -> new ItemStack(Items.STICK));

    public static final ItemGroup ITEM_GROUP_TABBED = TabbedItemGroup.builder().build(id("tabbed"), g -> GUIIcon.of(() -> new ItemStack(Items.BLAZE_ROD)));
    public static final ItemGroup ITEM_GROUP_TABBED_ICON = TabbedItemGroup.builder().build(id("tabbed_icon"), g -> GUIIcon.of(g::baseIconTex));
    public static final ItemGroup ITEM_GROUP_TABBED_ICON_SEL = TabbedItemGroup.builder().build(id("tabbed_icon_sel"), g -> GUIIcon.of(g::baseIconTex, g::baseIconTex, g::selectedIconTex));
    public static final ItemGroup ITEM_GROUP_TABBED_ICON_HOV = TabbedItemGroup.builder().build(id("tabbed_icon_hov"), g -> GUIIcon.of(g::baseIconTex, g::hoverIconTex));
    public static final ItemGroup ITEM_GROUP_TABBED_ICON_HOV_NODEL = TabbedItemGroup.builder().build(id("tabbed_icon_hov_nodel"), g -> GUIIcon.of(g::baseIconTex, g::hoverIconTex, false));

    public static final ItemGroup ITEM_GROUP_TABBED_ICON_TEXTURES =
        TabbedItemGroup.builder()
                       .defaultPredicate(ALWAYS)
                       .tab(Tab.builder().predicate(items(Items.STONE, Items.AXOLOTL_BUCKET)).build("one", GUIIcon.of(() -> new ItemStack(Items.GLOWSTONE))))
                       .tab(Tab.builder().predicate(items(Items.SPONGE)).build("two", GUIIcon.of(() -> new ItemStack(Items.STICK))))
                       .tab(Tab.builder()
                               .predicate(tag(ItemTags.AXOLOTL_TEMPT_ITEMS))
                               .build("three", FrameUtil.iconOf(new Identifier("three"))))
                       .tab(Tab.builder()
                               .predicate(tag(ItemTags.BEACON_PAYMENT_ITEMS))
                               .displayText(t -> createDisplayText(t.getGroup(), t).shallowCopy().formatted(Formatting.AQUA))
                               .build("four", GUIIcon.of(() -> new ItemStack(Items.DIAMOND))))
                       .tab(Tab.builder().build("five", GUIIcon.of(() -> new ItemStack(Items.GLOWSTONE))))
                       .tab(Tab.builder().build("six", GUIIcon.of(() -> new ItemStack(Items.STICK))))
                       .tab(Tab.builder().build("seven", FrameUtil.iconOf(new Identifier("seven"))))
                       .tab(Tab.builder().build("eight", GUIIcon.of(() -> new ItemStack(Items.DIAMOND))))
                       .tab(Tab.builder().build("nine", GUIIcon.of(() -> new ItemStack(Items.BARRIER))))
                       .build(id("tabbed_icon_textures"), FrameUtil::iconOf);

    public static final ItemGroup ITEM_GROUP_DEFAULT_BOTTOM = FabricItemGroupBuilder.build(id("default_bottom"), () -> new ItemStack(Items.STICK));
    public static final ItemGroup ITEM_GROUP_TABBED_BOTTOM = TabbedItemGroup.builder().build(id("tabbed_bottom"), g -> GUIIcon.of(() -> new ItemStack(Items.BLAZE_ROD)));

    public static final Item TEST = Registry.register(Registry.ITEM, id("test"), new Item(new FabricItemSettings().group(ITEM_GROUP_TABBED_ICON_TEXTURES)));

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {}-test", MOD_NAME);

        //

        LOGGER.info("Initialized {}-test", MOD_NAME);
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }
}