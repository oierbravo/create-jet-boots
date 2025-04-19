package com.oierbravo.create_jet_boots.content.jet_boots;

import com.oierbravo.create_jet_boots.event.FlightManager;
import com.oierbravo.create_jet_boots.infrastructure.config.MConfigs;
import com.oierbravo.create_jet_boots.registrate.ModSounds;
import com.simibubi.create.AllSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
/*
 * Credit to BlakeBr0
 * https://github.com/BlakeBr0/IronJetpacks/blob/1.21/src/main/java/com/blakebr0/ironjetpacks/client/sound/JetpackSound.java
 */
public class JetBootsSound extends AbstractTickableSoundInstance {
    private static final Map<Integer, JetBootsSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
    private final Player player;

    public JetBootsSound(Player player) {
        super(ModSounds.JET_BOOTS.get(), SoundSource.PLAYERS, player.getRandom());
        this.player = player;
        this.looping = true;
        this.volume = MConfigs.client().soundVolume.getF();
        PLAYING_FOR.put(player.getId(), this);
    }

    public static boolean playing(int entityId) {
        return PLAYING_FOR.containsKey(entityId) && PLAYING_FOR.get(entityId) != null && !PLAYING_FOR.get(entityId).isStopped();
    }

    @Override
    public void tick() {
        var pos = this.player.position();

        this.x = (float) pos.x();
        this.y = (float) pos.y() - 10;
        this.z = (float) pos.z();

        if (!FlightManager.isFlying(this.player) || JetBootsItem.getWornItem(this.player).isEmpty()) {
            synchronized (PLAYING_FOR) {
                PLAYING_FOR.remove(this.player.getId());
                if(!this.isStopped())
                    AllSoundEvents.STEAM.play(this.player.level(),this.player,this.x, this.y, this.z, MConfigs.client().soundVolume.getF(),0.5f);
                this.stop();

            }
        }
    }
}
