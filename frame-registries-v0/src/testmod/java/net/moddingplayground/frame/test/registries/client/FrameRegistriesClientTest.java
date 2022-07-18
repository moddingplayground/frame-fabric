package net.moddingplayground.frame.test.registries.client;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.moddingplayground.frame.test.registries.FrameRegistriesTest;
import net.moddingplayground.frame.test.registries.Tater;

import java.util.Map;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class FrameRegistriesClientTest implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal(FrameRegistriesTest.MOD_ID + ":get_tater_registry/client").executes(this::executeClientTestCommand));
        });
    }

    private int executeClientTestCommand(CommandContext<FabricClientCommandSource> context) {
        FabricClientCommandSource source = context.getSource();
        Registry<Tater> registry = source.getRegistryManager().get(FrameRegistriesTest.TATER_KEY);

        Set<Map.Entry<RegistryKey<Tater>, Tater>> entries = registry.getEntrySet();
        source.sendFeedback(Text.literal("Found " + entries.size() + " taters:").formatted(Formatting.GRAY));

        for (Map.Entry<RegistryKey<Tater>, Tater> entry : entries) {
            Identifier id = entry.getKey().getValue();
            Tater tater = entry.getValue();

            source.sendFeedback(Text.literal("- ID:   " + id));
            source.sendFeedback(Text.literal("  Name: " + tater.getName()));
        }

        return 1;
    }
}
