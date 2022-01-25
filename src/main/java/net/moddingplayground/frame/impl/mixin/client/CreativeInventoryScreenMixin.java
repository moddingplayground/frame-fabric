package net.moddingplayground.frame.impl.mixin.client;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.moddingplayground.frame.api.gui.itemgroup.Tab;
import net.moddingplayground.frame.impl.client.gui.itemgroup.TabWidget;
import net.moddingplayground.frame.api.gui.itemgroup.TabbedItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

import static com.mojang.blaze3d.systems.RenderSystem.*;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {
    @Shadow private static int selectedTab;

    private int frame_mouseX, frame_mouseY;
    private final List<TabWidget> frame_tabWidgets = Lists.newArrayList();

    public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler handler, PlayerInventory inventory, Text text) {
        super(handler, inventory, text);
    }

    // capture mouse position
    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderHead(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.frame_mouseX = mouseX;
        this.frame_mouseY = mouseY;
    }

    // render tooltip for sidebar tab widgets
    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At("TAIL"))
    public void onRenderTail(MatrixStack matrices, int mouseX, int mouseY, float tickDelta, CallbackInfo cbi) {
        this.frame_tabWidgets.forEach(b -> {
            if (b.isHovered()) renderTooltip(matrices, b.getMessage(), mouseX, mouseY);
        });
    }

    // replace with tabbed title
    @Redirect(method = "drawForeground", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemGroup;getDisplayName()Lnet/minecraft/text/Text;"))
    private Text redirectDrawForegroundGetDisplayName(ItemGroup group) {
        if (group instanceof TabbedItemGroup tabbedGroup) {
            return tabbedGroup.getSelectedTab()
                              .<Text>map(tab -> new TranslatableText(tabbedGroup.getTabbedTextKey(), tab.getDisplayText()))
                              .orElseGet(group::getDisplayName);
        }
        return group.getDisplayName();
    }

    // render custom tab icon textures
    @Inject(method = "renderTabIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void onRenderTabIcon(MatrixStack matrices, ItemGroup group, CallbackInfo ci, boolean selected, boolean isTopRow, int column, int u, int v, int x, int y) {
        if (group instanceof TabbedItemGroup tabbedGroup) {
            tabbedGroup.getIconTexture(this.frame_isHovered(group, column, isTopRow), Identifier.class).ifPresent(texture -> {
                setShader(GameRenderer::getPositionTexShader);
                setShaderTexture(0, texture);
                drawTexture(matrices, x + 6, y + (group.isTopRow() ? 9 : 7), 0, 0, 16, 16, 16, 16);
                ci.cancel();
            });
        }
    }

    // render custom implementation of icons
    @Inject(method = "renderTabIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemGroup;getIcon()Lnet/minecraft/item/ItemStack;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void onRenderTabIconGetIcon(MatrixStack matrices, ItemGroup group, CallbackInfo ci, boolean selected, boolean isTopRow, int column, int u, int v, int x, int y) {
        if (group instanceof TabbedItemGroup tabbedGroup) {
            tabbedGroup.getIconTexture(this.frame_isHovered(group, column, isTopRow), ItemStack.class).ifPresent(stack -> {
                int fx = x + (isTopRow ? 1 : 0);
                this.itemRenderer.renderInGuiWithOverrides(stack, fx, y);
                this.itemRenderer.renderGuiItemOverlay(this.textRenderer, stack, fx, y);
                this.itemRenderer.zOffset = 0.0f;
                ci.cancel();
            });
        }
    }

    private boolean frame_isHovered(ItemGroup group, int column, boolean isTopRow) {
        int mx = group.isSpecial()
            ? this.backgroundWidth - 28 * (6 - column) + 2
            : 28 * column + Math.max(column, 0);
        int my = isTopRow ? -32 : this.backgroundHeight;
        return this.isPointWithinBounds(mx + 3, my + 3, 23, 27, this.frame_mouseX, this.frame_mouseY);
    }

    // add sidebar tab widgets
    @Inject(method = "setSelectedTab(Lnet/minecraft/item/ItemGroup;)V", at = @At("HEAD"))
    private void onSetSelectedTab(ItemGroup group, CallbackInfo ci) {
        try {
            ItemGroup oldGroup = ItemGroup.GROUPS[selectedTab];
            if (oldGroup instanceof TabbedItemGroup tgroup) tgroup.setSelectedTabIndex(-1);
        } catch (ArrayIndexOutOfBoundsException ignored) {}

        List<Drawable> drawables = ((ScreenAccessor) this).getDrawables();
        drawables.removeAll(this.frame_tabWidgets);
        this.frame_tabWidgets.clear();

        if (group instanceof TabbedItemGroup tgroup) {
            List<Tab> tabs = tgroup.getTabs();
            for (int i = 0, l = tabs.size(); i < l; i++) {
                Tab tab = tabs.get(i);
                this.frame_addTabWidget(tgroup, i, tab.getBackgroundTexture(), tab.getDisplayText());
            }
        }
    }

    private void frame_addTabWidget(TabbedItemGroup group, int index, GUIIcon<Identifier> backgroundTexture, Text message) {
        int x = this.x - 29;
        int y = (this.y + 12) + (index * 26);
        TabWidget widget = new TabWidget(x, y, group, index, message, backgroundTexture);
        if (index == group.getSelectedTabIndex()) widget.setSelected(true);
        this.frame_tabWidgets.add(widget);
        this.addDrawableChild(widget);
    }
}
