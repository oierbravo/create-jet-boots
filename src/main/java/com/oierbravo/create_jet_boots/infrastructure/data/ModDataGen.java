package com.oierbravo.create_jet_boots.infrastructure.data;

import com.oierbravo.create_jet_boots.CreateJetBoots;
import com.oierbravo.create_jet_boots.ModConstants;
import com.oierbravo.create_jet_boots.registrate.ModKeys;
import com.tterrag.registrate.providers.ProviderType;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;

public class ModDataGen {
    public static void gatherDataHighPriority(GatherDataEvent event) {
        if (event.getMods().contains(ModConstants.MODID))
            addExtraRegistrateData();
    }
    public static void gatherData(GatherDataEvent event) {
        //addExtraRegistrateData();
    }

    private static void addExtraRegistrateData() {
        CreateJetBoots.registrate().addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            ModKeys.provideLang(langConsumer);
        });
    }
}
