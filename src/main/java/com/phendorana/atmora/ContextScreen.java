package com.phendorana.atmora;

import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ContextScreen extends Screen {

    private static final int PANEL_WIDTH = 440;
    private static final int PANEL_HEIGHT = 400;

    private String selectedSection = "Fishing";

    public ContextScreen() {
        super(Component.literal("Atmora v0.0.1"));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;
        int right = left + PANEL_WIDTH;
        int bottom = top + PANEL_HEIGHT;

        // Outer border
        graphics.fill(
                left - 2,
                top - 2,
                right + 2,
                bottom + 2,
                0xFF000000
        );

        // Main panel
        graphics.fill(
                left,
                top,
                right,
                bottom,
                0xFF202020
        );

        // Title bar
        graphics.fill(
                left,
                top,
                right,
                top + 35,
                0xFF303030
        );

        // Sidebar
        int sidebarWidth = 100;

        graphics.fill(
                left,
                top + 36,
                left + sidebarWidth,
                bottom,
                0xFF181818
        );

        // Fishing section
        int fishingTop = top + 45;
        int fishingHeight = 24;

        graphics.fill(
                left + 5,
                fishingTop,
                left + sidebarWidth - 5,
                fishingTop + fishingHeight,
                selectedSection.equals("Fishing") ? 0xFF302030 : 0xFF202020
        );

        graphics.text(
                this.font,
                "Fishing",
                left + 12,
                fishingTop + 7,
                0xFFFFFFFF,
                false
        );

        // Dungeons section
        int dungeonsTop = top + 75;
        int dungeonsHeight = 24;

        graphics.fill(
                left + 5,
                dungeonsTop,
                left + sidebarWidth - 5,
                dungeonsTop + dungeonsHeight,
                selectedSection.equals("Dungeons") ? 0xFF302030 : 0xFF202020
        );

        graphics.text(
                this.font,
                "Dungeons",
                left + 12,
                dungeonsTop + 7,
                0xFFFFFFFF,
                false
        );

        // Sidebar divider
        graphics.fill(
                left + sidebarWidth,
                top + 36,
                left + sidebarWidth + 1,
                bottom,
                0xFF555555
        );

        // Divider underneath title
        graphics.fill(
                left,
                top + 35,
                right,
                top + 36,
                0xFF555555
        );

        // Title
        Component gradientTitle = createGradientText(
                this.title.getString(),
                0xFF66CC,
                0xAA00FF
        );

        graphics.pose().pushMatrix();

        graphics.pose().translate(left + 10, top + 9);
        graphics.pose().scale(1.25f, 1.25f);

        graphics.text(
                this.font,
                gradientTitle,
                0,
                3,
                0xFFFFFFFF
        );

        graphics.pose().popMatrix();

        if (selectedSection.equals("Fishing")) {

            graphics.text(
                    this.font,
                    "Fishing",
                    left + sidebarWidth + 15,
                    top + 53,
                    0xFFFFFFFF,
                    false
            );

        } else if (selectedSection.equals("Dungeons")) {

            graphics.text(
                    this.font,
                    "Dungeons",
                    left + sidebarWidth + 15,
                    top + 53,
                    0xFFFFFFFF,
                    false
            );
        }
    }

    private Component createGradientText(String text, int startColor, int endColor) {
        MutableComponent result = Component.empty();

        int length = text.length();

        for (int i = 0; i < length; i++) {
            float progress = length <= 1 ? 0 : (float) i / (length - 1);

            int startR = (startColor >> 16) & 0xFF;
            int startG = (startColor >> 8) & 0xFF;
            int startB = startColor & 0xFF;

            int endR = (endColor >> 16) & 0xFF;
            int endG = (endColor >> 8) & 0xFF;
            int endB = endColor & 0xFF;

            int r = (int) (startR + (endR - startR) * progress);
            int g = (int) (startG + (endG - startG) * progress);
            int b = (int) (startB + (endB - startB) * progress);

            int color = (r << 16) | (g << 8) | b;

            result.append(
                    Component.literal(String.valueOf(text.charAt(i)))
                            .setStyle(Style.EMPTY.withColor(color))
            );
        }

        return result;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {

        if (event.button() == 0) {

            int left = (this.width - PANEL_WIDTH) / 2;
            int top = (this.height - PANEL_HEIGHT) / 2;

            int sidebarWidth = 100;

            int fishingLeft = left + 5;
            int fishingTop = top + 45;
            int fishingRight = left + sidebarWidth - 5;
            int fishingBottom = fishingTop + 24;

            double mouseX = event.x();
            double mouseY = event.y();

            if (mouseX >= fishingLeft && mouseX <= fishingRight
                    && mouseY >= fishingTop && mouseY <= fishingBottom) {

                selectedSection = "Fishing";
                return true;
            }

            int dungeonsLeft = left + 5;
            int dungeonsTop = top + 75;
            int dungeonsRight = left + sidebarWidth - 5;
            int dungeonsBottom = dungeonsTop + 24;

            if (mouseX >= dungeonsLeft && mouseX <= dungeonsRight
                    && mouseY >= dungeonsTop && mouseY <= dungeonsBottom) {

                selectedSection = "Dungeons";
                return true;
            }
        }

        return super.mouseClicked(event, doubleClick);
    }
}