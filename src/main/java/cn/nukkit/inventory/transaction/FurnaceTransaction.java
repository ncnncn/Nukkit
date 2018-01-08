package cn.nukkit.inventory.transaction;

import cn.nukkit.Player;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemEdible;
import cn.nukkit.math.Vector3;

import java.util.List;

public class FurnaceTransaction extends InventoryTransaction {
    public FurnaceTransaction(Player player, List<InventoryAction> action) {
        super(player, action);
    }

    @Override
    public boolean execute() {
        if (!super.execute()) return false;
        for (InventoryAction action : actions) {
            if (action instanceof SlotChangeAction && ((SlotChangeAction) action).getSlot() == 2 && action.getSourceItem().getId() != Item.AIR) {
                Item sourceItem = action.getSourceItem();
                switch (sourceItem.getId()) {
                    case Item.IRON_INGOT:
                        super.source.awardAchievement("acquireIron");
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.7 * sourceItem.getCount()));
                        break;
                    case Item.GOLD_INGOT:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (1.0 * sourceItem.getCount()));
                        break;
                    case Item.SAND:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.1 * sourceItem.getCount()));
                        break;
                    case Item.BRICK:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.3 * sourceItem.getCount()));
                        break;
                    case Item.NETHER_BRICK:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.3 * sourceItem.getCount()));
                        break;
                    case Item.TERRACOTTA:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.35 * sourceItem.getCount()));
                        break;
                    case Item.STONE_BRICK://Cracked Stone Brick?
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.1 * sourceItem.getCount()));
                        break;
                    case Item.STONE:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.1 * sourceItem.getCount()));
                        break;
                    case Item.GRAY_GLAZED_TERRACOTTA:
                    case Item.GREEN_GLAZED_TERRACOTTA:
                    case Item.BLACK_GLAZED_TERRACOTTA:
                    case Item.BLUE_GLAZED_TERRACOTTA:
                    case Item.BROWN_GLAZED_TERRACOTTA:
                    case Item.CYAN_GLAZED_TERRACOTTA:
                    case Item.LIGHT_BLUE_GLAZED_TERRACOTTA:
                    case Item.LIME_GLAZED_TERRACOTTA:
                    case Item.MAGENTA_GLAZED_TERRACOTTA:
                    case Item.ORANGE_GLAZED_TERRACOTTA:
                    case Item.PINK_GLAZED_TERRACOTTA:
                    case Item.PURPLE_GLAZED_TERRACOTTA:
                    case Item.RED_GLAZED_TERRACOTTA:
                    case Item.SILVER_GLAZED_TERRACOTTA:
                    case Item.WHITE_GLAZED_TERRACOTTA:
                    case Item.YELLOW_GLAZED_TERRACOTTA:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.1 * sourceItem.getCount()));
                        break;
                    case Item.DIAMOND:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (1.0 * sourceItem.getCount()));
                        break;
                    case Item.DYE:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.2 * sourceItem.getCount()));
                        break;
                    case Item.REDSTONE_DUST:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.7 * sourceItem.getCount()));
                        break;
                    case Item.COAL:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.1 * sourceItem.getCount()));
                        break;
                    case Item.EMERALD:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (1.0 * sourceItem.getCount()));
                        break;
                    case Item.NETHER_QUARTZ:
                        source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.2 * sourceItem.getCount()));
                        break;
                    default:
                        if (sourceItem instanceof ItemEdible) {
                            source.getLevel().dropExpOrb(new Vector3(source.getFloorX(), source.getFloorY(), source.getFloorZ()), (int) (0.35 * sourceItem.getCount()));
                        }
                        break;
                }

            }
        }


        return true;
    }
}
