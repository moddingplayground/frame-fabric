package net.moddingplayground.frame.api.gui.itemgroup;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.util.Window;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.moddingplayground.frame.Frame;
import net.moddingplayground.frame.api.util.GUIIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static net.moddingplayground.frame.api.util.FrameUtil.*;

public class TabbedItemGroup extends ItemGroup {
    private final Identifier id;
    private final GUIIcon<?> icon;
    private final List<Tab> tabs;
    private final String tabbedTextKey;
    private final Tab.Predicate defaultPredicate;

    private int selectedTabIndex = -1;

    protected TabbedItemGroup(Identifier id, Function<TabbedItemGroup, GUIIcon<?>> icon, List<Tab> tabs, Tab.Predicate defaultPredicate) {
        super(getNextItemGroupIndex(), "%s.%s".formatted(id.getNamespace(), id.getPath()));
        this.id = id;
        this.icon = icon.apply(this);
        this.tabs = tabs;
        this.defaultPredicate = defaultPredicate;

        TranslatableText text = (TranslatableText) this.getDisplayName();
        this.tabbedTextKey = "%s.tab".formatted(text.getKey());
    }

    public Identifier getId() {
        return this.id;
    }

    public List<Tab> getTabs() {
        return this.tabs;
    }

    public String getTabbedTextKey() {
        return this.tabbedTextKey;
    }

    public int getSelectedTabIndex() {
        return this.selectedTabIndex;
    }

    public Optional<Tab> getSelectedTab() {
        int i = this.getSelectedTabIndex();
        List<Tab> tabs = this.getTabs();
        return i == -1 ? Optional.empty() : Optional.of(tabs.get(i));
    }

    @Environment(EnvType.CLIENT)
    public void setSelectedTabIndex(int selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof CreativeInventoryScreen creativeScreen) {
            Window window = client.getWindow();
            creativeScreen.init(client, window.getScaledWidth(), window.getScaledHeight());
        }
    }

    @Override
    public ItemStack createIcon() {
        throw new AssertionError("createIcon should not be called");
    }

    public <T> Optional<T> getIconTexture(boolean hovered, Class<T> clazz) {
        return GUIIcon.optional(this.icon, hovered, this.isSelected(), clazz);
    }

    @Environment(EnvType.CLIENT)
    public boolean isSelected() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen instanceof CreativeInventoryScreen screen) return screen.getSelectedTab() == this.getIndex();
        throw new IllegalStateException("Current sreeen is not CreativeInventoryScreen");
    }

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        Optional<Tab> tab = this.getSelectedTab();
        Tab.Predicate predicate = tab.map(Tab::getPredicate).orElse(this.defaultPredicate);
        Stream<Item> stream = Registry.ITEM.stream().filter(i -> predicate.test(this, i));
        for (Item item : stream.toList()) stacks.add(new ItemStack(item));
    }

    public Identifier getIconTexture() {
        Identifier id = this.getId();
        return new Identifier(id.getNamespace(), "textures/%s/gui/item_group_icon/%s".formatted(Frame.MOD_ID, id.getPath()));
    }

    public Identifier createIconTexture(String suffix) {
        return suffixId(this.getIconTexture(), suffix);
    }

    public Identifier createBaseIconTexture() {
        return this.createIconTexture("");
    }

    public Identifier createHoverIconTexture() {
        return this.createIconTexture("hovered");
    }

    public Identifier createSelectedIconTexture() {
        return this.createIconTexture("selected");
    }

    public static TabbedItemGroup.Builder builder() {
        return new TabbedItemGroup.Builder();
    }

    protected static int getNextItemGroupIndex() {
        ((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
        return ItemGroup.GROUPS.length - 1;
    }

    public static class Builder {
        private final List<Tab> tabs = new ArrayList<>();
        private Tab.Predicate defaultPredicate = Tab.Predicate.CONTAINS;

        protected Builder() {}

        public Builder tab(Tab tab) {
            this.tabs.add(tab);
            return this;
        }

        public Builder defaultPredicate(Tab.Predicate predicate) {
            this.defaultPredicate = predicate;
            return this;
        }

        public TabbedItemGroup build(Identifier id, Function<TabbedItemGroup, GUIIcon<?>> icon) {
            TabbedItemGroup group = new TabbedItemGroup(id, icon, this.tabs, this.defaultPredicate);
            for (Tab tab : this.tabs) tab.addToGroup(group);
            return group;
        }
    }
}
