package com.oierbravo.create_jet_boots.infrastructure.config;

import net.createmod.catnip.config.ConfigBase;

public class ModConfigServer extends ConfigBase {

    public final ConfigInt numTicks = i(20, 1, "numTicks", Comments.numTicks);
    public final ConfigInt airAmount = i(1, 1, "numTicks", Comments.airAmount);


    private static class Comments {
        static String numTicks = "How many ticks needs to consume air.";
        static String airAmount = "How many air units consumes.";
    }

    @Override
    public String getName() {
        return "server";
    }
}
