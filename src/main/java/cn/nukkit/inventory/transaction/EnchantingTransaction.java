package cn.nukkit.inventory.transaction;

import cn.nukkit.Player;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;

import java.util.ArrayList;
import java.util.List;

public class EnchantingTransaction extends InventoryTransaction {
    public EnchantingTransaction(Player source, List<InventoryAction> actions) {
        super(source, actions);
    }


    @Override
    public boolean canExecute() {
        ArrayList<InventoryAction> rm = new ArrayList<>();
        for (InventoryAction action : getActions()) {
            if (action instanceof SlotChangeAction) {
                if (((SlotChangeAction) action).getSlot() == -1) {
                    rm.add(action);
                }
            }
        }
        for (InventoryAction action : rm) {
            actions.remove(action);
        }
        this.squashDuplicateSlotChanges();
        if (rm.size() > 0) {
            return true;
        } else {
            return super.canExecute();
        }
    }

    @Override
    public boolean execute() {
        return super.execute();
    }
}
