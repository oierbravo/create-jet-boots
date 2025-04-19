package com.oierbravo.create_jet_boots.content.jet_boots;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class JetBootsOverlay implements LayeredDraw.Layer  {
    public static final JetBootsOverlay INSTANCE = new JetBootsOverlay();

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui || mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
            return;

        LocalPlayer player = mc.player;
        if (player == null)
            return;
        if (player.isCreative())
            return;
        if (!player.getPersistentData()
                .contains("DisplayJetBootsOverlay"))
            return;
        if (player.getPersistentData()
                .contains("VisualBacktankAir"))
            return;

        if (player.isEyeInFluid(FluidTags.WATER) || player.isInLava())
            return;

        int airLeft = player.getPersistentData()
                .getInt("DisplayJetBootsOverlay");


        int yPosition = renderTankTimer(airLeft, guiGraphics, player);
        renderBoots(guiGraphics, player, yPosition);

    }
    public int renderTankTimer(int airLeft, GuiGraphics guiGraphics, LocalPlayer player){
        int timeLeft = airLeft / JetBootsItem.DRAIN_AMOUNT;
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        ItemStack backtank = RemainingAirOverlay.getDisplayedBacktank(player);


        int yPosition = guiGraphics.guiHeight() - 53 + (backtank.has(DataComponents.FIRE_RESISTANT) ? 9 : 0);
        poseStack.translate(guiGraphics.guiWidth() / 2 + 90, yPosition , 0);

        Minecraft mc = Minecraft.getInstance();

        Component text = Component.literal(StringUtil.formatTickDuration(Math.max(0, timeLeft - 1) * JetBootsItem.TICKS_TO_DRAIN, mc.level.tickRateManager().tickrate()));
        GuiGameElement.of(backtank)
                .at(0, 0)
                .render(guiGraphics);
        int color = 0xFF_FFFFFF;
        if (timeLeft < 60 && timeLeft % 2 == 0) {
            color = Color.mixColors(0xFF_FF0000, color, Math.max(timeLeft / 60f, .25f));
        }
        guiGraphics.drawString(mc.font, text, 16, 5, color);
        poseStack.popPose();
        return yPosition;
    }

        public void renderBoots(GuiGraphics guiGraphics, LocalPlayer player, int yPosition){
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();

            int bootsYDistance = 20;
            ItemStack boots = getDisplayedBoots(player);
            //poseStack.translate(guiGraphics.guiWidth() / 2, guiGraphics.guiHeight() /2 , 0);
            poseStack.translate(guiGraphics.guiWidth() / 2 + 90, yPosition + bootsYDistance, 0);
            GuiGameElement.of(boots)
                    .at(0, 0)
                    .render(guiGraphics);
            poseStack.popPose();
        }


        public static ItemStack getDisplayedBoots(LocalPlayer player) {
        ItemStack boots = JetBootsItem.getWornItem(player);
        if (!boots.isEmpty()) {
            return boots;
        }
        return AllItems.COPPER_BACKTANK.asStack();
    }
}
