package net.moddingplayground.frame.mixin.toymaker;

import com.google.common.base.Stopwatch;
import net.minecraft.GameVersion;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.moddingplayground.frame.impl.toymaker.DataCacheAccess;
import net.moddingplayground.frame.impl.toymaker.DataGenAccess;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@SuppressWarnings({"UnusedMixin","unused"})
@Mixin(DataGenerator.class)
public abstract class DataGeneratorMixin implements DataGenAccess {
    @Shadow public abstract Path getOutput();
    @Shadow @Final private List<DataProvider> providers;
    @Shadow @Final private static Logger LOGGER;

    @Shadow @Final private GameVersion gameVersion;

    @Override
    public void run(Consumer<DataCacheAccess> configure) throws IOException {
        DataCache cache = new DataCache(getOutput(), this.providers, this.gameVersion);
        configure.accept((DataCacheAccess) cache);
        Stopwatch allSw = Stopwatch.createStarted();
        Stopwatch providerSw = Stopwatch.createUnstarted();

        LOGGER.info("Starting providers...");

        for (DataProvider provider : providers) {
            LOGGER.info("  Starting provider: {}", provider.getName());
            providerSw.start();
            provider.run(cache.getOrCreateWriter(provider));
            providerSw.stop();
            LOGGER.info("    {} finished after {} ms", provider.getName(), providerSw.elapsed(TimeUnit.MILLISECONDS));
            providerSw.reset();
        }

        LOGGER.info("All providers took: {} ms", allSw.elapsed(TimeUnit.MILLISECONDS));
        cache.write();
    }
}
