package com.oierbravo.create_jet_boots.registrate;

import com.oierbravo.create_jet_boots.ModConstants;
import com.oierbravo.create_jet_boots.infrastructure.network.ToggleJetBootsHandler;
import com.oierbravo.create_jet_boots.infrastructure.network.ToggleJetBootsPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModMessages {
    public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ModConstants.MODID);

        //Going to Client
        //registrar.playToClient(ItemSyncPayload.TYPE, ItemSyncPayload.STREAM_CODEC, ItemSyncPacket.get()::handle);
        //Going to server
        registrar.playToServer(ToggleJetBootsPayload.TYPE, ToggleJetBootsPayload.STREAM_CODEC, ToggleJetBootsHandler.get()::handle);

    }
    public static void sendToAllClients(CustomPacketPayload message) {
        PacketDistributor.sendToAllPlayers(message);
    }
    public static void sendToServer(CustomPacketPayload message){
        PacketDistributor.sendToServer(message);
    }
    public static void register() {}
}
