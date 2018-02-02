package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemSnowball;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.BlockColor;

public class BlockSnow extends BlockSolid {

    public BlockSnow() {
        this(0);
    }

    public BlockSnow(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Snow Block";
    }

    @Override
    public int getId() {
        return SNOW_BLOCK;
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public double getResistance() {
        return 1;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_SHOVEL;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.getEnchantments().length > 0 && item.getEnchantment(Enchantment.ID_SILK_TOUCH) != null && item.getEnchantment(Enchantment.ID_SILK_TOUCH).getLevel() > 0) {
            return new Item[]{
                    new ItemBlock(this, 0, 1)
            };

        } else {
            if (item.isShovel() && item.getTier() >= ItemTool.TIER_WOODEN) {
                return new Item[]{
                        new ItemSnowball(0, 4)
                };
            } else {
                return new Item[0];
            }
        }

    }

    @Override
    public BlockColor getColor() {
        return BlockColor.SNOW_BLOCK_COLOR;
    }


    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
}
