package com.oierbravo.create_jet_boots.infrastructure.data;

import com.oierbravo.create_jet_boots.CreateJetBoots;
import com.oierbravo.create_jet_boots.ModConstants;
import com.oierbravo.create_jet_boots.registrate.ModKeys;
import com.simibubi.create.AllKeys;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.infrastructure.data.CreateRegistrateTags;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;

public class ModDataGen {
    public static void gatherData(GatherDataEvent event) {
        addExtraRegistrateData();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        if (event.includeServer()) {
            //generator.addProvider(true, new ExamplenRecipeGen(output, event.getLookupProvider()));

        }
        event.getGenerator().addProvider(true, CreateJetBoots.registrate().setDataProvider(new RegistrateDataProvider(CreateJetBoots.registrate(), ModConstants.MODID, event)));
    }

    private static void addExtraRegistrateData() {
        CreateJetBoots.registrate().addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;
            ModKeys.provideLang(langConsumer);
        });
    }
}
