package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class BlockPackedIce extends BlockIce {

    public BlockPackedIce() {
        this(0);
    }

    public BlockPackedIce(int meta) {
        super(0);
    }

    @Override
    public int getId() {
        return PACKED_ICE;
    }

    @Override
    public String getName() {
        return "Packed Ice";
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public int onUpdate(int type) {
        return 0; //not being melted
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public boolean onBreak(Item item) {
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.getEnchantments().length > 0 && item.getEnchantment(Enchantment.ID_SILK_TOUCH).getLevel() > 0) {
            return new Item[]{
                    new ItemBlock(this, 0, 1)
            };

        } else {
            return new Item[0];
        }

    }
}
