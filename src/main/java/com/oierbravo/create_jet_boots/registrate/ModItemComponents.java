package com.oierbravo.create_jet_boots.registrate;

import com.oierbravo.create_jet_boots.ModConstants;
import com.oierbravo.create_jet_boots.content.jet_boots.JetBootsItemComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.UnaryOperator;

public class ModItemComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, ModConstants.MODID);

    public static final DataComponentType<JetBootsItemComponent> JET_BOOTS = register(
            "jet_boots",
            builder -> builder.persistent(JetBootsItemComponent.CODEC).networkSynchronized(JetBootsItemComponent.STREAM_CODEC)
    );

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
        DATA_COMPONENTS.register(name, () -> type);
        return type;
    }

    @ApiStatus.Internal
    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
