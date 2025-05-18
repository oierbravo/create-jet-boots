package com.oierbravo.create_jet_boots.content.jet_boots;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.oierbravo.create_jet_boots.registrate.ModGuiTextures;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.RemainingAirOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.tags.FluidTags;
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

        renderGauge(airLeft, guiGraphics, player);

    }
    protected void renderGauge(int airLeft, GuiGraphics guiGraphics, LocalPlayer player){
        ItemStack backtank = RemainingAirOverlay.getDisplayedBacktank(player);
        BacktankUtil.maxAir(backtank);
        int maxAir = BacktankUtil.maxAir(backtank);
        float airPercentLeft = (float) airLeft / BacktankUtil.maxAir(backtank);
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        int xPosition = guiGraphics.guiWidth() / 2 - 98;
        int yPosition = guiGraphics.guiHeight() - 24;
        poseStack.translate(xPosition , yPosition , 0);
        poseStack.scale(.1f,.1f,.1f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
        ModGuiTextures.GAUGE_CONTENT.renderVerticalPercent(guiGraphics, 0,0,airPercentLeft);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-180f));
        ModGuiTextures.GAUGE_FRAME.render(guiGraphics, -128,-256);
        poseStack.popPose();

    }
}
