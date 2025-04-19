package com.oierbravo.create_jet_boots.event;

import com.oierbravo.create_jet_boots.ModConstants;
import com.oierbravo.create_jet_boots.content.jet_boots.JetBootsItem;
import com.oierbravo.create_jet_boots.content.jet_boots.JetBootsOverlay;
import com.oierbravo.create_jet_boots.content.jet_boots.JetBootsSound;
import com.oierbravo.create_jet_boots.infrastructure.config.MConfigs;
import com.oierbravo.create_jet_boots.infrastructure.network.ToggleJetBootsPayload;
import com.oierbravo.create_jet_boots.registrate.ModKeys;
import com.oierbravo.create_jet_boots.registrate.ModMessages;
import com.simibubi.create.foundation.sound.SoundScapes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import java.util.concurrent.ThreadLocalRandom;

import static net.createmod.ponder.PonderClient.isGameActive;

@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().screen != null)
            return;

        int key = event.getKey();
        boolean pressed = !(event.getAction() == 0);

        Minecraft mc = Minecraft.getInstance();
        if (mc.gameMode == null || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;
        if (key != ModKeys.TOGGLE_BOOTS.getBoundCode())
            return;
        if(mc.player == null)
            return;
        if(pressed)
            ModMessages.sendToServer(new ToggleJetBootsPayload(mc.player.getUUID()));
    }

    @SubscribeEvent
    public static void onTickPre(ClientTickEvent.Pre event) {
        onTick( true);
    }

    @SubscribeEvent
    public static void onTickPost(ClientTickEvent.Post event) {
        onTick(false);
    }

    public static void onTick(boolean isPreEvent) {

        if (!isGameActive())
            return;

        Minecraft mc = Minecraft.getInstance();

        if (isPreEvent) {
            return;
        }
        if(JetBootsItem.getWornItem(mc.player).isEmpty())
            return;


        if(!FlightManager.isFlying(mc.player))
            return;


        if (MConfigs.client().showParticles.get() && (mc.options.particles().get() != ParticleStatus.MINIMAL)) {

            float random = (ThreadLocalRandom.current().nextFloat() - 0.5F) * 0.1F;

            /* Inspiration from: https://github.com/BlakeBr0/IronJetpacks/tree/1.21 */

            Vec3 left = mc.player.position().add(0,0,-0.2).add(mc.player.getDeltaMovement());
            mc.particleEngine.createParticle(ParticleTypes.SMOKE, left.x, left.y, left.z, random, -0.2D, random);

            Vec3 right = mc.player.position().add(0,0,0.2).add(mc.player.getDeltaMovement());
            mc.particleEngine.createParticle(ParticleTypes.SMOKE, right.x, right.y, right.z, random, -0.2D, random);
        }

        if(MConfigs.client().playSound.get()){
            SoundScapes.play(SoundScapes.AmbienceGroup.KINETIC, mc.player.getOnPos(), 5f);
            if(!JetBootsSound.playing(mc.player.getId()))
                mc.getSoundManager().play(new JetBootsSound(mc.player));
        }
    }
    @EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
            event.registerAbove(VanillaGuiLayers.AIR_LEVEL, ModConstants.asResource("boots_display"), JetBootsOverlay.INSTANCE);
        }
    }



}
