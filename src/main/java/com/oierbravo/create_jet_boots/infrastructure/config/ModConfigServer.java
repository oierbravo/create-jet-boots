package com.oierbravo.create_jet_boots.infrastructure.config;

import com.oierbravo.create_jet_boots.content.jet_boots.JetBootsConfig;
import net.createmod.catnip.config.ConfigBase;

public class ModConfigServer extends ConfigBase {
    public final JetBootsConfig jetBoots = nested(0, JetBootsConfig::new, "Jet boots");

    @Override
    public String getName() {
        return "server";
    }
}
