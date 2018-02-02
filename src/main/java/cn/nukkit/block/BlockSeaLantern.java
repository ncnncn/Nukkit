package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemPrismarineCrystals;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.BlockColor;

import java.util.concurrent.ThreadLocalRandom;


public class BlockSeaLantern extends BlockTransparent {
    public BlockSeaLantern() {
        this(0);
    }

    public BlockSeaLantern(int meta) {
        super(0);
    }

    @Override
    public String getName() {
        return "Sea Lantern";
    }

    @Override
    public int getId() {
        return SEA_LANTERN;
    }

    @Override
    public double getResistance() {
        return 1.5;
    }

    @Override
    public double getHardness() {
        return 0.3;
    }

    @Override
    public int getLightLevel() {
        return 15;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.getEnchantments().length > 0 && item.getEnchantment(Enchantment.ID_SILK_TOUCH) != null && item.getEnchantment(Enchantment.ID_SILK_TOUCH).getLevel() > 0) {
            return new Item[]{
                    new ItemBlock(this, 0, 1)
            };

        } else {
            return new Item[]{
                    new ItemPrismarineCrystals(0, ThreadLocalRandom.current().nextInt(2, 4))
            };
        }

    }

    @Override
    public BlockColor getColor() {
        return BlockColor.AIR_BLOCK_COLOR;
    }
}
