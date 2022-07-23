package net.moddingplayground.frame.impl.enchantment;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.moddingplayground.frame.api.enchantment.v0.FrameEnchantmentTargetsEntrypoint;
import net.moddingplayground.frame.api.enchantment.v0.target.EnchantmentTargetInfo;

import java.util.List;
import java.util.stream.Collectors;

public final class FrameEnchantmentsASMImpl implements Runnable {
    @Override
    public void run() {
        FabricLoader loader = FabricLoader.getInstance();
        List<FrameEnchantmentTargetsEntrypoint> entrypoints = loader.getEntrypoints(FrameEnchantmentTargetsEntrypoint.ENTRYPOINT_ID, FrameEnchantmentTargetsEntrypoint.class);

        EnchantmentTargetManager manager = new EnchantmentTargetManager();
        entrypoints.forEach(entrypoint -> entrypoint.registerEnchantmentTargets(manager));

        List<EnchantmentTargetInfo> newTargets = manager.values();
        if (!newTargets.isEmpty()) {
            MappingResolver mappings = loader.getMappingResolver();
            EnumAdder adder = ClassTinkerers.enumBuilder(mappings.mapClassName("intermediary", "net.minecraft.class_1886"));
            newTargets.stream().collect(Collectors.toMap(EnchantmentTargetInfo::getEnumName, EnchantmentTargetInfo::className)).forEach(adder::addEnumSubclass);
            adder.build();
        }
    }
}
