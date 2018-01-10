package cn.nukkit.inventory.transaction;

import cn.nukkit.Player;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;

import java.util.ArrayList;
import java.util.List;

public class AnvilTransaction extends InventoryTransaction {
    public AnvilTransaction(Player player, List<InventoryAction> action) {
        super(player, action);

    }

    @Override
    protected boolean matchItems() {
        List<Item> haveItems = new ArrayList<>();
        List<Item> needItems = new ArrayList<>();
        for (InventoryAction action : this.actions) {
            if (action.getTargetItem().getId() != Item.AIR) {
                needItems.add(action.getTargetItem());
            }

            if (!action.isValid(this.source)) {
                return false;
            }

            if (action.getSourceItem().getId() != Item.AIR) {
                haveItems.add(action.getSourceItem());
            }
        }

        for (Item needItem : new ArrayList<>(needItems)) {
            for (Item haveItem : new ArrayList<>(haveItems)) {
                if (needItem.equals(haveItem)) {
                    int amount = Math.min(haveItem.getCount(), needItem.getCount());
                    needItem.setCount(needItem.getCount() - amount);
                    haveItem.setCount(haveItem.getCount() - amount);
                    if (haveItem.getCount() == 0) {
                        haveItems.remove(haveItem);
                    }
                    if (needItem.getCount() == 0) {
                        needItems.remove(needItem);
                        break;
                    }
                }
            }
        }

        return haveItems.isEmpty() && (needItems.isEmpty() || (needItems.size() == 4));
    }

    @Override
    public boolean execute() {

        return super.execute();
    }
}
