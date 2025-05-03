package com.oierbravo.create_jet_boots.content.jet_boots;

import net.createmod.catnip.config.ConfigBase;

public class JetBootsConfig extends ConfigBase {
    public final int version = 1;
    public final ConfigInt numTicks = i(20, 1, "numTicks", Comments.numTicks);
    public final ConfigInt airAmount = i(1, 1, "airAmount", Comments.airAmount);

    public final ConfigBool creativeFlight = b(false, "creativeFlight", Comments.creativeFlight);

    public final ConfigFloat restrictedHorizontalFactor = f(0.5f, 0,"restrictedHorizontalFactor", Comments.restrictedHorizontalFactor);
    public final ConfigFloat restrictedVerticalFactor = f(0.3f, 0, "restrictedVerticalFactor", Comments.restrictedVerticalFactor);


    private static class Comments {
        static String numTicks = "How many ticks needs to consume air.";
        static String airAmount = "How many air units consumes.";
        static String creativeFlight = "Enables flying like in creative mode";
        static String restrictedHorizontalFactor = "X and Z axis movement limitaton factor.";
        static String restrictedVerticalFactor = "X and Z axis movement limitaton factor.X and Z axis movement limitaton factor.";
    }

    @Override
    public String getName() {
        return "jet_boots" + ".v" + version;
    }
}
