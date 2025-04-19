package com.oierbravo.create_jet_boots.registrate;

import com.oierbravo.create_jet_boots.ModConstants;
import com.oierbravo.create_jet_boots.content.jet_boots.JetBootsItem;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTags;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;

import static com.oierbravo.create_jet_boots.CreateJetBoots.REGISTRATE;

public class ModItems {

    public static final ItemEntry<? extends JetBootsItem> JET_BOOTS = REGISTRATE
                            .item("jet_boots",
                                    p -> new JetBootsItem(ModArmorMaterials.JET_BOOTS, p, ModConstants.asResource("jet_boots")))
                            .properties(p -> p.durability(ArmorItem.Type.BOOTS.getDurability(7)).stacksTo(1))
                            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                                    .define('E', AllItems.ELECTRON_TUBE)
                                    .define('I', AllItems.BRASS_INGOT)
                                    .define('C', AllBlocks.COGWHEEL)
                                    .define('B', AllItems.COPPER_DIVING_BOOTS)
                                    .pattern("E E")
                                    .pattern("CBC")
                                    .pattern("I I")
                                    .unlockedBy("has_diving_armor", RegistrateRecipeProvider.has(AllTags.AllItemTags.DIVING_ARMOR.tag))
                                    .save(p, ModConstants.asResource("crafting/" + c.getName())))
                            .tag(ItemTags.FOOT_ARMOR, ModItemTags.JET_BOOTS.tag)
                            .register();

    public static void register() {}


}
