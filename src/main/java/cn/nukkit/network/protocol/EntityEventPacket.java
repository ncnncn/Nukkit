package cn.nukkit.network.protocol;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public class EntityEventPacket extends DataPacket {
    public static final int NETWORK_ID = ProtocolInfo.ENTITY_EVENT_PACKET;


    public static final int NONE = 0;
    public static final int JUMP = 1;
    public static final int HURT = 2;
    public static final int DEATH_ANIMATION = 3;
    public static final int START_ATTACKING = 4;
    public static final int STOP_ATTACKING = 5;
    public static final int TAMING_FAILED = 6;
    public static final int TAMING_SUCCEEDED = 7;
    public static final int SHAKE_WETNESS = 8;
    public static final int USE_ITEM = 9;
    public static final int EAT_GRASS = 10;
    public static final int FISHHOOK_BUBBLE = 11;
    public static final int FISHHOOK_FISHPOS = 12;
    public static final int FISHHOOK_HOOKTIME = 13;
    public static final int FISHHOOK_TEASE = 14;
    public static final int SQUID_FLEEING = 15;
    public static final int ZOMBIE_CONVERTING = 16;
    public static final int PLAY_AMBIENT = 17;
    public static final int SPAWN_ALIVE = 18;
    public static final int START_OFFER_FLOWER = 19;
    public static final int STOP_OFFER_FLOWER = 20;
    public static final int LOVE_HEARTS = 21;
    public static final int VILLAGER_ANGRY = 22;
    public static final int VILLAGER_HAPPY = 23;
    public static final int WITCH_HAT_MAGIC = 24;
    public static final int FIREWORKS_EXPLODE = 25;
    public static final int IN_LOVE_HEARTS = 26;
    public static final int SILVERFISH_MERGE_ANIM = 27;
    public static final int GUARDIAN_ATTACK_SOUND = 28;
    public static final int DRINK_POTION = 29;
    public static final int THROW_POTION = 30;
    public static final int PRIME_TNTCART = 31;
    public static final int PRIME_CREEPER = 32;
    public static final int AIR_SUPPLY = 33;
    public static final int ADD_PLAYER_LEVELS = 34;
    public static final int GUARDIAN_MINING_FATIGUE = 35;
    public static final int AGENT_SWING_ARM = 36;
    public static final int DRAGON_START_DEATH_ANIM = 37;
    public static final int GROUND_DUST = 38;
    public static final int SHAKE = 39;
    public static final int FEED = 57;
    public static final int BABY_EAT = 60;
    public static final int INSTANT_DEATH = 61;
    public static final int NOTIFY_TRADE = 62;
    public static final int LEASH_DESTROYED = 63;
    public static final int CARAVAN_UPDATED = 64;
    public static final int TALISMAN_ACTIVATE = 65;
    public static final int UPDATE_STRUCTURE_FEATURE = 66;
    public static final int PLAYER_SPAWNED_MOB = 67;
    public static final int PUKE = 68;
    public static final int UPDATE_STACK_SIZE = 69;



    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    public long eid;
    public int event;
    public int data;

    @Override
    public void decode() {
        this.eid = this.getEntityRuntimeId();
        this.event = this.getByte();
        this.data = this.getVarInt();
    }

    @Override
    public void encode() {
        this.reset();
        this.putEntityRuntimeId(this.eid);
        this.putByte((byte) this.event);
        this.putVarInt((byte) this.data);
    }
}
