package com.oierbravo.create_jet_boots.content.jet_boots;

import com.oierbravo.create_jet_boots.event.FlightManager;
import com.oierbravo.create_jet_boots.infrastructure.config.MConfigs;
import com.simibubi.create.content.equipment.armor.BacktankUtil;
import com.simibubi.create.content.equipment.armor.BaseArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

@EventBusSubscriber
public class JetBootsItem extends BaseArmorItem {
	//private static final Map<UUID, Boolean> flightPermissionMap = new HashMap<>();
	public static final EquipmentSlot SLOT = EquipmentSlot.FEET;
	public static final ArmorItem.Type TYPE = ArmorItem.Type.BOOTS;

	private static double HORIZONTAL_FACTOR = 0.5;
	private static  double VERTICAL_FACTOR = 0.3;

	public static int TICKS_TO_DRAIN = 20;
	public static int DRAIN_AMOUNT = 1;

	private int ticks;

	public JetBootsItem(Holder<ArmorMaterial> material, Properties properties, ResourceLocation textureLoc) {
		super(material, TYPE, properties, textureLoc);
	}

	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
		return true;
	}

	public static ItemStack getWornItem(Entity entity) {
		if (!(entity instanceof LivingEntity livingEntity)) {
			return ItemStack.EMPTY;
		}
		ItemStack stack = livingEntity.getItemBySlot(SLOT);
		if (!(stack.getItem() instanceof JetBootsItem)) {
			return ItemStack.EMPTY;
		}
		return stack;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (entity instanceof ServerPlayer player) {
			if(player.isCreative() || player.isSpectator())
				return;

			ItemStack equipedBoots = getWornItem(entity);
			if(equipedBoots.isEmpty())
				return;
			if(FlightManager.weOwnFlight(player) && !player.onGround() && !player.jumping) {

				List<ItemStack> backtanks = BacktankUtil.getAllWithAir(player);
				/*if (backtanks.isEmpty()){
					FlightManager.disableFlight(player);
					return;
				}*/

 				ticks++;
				if(ticks >= TICKS_TO_DRAIN){
					BacktankUtil.consumeAir(player, backtanks.getFirst(), MConfigs.server().airAmount.get());
					ticks = 0;
				}
			}
		}
		if (!(entity instanceof Player player))
			return;
		if(player.isCreative() || player.isSpectator())
			return;
		Level world = entity.level();
		if (world.isClientSide)
			entity.getPersistentData()
					.remove("DisplayJetBootsOverlay");

		if(FlightManager.weOwnFlight(player)){
			List<ItemStack> backtanks = BacktankUtil.getAllWithAir(player);

			if(player instanceof ServerPlayer serverPlayer)
				if (backtanks.isEmpty() || getWornItem(player).isEmpty())
					FlightManager.disableFlight(serverPlayer);

			if(world.isClientSide) {
				Vec3 motion = player.getDeltaMovement();

				if(!player.jumping && player.getAbilities().flying)
					player.setDeltaMovement(motion.multiply(HORIZONTAL_FACTOR,VERTICAL_FACTOR, HORIZONTAL_FACTOR));
			}


			if(world.isClientSide) {
				entity.getPersistentData()
						.putInt("DisplayJetBootsOverlay", Math.round(backtanks.stream()
								.map(BacktankUtil::getAir)
								.reduce(0, Integer::sum)));

			}
		}
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return BacktankUtil.isBarVisible(stack, 40);
	}

	@SubscribeEvent
	public static void entityTickPre(EntityTickEvent.Pre event) {
		if (!(event.getEntity() instanceof LivingEntity entity))
			return;
		if (!(event.getEntity() instanceof Player player))
			return;

	}
}
