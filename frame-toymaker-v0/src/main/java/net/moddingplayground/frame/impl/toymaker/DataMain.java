package net.moddingplayground.frame.impl.toymaker;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.data.DataGenerator;
import net.moddingplayground.frame.api.toymaker.v0.ToymakerEntrypoint;
import net.moddingplayground.frame.impl.toymaker.provider.AdvancementProvider;
import net.moddingplayground.frame.impl.toymaker.provider.ItemModelProvider;
import net.moddingplayground.frame.impl.toymaker.provider.LootTableProvider;
import net.moddingplayground.frame.impl.toymaker.provider.RecipeProvider;
import net.moddingplayground.frame.impl.toymaker.provider.StateModelProvider;
import net.moddingplayground.frame.impl.toymaker.provider.TagProvider;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class DataMain {
    private static final Logger LOGGER = LoggerFactory.getLogger("toymaker");
    @Nullable private static final String TARGET_MOD_ID = System.getProperty("toymaker.datagen.modid");

    public static void main(String[] strings) throws IOException {
        OptionParser opts = new OptionParser();

        OptionSpec<Void> help     = opts.accepts("help", "Show the help menu").forHelp();
        OptionSpec<Void> server   = opts.accepts("server", "Include server generators");
        OptionSpec<Void> client   = opts.accepts("client", "Include client generators");
        OptionSpec<Void> dev      = opts.accepts("dev", "Include development tools");
        OptionSpec<Void> reports  = opts.accepts("reports", "Include data reports");
        OptionSpec<Void> validate = opts.accepts("validate", "Validate inputs");
        OptionSpec<Void> all      = opts.accepts("all", "Include all generators");

        OptionSpec<String> output = opts.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated");
        OptionSpec<String> extra  = opts.accepts("extra", "Additional output folder to copy assets to").withRequiredArg();
        OptionSpec<String> input  = opts.accepts("input", "Input folder").withRequiredArg();

        OptionSet optionSet = opts.parse(strings);
        if (!optionSet.has(help) && optionSet.hasOptions()) {
            Path path = Paths.get(output.value(optionSet));

            boolean genAll      = optionSet.has(all);
            boolean genClient   = genAll || optionSet.has(client);
            boolean genServer   = genAll || optionSet.has(server);
            boolean genDev      = genAll || optionSet.has(dev);
            boolean genReports  = genAll || optionSet.has(reports);
            boolean genValidate = genAll || optionSet.has(validate);

            FabricLoader loader = FabricLoader.getInstance();
            List<EntrypointContainer<ToymakerEntrypoint>> initializers = loader.getEntrypointContainers("toymaker", ToymakerEntrypoint.class);
            if (initializers.isEmpty()) {
                LOGGER.error("No data generators were initialized!");
            } else {
                for (EntrypointContainer<ToymakerEntrypoint> initializer : initializers) {
                    ModMetadata meta = initializer.getProvider().getMetadata();
                    if (TARGET_MOD_ID != null && !meta.getId().equals(TARGET_MOD_ID)) continue;
                    LOGGER.info("Initializing data generator for " + meta.getId());
                    initializer.getEntrypoint().onInitializeToymaker();
                }
            }

            DataGenerator gen = create(
                path,
                optionSet.valuesOf(input)
                         .stream()
                         .map(Paths::get)
                         .collect(Collectors.toList()),
                genClient,
                genServer,
                genDev,
                genReports,
                genValidate
            );

            DataGenAccess access = (DataGenAccess) gen;
            access.run(config -> optionSet.valuesOf(extra)
                                          .stream()
                                          .map(Paths::get)
                                          .forEach(config::addCopyPath));
        } else {
            opts.printHelpOn(System.out);
        }
    }

    public static DataGenerator create(Path output, Collection<Path> inputs, boolean includeClient, boolean includeServer, boolean includeDev, boolean includeReports, boolean validate) {
        DataGenerator gen = new DataGenerator(output, inputs);

        if (includeClient) {
            gen.addProvider(new StateModelProvider(gen));
            gen.addProvider(new ItemModelProvider(gen));
        }
        if (includeServer) {
            gen.addProvider(new TagProvider(gen));
            gen.addProvider(new LootTableProvider(gen));
            gen.addProvider(new RecipeProvider(gen));
            gen.addProvider(new AdvancementProvider(gen));
        }

        return gen;
    }
}
