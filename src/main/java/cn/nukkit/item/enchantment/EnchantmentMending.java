package cn.nukkit.item.enchantment;

public class EnchantmentMending extends Enchantment {
    protected EnchantmentMending() {
        super(ID_MENDING, "mending", 2, EnchantmentType.ALL);
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return level * 10;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return this.getMinEnchantAbility(level) + 15;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
