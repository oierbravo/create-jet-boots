package com.oierbravo.create_jet_boots.infrastructure.network;

import com.oierbravo.create_jet_boots.ModConstants;
import com.oierbravo.create_jet_boots.event.FlightManager;
import dev.denismasterherobrine.flightapi.api.FlightAPI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;

public class ToggleJetBootsHandler {
    public static final ToggleJetBootsHandler INSTANCE = new ToggleJetBootsHandler();

    public static ToggleJetBootsHandler get() {
        return INSTANCE;
    }

    public void handle(final ToggleJetBootsPayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var level = context.player().level();
            Player player = level.getPlayerByUUID(payload.playerUUID());
            if(player instanceof ServerPlayer serverPlayer){
                Optional<String> currentOwner = FlightAPI.getCurrentOwner(payload.playerUUID());
                if(currentOwner.isPresent() && currentOwner.get().equals(ModConstants.MODID)) {
                    FlightManager.disableFlight(serverPlayer);
                } else {
                    if(!player.isCreative() || !player.isSpectator())
                        FlightManager.enableFlight(serverPlayer);
                }
            }

        });
    }
}
