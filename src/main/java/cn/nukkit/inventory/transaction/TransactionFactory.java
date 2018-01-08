package cn.nukkit.inventory.transaction;

import cn.nukkit.Player;
import cn.nukkit.inventory.EnchantInventory;
import cn.nukkit.inventory.FurnaceInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;

import java.util.ArrayList;
import java.util.List;

public class TransactionFactory {
    public static InventoryTransaction createTransaction(Player player, List<InventoryAction> action) {
        List<Class<? extends Inventory>> inventories = new ArrayList<>();
        for (InventoryAction inventoryAction : action) {
            if (inventoryAction instanceof SlotChangeAction) {
                Inventory inventory = ((SlotChangeAction) inventoryAction).getInventory();
                Class<? extends Inventory> aClass = inventory.getClass();
                if (!inventories.contains(aClass)) {
                    inventories.add(aClass);
                }
            }
        }
        if (inventories.contains(EnchantInventory.class)) {
            return new EnchantingTransaction(player, action);
        } else if (inventories.contains(FurnaceInventory.class)) {
            return new FurnaceTransaction(player, action);
        }


        return new InventoryTransaction(player, action);

    }
}
