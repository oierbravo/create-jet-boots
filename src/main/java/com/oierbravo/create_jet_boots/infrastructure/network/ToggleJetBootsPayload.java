package com.oierbravo.create_jet_boots.infrastructure.network;

import com.oierbravo.create_jet_boots.ModConstants;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.UUID;

public record ToggleJetBootsPayload(UUID playerUUID) implements CustomPacketPayload {
    public static final Type<ToggleJetBootsPayload> TYPE = new Type<>(ModConstants.asResource("toggle_jet_boots"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleJetBootsPayload> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, ToggleJetBootsPayload::playerUUID,
            ToggleJetBootsPayload::new
    );
}
