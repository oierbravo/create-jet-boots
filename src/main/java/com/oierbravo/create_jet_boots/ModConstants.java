package com.oierbravo.create_jet_boots;

import net.minecraft.resources.ResourceLocation;

public class ModConstants {
    public static final String MODID = "create_jet_boots";
    public static final String DISPLAY_NAME = "Create Jet Boots";
    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
