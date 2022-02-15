package net.moddingplayground.frame.api.util;

import com.google.common.base.Stopwatch;
import net.fabricmc.api.EnvType;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class InitializationLogger {
    private final Logger logger;
    private final String name;
    private final Stopwatch sw;

    public InitializationLogger(Logger logger, String name, @Nullable EnvType env) {
        this.logger = logger;
        this.sw = Stopwatch.createUnstarted();

        String suffix = Optional.ofNullable(env).map(e -> "-%s".formatted(e.name())).orElse("");
        this.name = name + suffix;
    }

    public InitializationLogger(Logger logger, String name) {
        this(logger, name, null);
    }

    public void start() {
        this.logger.info("Initializing {}", this.name);
        this.sw.start();
    }

    public void finish() {
        this.sw.stop();
        this.logger.info("Initialized {} (took {}ms)", this.name, this.sw.elapsed(TimeUnit.MILLISECONDS));
    }
}
