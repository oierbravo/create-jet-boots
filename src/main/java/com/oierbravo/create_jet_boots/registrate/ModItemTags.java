package com.oierbravo.create_jet_boots.registrate;

import com.oierbravo.create_jet_boots.ModConstants;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Locale;

public enum ModItemTags {
    JET_BOOTS;

    public final TagKey<Item> tag;

    ModItemTags() {
        ResourceLocation id = ModConstants.asResource(name().toLowerCase(Locale.ROOT));
        tag = ItemTags.create(id);
    }
    public boolean matches(ItemStack stack) {
        return stack.is(tag);
    }
    public static void init() {

    }
}
