package net.moddingplayground.frame.api.config.v0;

import com.google.common.collect.HashBiMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.moddingplayground.frame.api.config.v0.option.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class Config {
    private final File file;
    private final File backupFile;
    protected final HashBiMap<Identifier, Option<?>> map = HashBiMap.create();

    private JsonObject oldJson;

    public Config(File file) {
        this.file = file;
        this.backupFile = new File(this.file.getAbsolutePath() + "_old");
    }

    protected <T, O extends Option<T>> O add(Identifier id, O option) {
        this.map.put(id, option);
        return option;
    }

    @Environment(EnvType.CLIENT)
    public void addConfigListEntries(ConfigEntryBuilder entryBuilder, Supplier<ConfigCategory> categoryCreator) {
        if (this.canDisplayInMenu()) {
            ConfigCategory category = categoryCreator.get();
            for (Map.Entry<Identifier, Option<?>> entry : this.getDisplayedOptions().entrySet()) {
                Identifier id = entry.getKey();
                Option<?> option = entry.getValue();
                option.addConfigEntries(category, id, entryBuilder);
            }
        }
    }

    public final HashBiMap<Identifier, Option<?>> getMap() {
        return HashBiMap.create(this.map);
    }

    public HashBiMap<Identifier, Option<?>> getDisplayedOptions() {
        return HashBiMap.create(this.map);
    }

    @Environment(EnvType.CLIENT)
    public boolean canDisplayInMenu() {
        return !this.getDisplayedOptions().isEmpty();
    }

    public void save() {
        File folder = this.file.getParentFile();
        if (folder.exists() || folder.mkdirs()) {
            try (PrintWriter out = new PrintWriter(this.file)) {
                JsonObject jsonObject = Optional.ofNullable(this.oldJson).map(JsonObject::deepCopy)
                                                .orElseGet(JsonObject::new);
                this.map.forEach((id, option) -> jsonObject.add(id.toString(), option.toJson()));

                StringWriter writer = new StringWriter();
                Streams.write(jsonObject, createJsonWriter(writer));
                out.println(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else throw new RuntimeException("Fatal error! Could not find config %s".formatted(this.file));
    }

    @SuppressWarnings("unchecked")
    public <T extends Config> T load() {
        try {
            Files.copy(this.file.toPath(), this.backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (NoSuchFileException | FileNotFoundException e) {
            this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path path = this.file.toPath();
            String json = new String(Files.readAllBytes(path));
            if (!json.isEmpty()) {
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(json);

                this.oldJson = jsonObject;
                Set<Identifier> loadedConfigs = new HashSet<>();

                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    String id = entry.getKey();
                    JsonElement jsonElement = entry.getValue();

                    Identifier identifier = Identifier.tryParse(id);
                    Optional.ofNullable(this.map.get(identifier)).ifPresent(option -> {
                        option.fromJson(jsonElement);
                        loadedConfigs.add(identifier);
                    });
                }

                if (!loadedConfigs.equals(this.map.keySet())) this.save();
            } else this.save();
        } catch (NoSuchFileException | FileNotFoundException e) {
            this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (T) this;
    }

    public static JsonWriter createJsonWriter(StringWriter stringWriter) {
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        jsonWriter.setLenient(true);
        jsonWriter.setIndent("  ");
        return jsonWriter;
    }

    public static File createFile(String name) {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        return new File(new File(configDir.toString()), "%s.json".formatted(name));
    }
}
