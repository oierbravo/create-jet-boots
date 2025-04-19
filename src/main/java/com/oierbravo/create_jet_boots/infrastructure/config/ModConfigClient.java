package com.oierbravo.create_jet_boots.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class ModConfigClient extends ConfigBase {
    public ConfigBool showParticles = b(true, "showParticles", Comments.showParticles);
    public ConfigBool playSound = b(true, "playSound", Comments.playSound);
    public ConfigFloat soundVolume = f(0.3f,0,"soundVolume", Comments.soundVolume);

    private static class Comments {
        static String showParticles = "Show particles";
        static String playSound = "Show particles";
        static String soundVolume = "Show particles";
    }

    @Override
    public String getName() {
        return "client";
    }
}
