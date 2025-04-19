package com.oierbravo.create_jet_boots.event;

import com.oierbravo.create_jet_boots.ModConstants;
import com.oierbravo.create_jet_boots.content.jet_boots.JetBootsItem;
import dev.denismasterherobrine.flightapi.api.FlightAPI;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.UUID;

@EventBusSubscriber(modid = ModConstants.MODID)
public class FlightManager {
    //private static final Map<UUID, Boolean> flightPermissionMap = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player entity = event.getEntity();
        if (weOwnFlight(entity)) {
            enableFlight(entity);
        }
    }
    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player entity = event.getEntity();
        if (weOwnFlight(entity)) {
            enableFlight(entity);
        }
    }

    public static void enableFlight(Player player){
        if(JetBootsItem.getWornItem(player).isEmpty())
            return;
        if(player instanceof ServerPlayer serverPlayer)
            FlightAPI.requestFlight(ModConstants.MODID, serverPlayer);
    }
    public static void disableFlight(Player player){
        if(player instanceof ServerPlayer serverPlayer)
            FlightAPI.releaseFlight(ModConstants.MODID, serverPlayer);
    }
    public static boolean weOwnFlight(Player player){
        UUID playerUUID = player.getGameProfile().getId();
        return FlightAPI.getCurrentOwner(playerUUID).isPresent() && FlightAPI.getCurrentOwner(playerUUID).get().equals(ModConstants.MODID);
    }
    public static boolean isFlying(Player player){
        return weOwnFlight(player) && player.getAbilities().flying;
    }
}
