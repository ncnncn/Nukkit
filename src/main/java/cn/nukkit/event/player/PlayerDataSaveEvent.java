package cn.nukkit.event.player;

import cn.nukkit.event.Event;
import cn.nukkit.event.HandlerList;
import cn.nukkit.nbt.tag.CompoundTag;

public class PlayerDataSaveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private String playername;
    private CompoundTag tag;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerDataSaveEvent(String playername, CompoundTag tag) {
        this.playername = playername;
        this.tag = tag;
    }

    public String getPlayername() {
        return playername;
    }

    public CompoundTag getTag() {
        return tag;
    }
}
