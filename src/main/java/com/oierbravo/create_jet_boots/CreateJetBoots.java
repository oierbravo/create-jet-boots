package com.oierbravo.create_jet_boots;

import com.mojang.logging.LogUtils;
import com.oierbravo.create_jet_boots.infrastructure.config.MConfigs;
import com.oierbravo.create_jet_boots.infrastructure.data.ModDataGen;
import com.oierbravo.create_jet_boots.registrate.*;
import com.oierbravo.mechanicals.utility.RegistrateLangBuilder;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;

import static com.oierbravo.create_jet_boots.ModConstants.MODID;

@Mod(MODID)
public class CreateJetBoots
{

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID).defaultCreativeTab(ModCreativeTabs.MAIN_TAB.getKey());
    static {
        REGISTRATE.setTooltipModifierFactory(item ->
                new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                        .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
        );
    }
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateJetBoots(IEventBus modEventBus, ModContainer modContainer)
    {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        REGISTRATE.registerEventListeners(modEventBus);

        ModCreativeTabs.register(modEventBus);
        ModItemTags.init();
        ModItems.register();
        ModArmorMaterials.register(modEventBus);
        ModItemComponents.register(modEventBus);

        MConfigs.register(modLoadingContext,modContainer);

        ModMessages.register();
        modEventBus.addListener(ModMessages::registerNetworking);
        ModSounds.register(modEventBus);

        modEventBus.addListener(this::doClientStuff);

        modEventBus.addListener(ModDataGen::gatherData);

        generateLangEntries();
    }
    private void generateLangEntries(){
        new RegistrateLangBuilder(MODID, registrate())
            .add("itemGroup.create_jet_boots:main", "Create Jet Boots")
            .add("sounds.jet_boots", "Jet boots humming");

    }
    private void doClientStuff(final FMLClientSetupEvent event) {

    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }
}
