package cn.nukkit.inventory.transaction.action;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;

public class EnchantAction extends SlotChangeAction {
    public EnchantAction(Inventory inventory, int inventorySlot, Item sourceItem, Item targetItem) {
        super(inventory, inventorySlot, sourceItem, targetItem);
    }

    @Override
    public boolean isValid(Player source) {
        if (getSlot() == -1) return true;
        return super.isValid(source);
    }
}
