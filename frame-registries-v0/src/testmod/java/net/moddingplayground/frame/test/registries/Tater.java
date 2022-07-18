package net.moddingplayground.frame.test.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.dynamic.RegistryElementCodec;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

public class Tater {
    public static final Codec<Tater> CODEC = RecordCodecBuilder.create(instance -> {
        return instance
            .group(Codec.STRING.fieldOf("name").forGetter(Tater::getName))
            .apply(instance, Tater::new);
    });
    public static final Codec<Tater> NETWORK_CODEC = CODEC;

    public static final Codec<RegistryEntry<Tater>> REGISTRY_CODEC = RegistryElementCodec.of(FrameRegistriesTest.TATER_KEY, CODEC);

    private final String name;
    private final RegistryEntry.Reference<Tater> registryEntry;

    public Tater(String name) {
        this.name = name;
        this.registryEntry = FrameRegistriesTest.TATER_REGISTRY.createEntry(this);
    }

    public String getName() {
        return this.name;
    }

    public RegistryEntry.Reference<Tater> getRegistryEntry() {
        return this.registryEntry;
    }

    public static RegistryEntry<Tater> initAndGetDefault(Registry<Tater> registry) {
        return BuiltinRegistries.add(registry, FrameRegistriesTest.DEFAULT_TATER_ID, FrameRegistriesTest.DEFAULT_TATER);
    }

    @Override
    public String toString() {
        return "Tater{name=" + this.name + "}";
    }
}
