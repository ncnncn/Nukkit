package cn.nukkit.item;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;

/**
 * Created by Snake1999 on 2016/1/14.
 * Package cn.nukkit.item in project nukkit.
 */
public class ItemFishingRod extends Item {

    public ItemFishingRod() {
        this(0, 1);
    }

    public ItemFishingRod(Integer meta) {
        this(meta, 1);
    }

    public ItemFishingRod(Integer meta, int count) {
        super(FISHING_ROD, meta, count, "Fishing Rod");
    }

    @Override
    public int getEnchantAbility() {
        return 1;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxDurability() {
        return 360;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        boolean air = super.onClickAir(player, directionVector);
//TODO
//        if (player.fishHook != null) {
//            player.fishHook.handleHookRetraction();
//            player.fishHook = null;
//        } else {
//            CompoundTag nbt = new CompoundTag()
//                    .putList(new ListTag<DoubleTag>("Pos")
//                            .add(new DoubleTag("", player.x))
//                            .add(new DoubleTag("", player.y + player.getEyeHeight()))
//                            .add(new DoubleTag("", player.z)))
//                    .putList(new ListTag<DoubleTag>("Motion")
//                            .add(new DoubleTag("", -Math.sin(player.yaw / 180 * Math.PI) * Math.cos(player.pitch / 180 * Math.PI) * 0.5))
//                            .add(new DoubleTag("", -Math.sin(player.pitch / 180 * Math.PI) * 0.5))
//                            .add(new DoubleTag("", Math.cos(player.yaw / 180 * Math.PI) * Math.cos(player.pitch / 180 * Math.PI) * 0.5)))
//                    .putList(new ListTag<FloatTag>("Rotation")
//                            .add(new FloatTag("", (player.yaw > 180 ? 360 : 0) - (float) player.yaw))
//                            .add(new FloatTag("", (float) -player.pitch)));
//            EntityFishHook entityFishHook = new EntityFishHook(player.chunk, nbt, player);
//            player.getLevel().addEntity(entityFishHook);
//            player.fishHook = entityFishHook;
//            entityFishHook.spawnTo(player);
//        }
        return air;
    }
}

