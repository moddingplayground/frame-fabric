package net.moddingplayground.frame.test.registries;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;

import java.util.Map;
import java.util.Set;

public class FrameRegistriesTest implements ModInitializer {
    public static final String MOD_ID = "frame-registries-v0-test";

    public static final Identifier TATER_ID = new Identifier(MOD_ID, "tater");
    public static final RegistryKey<? extends Registry<Tater>> TATER_KEY = RegistryKey.ofRegistry(TATER_ID);
    public static final SimpleRegistry<Tater> TATER_REGISTRY = new SimpleRegistry<>(TATER_KEY, Lifecycle.stable(), Tater::getRegistryEntry);

    public static final Identifier DEFAULT_TATER_ID = new Identifier(MOD_ID, "tiny_potato");
    public static final Tater DEFAULT_TATER = new Tater("Tiny Potato");

    @Override
    public void onInitialize() {
        Registry.register(TATER_REGISTRY, DEFAULT_TATER_ID, DEFAULT_TATER);
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal(MOD_ID + ":get_tater_registry").executes(this::executeTestCommand));
        });
    }

    private int executeTestCommand(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        Registry<Tater> registry = source.getRegistryManager().get(TATER_KEY);

        Set<Map.Entry<RegistryKey<Tater>, Tater>> entries = registry.getEntrySet();
        source.sendFeedback(Text.literal("Found " + entries.size() + " taters:").formatted(Formatting.GRAY), false);

        for (Map.Entry<RegistryKey<Tater>, Tater> entry : entries) {
            Identifier id = entry.getKey().getValue();
            Tater tater = entry.getValue();

            source.sendFeedback(Text.literal("- ID:   " + id), false);
            source.sendFeedback(Text.literal("  Name: " + tater.getName()), false);
        }

        return 1;
    }
}
