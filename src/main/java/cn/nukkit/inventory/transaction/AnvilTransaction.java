package cn.nukkit.inventory.transaction;

import cn.nukkit.Player;
import cn.nukkit.inventory.AnvilInventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.item.Item;

import java.util.ArrayList;
import java.util.HashSet;
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

        return haveItems.isEmpty() && (needItems.isEmpty() || (needItems.size() == 2));
    }

    @Override
    public boolean canExecute() {
        this.squashDuplicateSlotChanges();
        Boolean isResult = false;

        for (InventoryAction action : this.actions) {
            if (action instanceof SlotChangeAction) {
                int slot = ((SlotChangeAction) action).getSlot();

                if (((SlotChangeAction) action).getInventory() instanceof AnvilInventory && slot == 2 && action.getSourceItem().getId() == Item.AIR) {
                    ((SlotChangeAction) action).getInventory().clear(0, true);
                    ((SlotChangeAction) action).getInventory().clear(1, true);
                    ((SlotChangeAction) action).getInventory().clear(2, true);
                    isResult = true;
                }
            }

        }

//        List<Item> haveItems = new ArrayList<>();
//        List<Item> needItems = new ArrayList<>();

        if (isResult) {
            HashSet<InventoryAction> actions = new HashSet<>();
            for (InventoryAction action : this.actions) {
                if (action instanceof SlotChangeAction) {
                    int slot = ((SlotChangeAction) action).getSlot();
                    if (((SlotChangeAction) action).getInventory() instanceof AnvilInventory && slot == 2) {
                        actions.add(action);
                    }
                    if (!(((SlotChangeAction) action).getInventory() instanceof AnvilInventory)) {
                        actions.add(action);
                    }
                }
            }
            this.actions = actions;
            return true;
        } else {
            return matchItems() && this.actions.size() > 0;
        }
    }

    @Override
    public boolean execute() {

        return super.execute();
    }
}
