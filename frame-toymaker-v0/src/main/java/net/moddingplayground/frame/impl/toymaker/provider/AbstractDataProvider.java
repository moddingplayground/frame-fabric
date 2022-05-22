package net.moddingplayground.frame.impl.toymaker.provider;

import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.impl.toymaker.DataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class AbstractDataProvider<T> implements DataProvider {
    protected static final Logger LOGGER = LoggerFactory.getLogger("toymaker");

    protected final DataGenerator root;

    public AbstractDataProvider(DataGenerator root) {
        this.root = root;
    }

    @Override public abstract String getName();
    public abstract String getFolder();
    public abstract DataType getDataType();

    public abstract Iterable<T> getGenerators();
    public abstract Map<Identifier, JsonElement> createFileMap();

    @Override
    public void run(DataWriter cache) {
        this.write(cache, this.createFileMap());
    }

    public void write(DataWriter cache, Map<Identifier, JsonElement> map, BiFunction<Path, Identifier, Path> pathCreator) {
        Path path = this.root.getOutput();
        map.forEach((id, json) -> {
            Path output = pathCreator.apply(path, id);
            try {
                DataProvider.writeToPath(cache, json, output);
            } catch (IOException e) {
                LOGGER.error("Couldn't save {} {}", this.getFolder(), output, e);
            }
        });
    }

    public void write(DataWriter cache, Map<Identifier, JsonElement> map) {
        this.write(cache, map, this::getOutput);
    }

    public Path getOutput(Path root, Identifier id, DataType type, String folder) {
        return root.resolve(String.format("%s/%s/%s/%s.json", type.getId(), id.getNamespace(), folder, id.getPath()));
    }

    public Path getOutput(Path root, Identifier id, String folder) {
        return this.getOutput(root, id, this.getDataType(), folder);
    }

    public Path getOutput(Path root, Identifier id, DataType type) {
        return this.getOutput(root, id, type, this.getFolder());
    }

    public Path getOutput(Path root, Identifier id) {
        return this.getOutput(root, id, this.getDataType(), this.getFolder());
    }
}
