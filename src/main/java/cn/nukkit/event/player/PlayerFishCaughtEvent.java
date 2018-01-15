package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.entity.projectile.EntityFishHook;
import cn.nukkit.event.HandlerList;

public class PlayerFishCaughtEvent extends PlayerEvent {
    EntityFishHook fishHook;
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerFishCaughtEvent(EntityFishHook entityFishHook, Player player) {
        super();
        this.player = player;
        this.fishHook = entityFishHook;
    }

    public EntityFishHook getFishHook() {
        return fishHook;
    }

    public void setFishHook(EntityFishHook fishHook) {
        this.fishHook = fishHook;
    }
}
