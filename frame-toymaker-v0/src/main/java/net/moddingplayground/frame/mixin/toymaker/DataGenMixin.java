package net.moddingplayground.frame.mixin.toymaker;

import net.minecraft.server.Main;
import net.moddingplayground.frame.impl.toymaker.DataMain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedMixin")
@Mixin(Main.class)
public class DataGenMixin {
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/SaveLoader;dynamicRegistryManager()Lnet/minecraft/util/registry/DynamicRegistryManager$Immutable;", shift = At.Shift.AFTER), cancellable = true, remap = false)
    private static void onDataMain(String[] strings, CallbackInfo ci) {
        boolean data = Boolean.parseBoolean(System.getProperty("toymaker.datagen"));
        if (data) {
            String path = System.getProperty("toymaker.datagen.path");

            String[] paths = path == null ? new String[0] : path.split(";");

            List<String> args = new ArrayList<>();
            args.add("-client");
            args.add("-server");
            String pathOptName = "-output";
            for (String pth : paths) {
                args.add(pathOptName);
                args.add(pth);
                pathOptName = "-extra";
            }

            try {
                DataMain.main(args.toArray(new String[0]));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                ci.cancel();
            }
        }
    }
}
