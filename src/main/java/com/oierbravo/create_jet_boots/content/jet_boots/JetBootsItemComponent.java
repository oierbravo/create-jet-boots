package com.oierbravo.create_jet_boots.content.jet_boots;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;

public record JetBootsItemComponent(boolean enabled) {
    public static final Codec<JetBootsItemComponent> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Codec.BOOL.fieldOf("enabled")
                    .forGetter(i -> i.enabled))
            .apply(instance, JetBootsItemComponent::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, JetBootsItemComponent> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.BOOL, i -> i.enabled, JetBootsItemComponent::new);

    @Override
    public boolean equals(Object arg0) {
        return arg0 instanceof Boolean otherElement && otherElement == enabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled);
    }
}
