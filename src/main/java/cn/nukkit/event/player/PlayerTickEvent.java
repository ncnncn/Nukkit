package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.HandlerList;

public class PlayerTickEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlers() {
        return handlers;
    }

    protected final int currentTick;

    public PlayerTickEvent(Player player, int currentTick) {
        this.player = player;
        this.currentTick = currentTick;
    }

    public int getCurrentTick() {
        return currentTick;
    }
}
