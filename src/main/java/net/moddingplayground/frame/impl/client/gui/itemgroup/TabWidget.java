package net.moddingplayground.frame.impl.client.gui.itemgroup;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.moddingplayground.frame.api.gui.itemgroup.Tab;
import net.moddingplayground.frame.api.gui.itemgroup.TabbedItemGroup;

import java.util.List;

import static com.mojang.blaze3d.systems.RenderSystem.*;

@Environment(EnvType.CLIENT)
public class TabWidget extends ButtonWidget {
    private final TabbedItemGroup group;
    private final int index;
    private final GUIIcon<Identifier> backgroundTexture;

    private boolean selected;

    public TabWidget(int x, int y, TabbedItemGroup group, int index, Text message, GUIIcon<Identifier> backgroundTexture) {
        super(x, y, 22, 22, message, button -> {});
        this.group = group;
        this.index = index;
        this.backgroundTexture = backgroundTexture;
    }

    public TabbedItemGroup getGroup() {
        return this.group;
    }

    public int getIndex() {
        return this.index;
    }

    public Tab getTab() {
        TabbedItemGroup group = this.getGroup();
        List<Tab> tabs = group.getTabs();
        int i = this.getIndex();
        return tabs.get(i);
    }

    public Identifier getBackgroundTexture() {
        return this.backgroundTexture.getIcon(this.hovered, this.isSelected());
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void onPress() {
        if (this.group.getSelectedTabIndex() == this.index) {
            this.group.setSelectedTabIndex(-1);
            this.setSelected(false);
            return;
        }

        this.group.setSelectedTabIndex(this.index);
        this.setSelected(true);
    }

    @Override
    protected int getYImage(boolean hover) {
        return hover || this.isSelected() ? 1 : 0;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float tickDelta) {
        enableBlend();
        defaultBlendFunc();
        blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

        MinecraftClient client = MinecraftClient.getInstance();
        setShader(GameRenderer::getPositionTexShader);
        setShaderTexture(0, this.getBackgroundTexture());
        drawTexture(matrices, this.x, this.y, 0, 0, 32, 32, 32, 32);
        this.renderBackground(matrices, client, mouseX, mouseY);

        int x = this.x + 6;
        int y = this.y + 3;
        Tab tab = this.getTab();
        GUIIcon<?> icon = tab.getIcon();
        boolean hovered = this.hovered;
        boolean selected = this.isSelected();

        GUIIcon.optional(icon, hovered, selected, Identifier.class).ifPresentOrElse(
            texture -> {
                setShader(GameRenderer::getPositionTexShader);
                setShaderTexture(0, texture);
                drawTexture(matrices, x, y, 0, 0, 16, 16, 16, 16);
            },
            () -> {
                GUIIcon.optional(icon, hovered, selected, ItemStack.class).ifPresent(stack -> {
                    ItemRenderer itemRenderer = client.getItemRenderer();
                    itemRenderer.renderInGui(stack, x, y);
                });
            }
        );
    }
}
