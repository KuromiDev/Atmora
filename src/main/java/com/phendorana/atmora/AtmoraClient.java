package com.phendorana.atmora;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class AtmoraClient implements ClientModInitializer {

    private static KeyMapping openContextKey;

    @Override
    public void onInitializeClient() {

        openContextKey = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key.atmora.open_context",
                        GLFW.GLFW_KEY_RIGHT_SHIFT,
                        KeyMapping.Category.MISC
                )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openContextKey.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(new ContextScreen());
                }
            }
        });
    }
}