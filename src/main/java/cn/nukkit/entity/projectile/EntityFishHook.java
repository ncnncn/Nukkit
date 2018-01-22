package cn.nukkit.entity.projectile;


import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.player.PlayerFishCaughtEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.particle.WaterParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.utils.MathHelper;

public class EntityFishHook extends EntityProjectile {
    public static final int NETWORK_ID = 77;
    private boolean inGround;
    private int ticksInGround;
    private Player angler;
    private int ticksInAir;
    private int ticksCatchable;
    private int ticksCaughtDelay;
    private int ticksCatchableDelay;
    private float fishApproachAngle;
    public Entity caughtEntity;
    private EntityFishHook.State state = EntityFishHook.State.FLYING;
    private int lure;
    private int luck;

    public EntityFishHook(FullChunk chunk, CompoundTag nbt, Entity shootingEntity) {
        super(chunk, nbt, shootingEntity);
        if (shootingEntity instanceof Player)
            this.angler = (Player) shootingEntity;
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public float getWidth() {
        return 0.25f;
    }

    @Override
    public float getLength() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }

    @Override
    protected float getGravity() {
        return 0.03f;
    }

    @Override
    protected float getDrag() {
        return 0.01f;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (angler == null || !angler.isOnline()) {
            this.close();
        }
        if (this.inGround) {
            ++this.ticksInGround;

            if (this.ticksInGround >= 1200) {
                this.close();
                return true;
            }
        }

        float f = 0.0F;
        Block block = server.getDefaultLevel().getBlock(this);

        if (block.getId() == Block.WATER) {
            f = 1.0F;
//            f = BlockLiquid.func_190973_f(block, this.world, blockpos);
        }

        if (this.state == EntityFishHook.State.FLYING) {
            if (this.caughtEntity != null) {
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
                this.state = EntityFishHook.State.HOOKED_IN_ENTITY;
                return true;
            }

            if (f > 0.0F) {
                this.motionX *= 0.3D;
                this.motionY *= 0.2D;
                this.motionZ *= 0.3D;
                this.state = EntityFishHook.State.BOBBING;
                return true;
            }


            super.onUpdate(currentTick);


            if (!this.inGround && !this.onGround && !this.isCollidedHorizontally) {
                ++this.ticksInAir;
            } else {
                this.ticksInAir = 0;
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            }
        } else {
            if (this.state == EntityFishHook.State.HOOKED_IN_ENTITY) {
                if (this.caughtEntity != null) {
                    if (!this.caughtEntity.isAlive()) {
                        this.caughtEntity = null;
                        this.state = EntityFishHook.State.FLYING;
                    } else {
                        this.x = this.caughtEntity.x;
                        double d2 = (double) this.caughtEntity.getHeight();
                        this.y = this.caughtEntity.getBoundingBox().minY + d2 * 0.8D;
                        this.z = this.caughtEntity.z;
                        this.setPosition(new Vector3(this.x, this.y, this.z));
                    }
                }

                return true;
            }

            if (this.state == EntityFishHook.State.BOBBING) {
                this.motionX *= 0.9D;
                this.motionZ *= 0.9D;
                double d0 = this.y + this.motionY - (double) this.getY() - (double) f;

                if (Math.abs(d0) < 0.01D) {
                    d0 += Math.signum(d0) * 0.1D;
                }

                this.motionY -= d0 * (double) this.rand.nextFloat() * 0.2D;

                this.updateHook(this);
            }
        }

        if (block.getId() != Block.WATER) {
            this.motionY -= 0.03D;
        }

//        this.moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
//        this.func_190623_q();
        double d1 = 0.92D;
        this.motionX *= 0.92D;
        this.motionY *= 0.92D;
        this.motionZ *= 0.92D;
        this.setPosition(new Vector3(this.x, this.y, this.z));

        return super.onUpdate(currentTick);

    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return canBeHooked(entity);
    }

    @Override
    public void onCollideWithEntity(Entity entity) {
        super.onCollideWithEntity(entity);

    }

    @Override
    public void spawnTo(Player player) {
        AddEntityPacket pk = new AddEntityPacket();
        pk.type = EntityFishHook.NETWORK_ID;
        pk.entityUniqueId = this.getId();
        pk.entityRuntimeId = this.getId();
        pk.x = (float) this.x;
        pk.y = (float) this.y;
        pk.z = (float) this.z;
        pk.speedX = (float) this.motionX;
        pk.speedY = (float) this.motionY;
        pk.speedZ = (float) this.motionZ;
        pk.metadata = this.dataProperties;
        player.dataPacket(pk);

        super.spawnTo(player);
    }


    private void updateHook(Vector3 position) {
        Level worldserver = this.level;
        int i = 1;
        Vector3 blockpos = position.up();

        if (this.rand.nextFloat() < 0.25F && this.server.getDefaultLevel().isRainingAt(blockpos)) {
            ++i;
        }

        if (this.rand.nextFloat() < 0.5F && !this.level.canBlockSeeSky(blockpos)) {
            --i;
        }

        if (this.ticksCatchable > 0) {
            --this.ticksCatchable;

            if (this.ticksCatchable <= 0) {
                this.ticksCaughtDelay = 0;
                this.ticksCatchableDelay = 0;
            } else {
                this.motionY -= 0.2D * (double) this.rand.nextFloat() * (double) this.rand.nextFloat();
            }
        } else if (this.ticksCatchableDelay > 0) {
            this.ticksCatchableDelay -= i;

            if (this.ticksCatchableDelay > 0) {
                this.fishApproachAngle = (float) ((double) this.fishApproachAngle + this.rand.nextGaussian() * 4.0D);
                float f = this.fishApproachAngle * 0.017453292F;
                float f1 = MathHelper.sin(f);
                float f2 = MathHelper.cos(f);
                double d0 = this.x + (double) (f1 * (float) this.ticksCatchableDelay * 0.1F);
                double d1 = (double) ((float) MathHelper.floor(this.getBoundingBox().minY) + 1.0F);
                double d2 = this.z + (double) (f2 * (float) this.ticksCatchableDelay * 0.1F);
                Block block = worldserver.getBlock(new Vector3(d0, d1 - 1.0D, d2));

                if (block.getId() == Block.WATER || block.getId() == Block.STILL_WATER) {
                    if (this.rand.nextFloat() < 0.15F) {
                        worldserver.addParticle(new WaterParticle(new Vector3(d0, d1 - 0.10000000149011612D, d2)));
                    }
//                TODO particle with motion
//                    float f3 = f1 * 0.04F;
//                    float f4 = f2 * 0.04F;
//                    worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0, (double) f4, 0.01D, (double) (-f3), 1.0D);
//                    worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0, (double) (-f4), 0.01D, (double) f3, 1.0D);
                }
            } else {
                //TODO bubber
//                this.motionY = (double) (-0.4F * MathHelper.nextFloat(this.rand, 0.6F, 1.0F));
//                this.getPlayer()(Sound.ENTITY_BOBBER_SPLASH, 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
//                double d3 = this.getEntityBoundingBox().minY + 0.5D;
//                worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.x, d3, this.z, (int) (1.0F + this.width * 20.0F), (double) this.width, 0.0D, (double) this.width, 0.20000000298023224D);
//                worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, this.x, d3, this.z, (int) (1.0F + this.width * 20.0F), (double) this.width, 0.0D, (double) this.width, 0.20000000298023224D);
                this.ticksCatchable = MathHelper.getInt(this.rand, 20, 40);
            }
        } else if (this.ticksCaughtDelay > 0) {
            this.ticksCaughtDelay -= i;
            float f5 = 0.15F;

            if (this.ticksCaughtDelay < 20) {
                f5 = (float) ((double) f5 + (double) (20 - this.ticksCaughtDelay) * 0.05D);
            } else if (this.ticksCaughtDelay < 40) {
                f5 = (float) ((double) f5 + (double) (40 - this.ticksCaughtDelay) * 0.02D);
            } else if (this.ticksCaughtDelay < 60) {
                f5 = (float) ((double) f5 + (double) (60 - this.ticksCaughtDelay) * 0.01D);
            }

            if (this.rand.nextFloat() < f5) {
                float f6 = MathHelper.nextFloat(this.rand, 0.0F, 360.0F) * 0.017453292F;
                float f7 = MathHelper.nextFloat(this.rand, 25.0F, 60.0F);
                double d4 = this.x + (double) (MathHelper.sin(f6) * f7 * 0.1F);
                double d5 = (double) ((float) MathHelper.floor(this.getBoundingBox().minY) + 1.0F);
                double d6 = this.z + (double) (MathHelper.cos(f6) * f7 * 0.1F);
                Block block1 = worldserver.getBlock(new Vector3((int) d4, (int) d5 - 1, (int) d6));

                if (block1.getId() == Block.WATER || block1.getId() == Block.STILL_WATER) {
//                    worldserver.spawnParticle(EnumParticleTypes.WATER_SPLASH, d4, d5, d6, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
                }
            }

            if (this.ticksCaughtDelay <= 0) {
                this.fishApproachAngle = MathHelper.nextFloat(this.rand, 0.0F, 360.0F);
                this.ticksCatchableDelay = MathHelper.getInt(this.rand, 20, 80);
            }
        } else {
            this.ticksCaughtDelay = MathHelper.getInt(this.rand, 100, 600);
            this.ticksCaughtDelay -= this.luck * 20 * 5;
        }
    }

    protected boolean canBeHooked(Entity entity) {
        if (entity != this.getPlayer() && entity != this && this.ticksInAir > 3 && caughtEntity == null && entity.canCollide()) {
            caughtEntity = entity;
            entity.attack(0);
            state = State.HOOKED_IN_ENTITY;
            return true;
        } else {
            return false;
        }
    }


    public int handleHookRetraction() {
        if (this.angler != null) {
            int i = 0;

            if (this.caughtEntity != null) {
                this.bringInHookedEntity();
//                this.level.setEntityState(this, (byte) 31);
                i = this.caughtEntity instanceof EntityItem ? 3 : 5;
            } else if (this.ticksCatchable > 0) {
                PlayerFishCaughtEvent event = new PlayerFishCaughtEvent(this, getPlayer());
                this.server.getPluginManager().callEvent(new PlayerFishCaughtEvent(this, getPlayer()));

//                LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer) this.world);
//                lootcontext$builder.withLuck((float) this.field_191518_aw + this.angler.getLuck());

//                for (ItemStack itemstack : this.server.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(this.rand, lootcontext$builder.build())) {
//                    EntityItem entityitem = new EntityItem(this.level, this.x, this.y, this.z, itemstack);
//                    double d0 = this.angler.x - this.x;
//                    double d1 = this.angler.y - this.y;
//                    double d2 = this.angler.z - this.z;
//                    double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
//                    double d4 = 0.1D;
//                    entityitem.motionX = d0 * 0.1D;
//                    entityitem.motionY = d1 * 0.1D + (double) MathHelper.sqrt(d3) * 0.08D;
//                    entityitem.motionZ = d2 * 0.1D;
//                    this.world.spawnEntityInWorld(entityitem);
//                    this.angler.world.spawnEntityInWorld(new EntityXPOrb(this.angler.world, this.angler.x, this.angler.y + 0.5D, this.angler.z + 0.5D, this.rand.nextInt(6) + 1));
//                    Item item = itemstack.getItem();
//
//                    if (item == Items.FISH || item == Items.COOKED_FISH) {
//                        this.angler.addStat(StatList.FISH_CAUGHT, 1);
//                    }
//                }
//
                i = 1;
            }

            if (this.inGround) {
                i = 2;
            }

            this.kill();
            return i;
        } else {
            return 0;
        }
    }

//    public void handleStatusUpdate(byte id) {
//        if (id == 31 && this.world.isRemote && this.caughtEntity instanceof EntityPlayer && ((EntityPlayer) this.caughtEntity).isUser()) {
//            this.bringInHookedEntity();
//        }
//
//        super.handleStatusUpdate(id);
//    }

    protected void bringInHookedEntity() {
        if (this.angler != null) {
            double d0 = this.angler.x - this.x;
            double d1 = this.angler.y - this.y;
            double d2 = this.angler.z - this.z;
            double d3 = 0.1D;
//            this.caughtEntity.motionX += d0 * 0.1D;
//            this.caughtEntity.motionY += d1 * 0.1D;
//            this.caughtEntity.motionZ += d2 * 0.1D;
            this.caughtEntity.addMotion(this.caughtEntity.motionX + d0 * 0.1D, this.caughtEntity.motionY + d1 * 0.1D + 1, this.caughtEntity.motionZ + d2 * 0.1D);
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
//    protected boolean canTriggerWalking() {
//        return false;
//    }

    /**
     * Will get destroyed next tick.
     */
    public void kill() {
        super.kill();

        if (this.angler != null) {
            this.angler.fishHook = null;
        }
    }

    public Player getPlayer() {
        return this.angler;
    }

    public boolean isInGround() {
        return inGround;
    }

    public void setInGround(boolean inGround) {
        this.inGround = inGround;
    }

    public int getTicksInGround() {
        return ticksInGround;
    }

    public void setTicksInGround(int ticksInGround) {
        this.ticksInGround = ticksInGround;
    }

    public Player getAngler() {
        return angler;
    }

    public void setAngler(Player angler) {
        this.angler = angler;
    }

    public int getTicksInAir() {
        return ticksInAir;
    }

    public void setTicksInAir(int ticksInAir) {
        this.ticksInAir = ticksInAir;
    }

    public int getTicksCatchable() {
        return ticksCatchable;
    }

    public void setTicksCatchable(int ticksCatchable) {
        this.ticksCatchable = ticksCatchable;
    }

    public int getTicksCaughtDelay() {
        return ticksCaughtDelay;
    }

    public void setTicksCaughtDelay(int ticksCaughtDelay) {
        this.ticksCaughtDelay = ticksCaughtDelay;
    }

    public int getTicksCatchableDelay() {
        return ticksCatchableDelay;
    }

    public void setTicksCatchableDelay(int ticksCatchableDelay) {
        this.ticksCatchableDelay = ticksCatchableDelay;
    }

    public float getFishApproachAngle() {
        return fishApproachAngle;
    }

    public void setFishApproachAngle(float fishApproachAngle) {
        this.fishApproachAngle = fishApproachAngle;
    }

    public Entity getCaughtEntity() {
        return caughtEntity;
    }

    public void setCaughtEntity(Entity caughtEntity) {
        this.caughtEntity = caughtEntity;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getLure() {
        return lure;
    }

    public void setLure(int lure) {
        this.lure = lure;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    static enum State {
        FLYING,
        HOOKED_IN_ENTITY,
        BOBBING;
    }
}
