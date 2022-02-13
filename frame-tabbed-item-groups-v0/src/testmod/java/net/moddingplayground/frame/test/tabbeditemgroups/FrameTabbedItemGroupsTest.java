package net.moddingplayground.frame.test.tabbeditemgroups;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;

import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.Predicate.*;
import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.*;
import static net.moddingplayground.frame.api.util.FrameUtil.*;

public class FrameTabbedItemGroupsTest implements ModInitializer {
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
                               .build("three", iconOf(new Identifier("three"))))
                       .tab(Tab.builder()
                               .predicate(tag(ItemTags.BEACON_PAYMENT_ITEMS))
                               .displayText(t -> createDisplayText(t.getGroup(), t).shallowCopy().formatted(Formatting.AQUA))
                               .build("four", GUIIcon.of(() -> new ItemStack(Items.DIAMOND))))
                       .tab(Tab.builder().build("five", GUIIcon.of(() -> new ItemStack(Items.GLOWSTONE))))
                       .tab(Tab.builder().build("six", GUIIcon.of(() -> new ItemStack(Items.STICK))))
                       .tab(Tab.builder().build("seven", iconOf(new Identifier("seven"))))
                       .tab(Tab.builder().build("eight", GUIIcon.of(() -> new ItemStack(Items.DIAMOND))))
                       .tab(Tab.builder().build("nine", GUIIcon.of(() -> new ItemStack(Items.BARRIER))))
                       .build(id("tabbed_icon_textures"), TabbedItemGroup::icon);

    public static final ItemGroup ITEM_GROUP_DEFAULT_BOTTOM = FabricItemGroupBuilder.build(id("default_bottom"), () -> new ItemStack(Items.STICK));
    public static final ItemGroup ITEM_GROUP_TABBED_BOTTOM = TabbedItemGroup.builder().build(id("tabbed_bottom"), g -> GUIIcon.of(() -> new ItemStack(Items.BLAZE_ROD)));

    @Override
    public void onInitialize() {}

    public static Identifier id(String id) {
        return new Identifier("frame-tabbed-item-groups-test", id);
    }
}
