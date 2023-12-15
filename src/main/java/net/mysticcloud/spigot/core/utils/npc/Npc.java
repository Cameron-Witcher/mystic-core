package net.mysticcloud.spigot.core.utils.npc;

import net.mysticcloud.spigot.core.utils.misc.UID;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.loot.LootTable;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Npc implements Villager {

    Location location;
    Villager npc;
    UID uid;

    Npc(Location location) {
        this.location = location;
        npc = location.getWorld().spawn(location, Villager.class);
        npc.setAI(false);
        uid = new UID();


    }

    public Villager getBukkitEntity() {
        return npc;
    }

    public UID getUid() {
        return uid;
    }


    @Override
    public Profession getProfession() {
        return npc.getProfession();
    }

    @Override
    public void setProfession(Profession profession) {
        npc.setProfession(profession);
    }

    @Override
    public Type getVillagerType() {
        return npc.getVillagerType();
    }

    @Override
    public void setVillagerType(Type type) {
        npc.setVillagerType(type);
    }

    @Override
    public int getVillagerLevel() {
        return npc.getVillagerLevel();
    }

    @Override
    public void setVillagerLevel(int i) {
        npc.setVillagerLevel(i);
    }

    @Override
    public int getVillagerExperience() {
        return npc.getVillagerExperience();
    }

    @Override
    public void setVillagerExperience(int i) {
        npc.setVillagerExperience(i);
    }

    @Override
    public boolean sleep(Location location) {
        return npc.sleep(location);
    }

    @Override
    public void wakeup() {
        npc.wakeup();
    }

    @Override
    public void shakeHead() {
        npc.shakeHead();
    }

    @Override
    public ZombieVillager zombify() {
        return npc.zombify();
    }

    @Override
    public Inventory getInventory() {
        return npc.getInventory();
    }

    @Override
    public int getAge() {
        return npc.getAge();
    }

    @Override
    public void setAge(int i) {
        npc.setAge(i);
    }

    @Override
    public void setAgeLock(boolean b) {
        npc.setAgeLock(b);
    }

    @Override
    public boolean getAgeLock() {
        return npc.getAgeLock();
    }

    @Override
    public void setBaby() {
        npc.setBaby();
    }

    @Override
    public void setAdult() {
        npc.setAdult();
    }

    @Override
    public boolean isAdult() {
        return npc.isAdult();
    }

    @Override
    public boolean canBreed() {
        return npc.canBreed();
    }

    @Override
    public void setBreed(boolean b) {
        npc.setBreed(b);
    }

    @Override
    public void setTarget(LivingEntity livingEntity) {
        npc.setTarget(livingEntity);
    }

    @Override
    public LivingEntity getTarget() {
        return npc.getTarget();
    }

    @Override
    public void setAware(boolean b) {
        npc.setAware(b);
    }

    @Override
    public boolean isAware() {
        return npc.isAware();
    }

    @Override
    public Sound getAmbientSound() {
        return npc.getAmbientSound();
    }

    @Override
    public double getEyeHeight() {
        return npc.getEyeHeight();
    }

    @Override
    public double getEyeHeight(boolean b) {
        return npc.getEyeHeight(b);
    }

    @Override
    public Location getEyeLocation() {
        return npc.getEyeLocation();
    }

    @Override
    public List<Block> getLineOfSight(Set<Material> set, int i) {
        return npc.getLineOfSight(set, i);
    }

    @Override
    public Block getTargetBlock(Set<Material> set, int i) {
        return npc.getTargetBlock(set, i);
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(Set<Material> set, int i) {
        return npc.getLastTwoTargetBlocks(set, i);
    }

    @Override
    public Block getTargetBlockExact(int i) {
        return npc.getTargetBlockExact(i);
    }

    @Override
    public Block getTargetBlockExact(int i, FluidCollisionMode fluidCollisionMode) {
        return npc.getTargetBlockExact(i, fluidCollisionMode);
    }

    @Override
    public RayTraceResult rayTraceBlocks(double v) {
        return npc.rayTraceBlocks(v);
    }

    @Override
    public RayTraceResult rayTraceBlocks(double v, FluidCollisionMode fluidCollisionMode) {
        return npc.rayTraceBlocks(v, fluidCollisionMode);
    }

    @Override
    public int getRemainingAir() {
        return npc.getRemainingAir();
    }

    @Override
    public void setRemainingAir(int i) {
        npc.setRemainingAir(i);
    }

    @Override
    public int getMaximumAir() {
        return npc.getMaximumAir();
    }

    @Override
    public void setMaximumAir(int i) {
        npc.setMaximumAir(i);
    }

    @Override
    public int getArrowCooldown() {
        return npc.getArrowCooldown();
    }

    @Override
    public void setArrowCooldown(int i) {
        npc.setArrowCooldown(i);
    }

    @Override
    public int getArrowsInBody() {
        return npc.getArrowsInBody();
    }

    @Override
    public void setArrowsInBody(int i) {
        npc.setArrowsInBody(i);
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return npc.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(int i) {
        npc.setMaximumNoDamageTicks(i);
    }

    @Override
    public double getLastDamage() {
        return npc.getLastDamage();
    }

    @Override
    public void setLastDamage(double v) {
        npc.setLastDamage(v);
    }

    @Override
    public int getNoDamageTicks() {
        return npc.getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks(int i) {
        npc.setNoDamageTicks(i);
    }

    @Override
    public int getNoActionTicks() {
        return npc.getNoActionTicks();
    }

    @Override
    public void setNoActionTicks(int i) {
        npc.setNoActionTicks(i);
    }

    @Override
    public Player getKiller() {
        return npc.getKiller();
    }

    @Override
    public boolean addPotionEffect(PotionEffect potionEffect) {
        return npc.addPotionEffect(potionEffect);
    }

    @Override
    public boolean addPotionEffect(PotionEffect potionEffect, boolean b) {
        return npc.addPotionEffect(potionEffect, b);
    }

    @Override
    public boolean addPotionEffects(Collection<PotionEffect> collection) {
        return npc.addPotionEffects(collection);
    }

    @Override
    public boolean hasPotionEffect(PotionEffectType potionEffectType) {
        return npc.hasPotionEffect(potionEffectType);
    }

    @Override
    public PotionEffect getPotionEffect(PotionEffectType potionEffectType) {
        return npc.getPotionEffect(potionEffectType);
    }

    @Override
    public void removePotionEffect(PotionEffectType potionEffectType) {
        npc.removePotionEffect(potionEffectType);
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return npc.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight(Entity entity) {
        return npc.hasLineOfSight(entity);
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return npc.getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway(boolean b) {
        npc.setRemoveWhenFarAway(b);
    }

    @Override
    public EntityEquipment getEquipment() {
        return npc.getEquipment();
    }

    @Override
    public void setCanPickupItems(boolean b) {
        npc.setCanPickupItems(b);
    }

    @Override
    public boolean getCanPickupItems() {
        return npc.getCanPickupItems();
    }

    @Override
    public boolean isLeashed() {
        return npc.isLeashed();
    }

    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        return npc.getLeashHolder();
    }

    @Override
    public boolean setLeashHolder(Entity entity) {
        return npc.setLeashHolder(entity);
    }

    @Override
    public boolean isGliding() {
        return npc.isGliding();
    }

    @Override
    public void setGliding(boolean b) {
        npc.setGliding(b);
    }

    @Override
    public boolean isSwimming() {
        return npc.isSwimming();
    }

    @Override
    public void setSwimming(boolean b) {
        npc.setSwimming(b);
    }

    @Override
    public boolean isRiptiding() {
        return npc.isRiptiding();
    }

    @Override
    public boolean isSleeping() {
        return npc.isSleeping();
    }

    @Override
    public boolean isClimbing() {
        return npc.isClimbing();
    }

    @Override
    public void setAI(boolean b) {
        npc.setAI(b);
    }

    @Override
    public boolean hasAI() {
        return npc.hasAI();
    }

    @Override
    public void attack(Entity entity) {
        npc.attack(entity);
    }

    @Override
    public void swingMainHand() {
        npc.swingMainHand();
    }

    @Override
    public void swingOffHand() {
        npc.swingOffHand();
    }

    @Override
    public void playHurtAnimation(float v) {
        npc.playHurtAnimation(v);
    }

    @Override
    public void setCollidable(boolean b) {
        npc.setCollidable(b);
    }

    @Override
    public boolean isCollidable() {
        return npc.isCollidable();
    }

    @Override
    public Set<UUID> getCollidableExemptions() {
        return npc.getCollidableExemptions();
    }

    @Override
    public <T> T getMemory(MemoryKey<T> memoryKey) {
        return npc.getMemory(memoryKey);
    }

    @Override
    public <T> void setMemory(MemoryKey<T> memoryKey, T t) {
        npc.setMemory(memoryKey, t);
    }

    @Override
    public Sound getHurtSound() {
        return npc.getHurtSound();
    }

    @Override
    public Sound getDeathSound() {
        return npc.getDeathSound();
    }

    @Override
    public Sound getFallDamageSound(int i) {
        return npc.getFallDamageSound(i);
    }

    @Override
    public Sound getFallDamageSoundSmall() {
        return npc.getFallDamageSoundSmall();
    }

    @Override
    public Sound getFallDamageSoundBig() {
        return npc.getFallDamageSoundBig();
    }

    @Override
    public Sound getDrinkingSound(ItemStack itemStack) {
        return npc.getDrinkingSound(itemStack);
    }

    @Override
    public Sound getEatingSound(ItemStack itemStack) {
        return npc.getEatingSound(itemStack);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return npc.canBreatheUnderwater();
    }

    @Override
    public EntityCategory getCategory() {
        return npc.getCategory();
    }

    @Override
    public void setInvisible(boolean b) {
        npc.setInvisible(b);
    }

    @Override
    public boolean isInvisible() {
        return npc.isInvisible();
    }

    @Override
    public AttributeInstance getAttribute(Attribute attribute) {
        return npc.getAttribute(attribute);
    }

    @Override
    public void damage(double v) {
        npc.damage(v);
    }

    @Override
    public void damage(double v, Entity entity) {
        npc.damage(v, entity);
    }

    @Override
    public double getHealth() {
        return npc.getHealth();
    }

    @Override
    public void setHealth(double v) {
        npc.setHealth(v);
    }

    @Override
    public double getAbsorptionAmount() {
        return npc.getAbsorptionAmount();
    }

    @Override
    public void setAbsorptionAmount(double v) {
        npc.setAbsorptionAmount(v);
    }

    @Deprecated
    @Override
    public double getMaxHealth() {
        return npc.getMaxHealth();
    }

    @Deprecated
    @Override
    public void setMaxHealth(double v) {
        npc.setMaxHealth(v);
    }

    @Deprecated
    @Override
    public void resetMaxHealth() {
        npc.resetMaxHealth();
    }

    @Override
    public Location getLocation() {
        return npc.getLocation();
    }

    @Override
    public Location getLocation(Location location) {
        return npc.getLocation(location);
    }

    @Override
    public void setVelocity(Vector vector) {
        npc.setVelocity(vector);
    }

    @Override
    public Vector getVelocity() {
        return npc.getVelocity();
    }

    @Override
    public double getHeight() {
        return npc.getHeight();
    }

    @Override
    public double getWidth() {
        return npc.getWidth();
    }

    @Override
    public BoundingBox getBoundingBox() {
        return npc.getBoundingBox();
    }

    @Override
    public boolean isOnGround() {
        return npc.isOnGround();
    }

    @Override
    public boolean isInWater() {
        return npc.isInWater();
    }

    @Override
    public World getWorld() {
        return npc.getWorld();
    }

    @Override
    public void setRotation(float v, float v1) {
        npc.setRotation(v, v1);
    }

    @Override
    public boolean teleport(Location location) {
        return npc.teleport(location);
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause teleportCause) {
        return npc.teleport(location, teleportCause);
    }

    @Override
    public boolean teleport(Entity entity) {
        return npc.teleport(entity);
    }

    @Override
    public boolean teleport(Entity entity, PlayerTeleportEvent.TeleportCause teleportCause) {
        return npc.teleport(entity, teleportCause);
    }

    @Override
    public List<Entity> getNearbyEntities(double v, double v1, double v2) {
        return npc.getNearbyEntities(v, v1, v2);
    }

    @Override
    public int getEntityId() {
        return npc.getEntityId();
    }

    @Override
    public int getFireTicks() {
        return npc.getFireTicks();
    }

    @Override
    public int getMaxFireTicks() {
        return npc.getMaxFireTicks();
    }

    @Override
    public void setFireTicks(int i) {
        npc.setFireTicks(i);
    }

    @Override
    public void setVisualFire(boolean b) {
        npc.setVisualFire(b);
    }

    @Override
    public boolean isVisualFire() {
        return npc.isVisualFire();
    }

    @Override
    public int getFreezeTicks() {
        return npc.getFreezeTicks();
    }

    @Override
    public int getMaxFreezeTicks() {
        return npc.getMaxFreezeTicks();
    }

    @Override
    public void setFreezeTicks(int i) {
        npc.setFreezeTicks(i);
    }

    @Override
    public boolean isFrozen() {
        return npc.isFrozen();
    }

    @Override
    public void remove() {
        npc.remove();
    }

    @Override
    public boolean isDead() {
        return npc.isDead();
    }

    @Override
    public boolean isValid() {
        return npc.isValid();
    }

    @Override
    public void sendMessage(String s) {
        npc.sendMessage(s);
    }

    @Override
    public void sendMessage(String... strings) {
        npc.sendMessage(strings);
    }

    @Override
    public void sendMessage(UUID uuid, String s) {
        npc.sendMessage(uuid, s);
    }

    @Override
    public void sendMessage(UUID uuid, String... strings) {
        npc.sendMessage(uuid, strings);
    }

    @Override
    public Server getServer() {
        return npc.getServer();
    }

    @Override
    public String getName() {
        return npc.getName();
    }

    @Override
    public boolean isPersistent() {
        return npc.isPersistent();
    }

    @Override
    public void setPersistent(boolean b) {
        npc.setPersistent(b);
    }

    @Deprecated
    @Override
    public Entity getPassenger() {
        return npc.getPassenger();
    }

    @Deprecated
    @Override
    public boolean setPassenger(Entity entity) {
        return npc.setPassenger(entity);
    }

    @Override
    public List<Entity> getPassengers() {
        return npc.getPassengers();
    }

    @Override
    public boolean addPassenger(Entity entity) {
        return npc.addPassenger(entity);
    }

    @Override
    public boolean removePassenger(Entity entity) {
        return npc.removePassenger(entity);
    }

    @Override
    public boolean isEmpty() {
        return npc.isEmpty();
    }

    @Override
    public boolean eject() {
        return npc.eject();
    }

    @Override
    public float getFallDistance() {
        return npc.getFallDistance();
    }

    @Override
    public void setFallDistance(float v) {
        npc.setFallDistance(v);
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent entityDamageEvent) {
        npc.setLastDamageCause(entityDamageEvent);
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return npc.getLastDamageCause();
    }

    @Override
    public UUID getUniqueId() {
        return npc.getUniqueId();
    }

    @Override
    public int getTicksLived() {
        return npc.getTicksLived();
    }

    @Override
    public void setTicksLived(int i) {
        npc.setTicksLived(i);
    }

    @Override
    public void playEffect(EntityEffect entityEffect) {
        npc.playEffect(entityEffect);
    }

    @Override
    public EntityType getType() {
        return npc.getType();
    }

    @Override
    public Sound getSwimSound() {
        return npc.getSwimSound();
    }

    @Override
    public Sound getSwimSplashSound() {
        return npc.getSwimSplashSound();
    }

    @Override
    public Sound getSwimHighSpeedSplashSound() {
        return npc.getSwimHighSpeedSplashSound();
    }

    @Override
    public boolean isInsideVehicle() {
        return npc.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return npc.leaveVehicle();
    }

    @Override
    public Entity getVehicle() {
        return npc.getVehicle();
    }

    @Override
    public void setCustomNameVisible(boolean b) {
        npc.setCustomNameVisible(b);
    }

    @Override
    public boolean isCustomNameVisible() {
        return npc.isCustomNameVisible();
    }

    @Override
    public void setVisibleByDefault(boolean b) {
        npc.setVisibleByDefault(b);
    }

    @Override
    public boolean isVisibleByDefault() {
        return npc.isVisibleByDefault();
    }

    @Override
    public Set<Player> getTrackedBy() {
        return npc.getTrackedBy();
    }

    @Override
    public void setGlowing(boolean b) {
        npc.setGlowing(b);
    }

    @Override
    public boolean isGlowing() {
        return npc.isGlowing();
    }

    @Override
    public void setInvulnerable(boolean b) {
        npc.setInvulnerable(b);
    }

    @Override
    public boolean isInvulnerable() {
        return npc.isInvulnerable();
    }

    @Override
    public boolean isSilent() {
        return npc.isSilent();
    }

    @Override
    public void setSilent(boolean b) {
        npc.setSilent(b);
    }

    @Override
    public boolean hasGravity() {
        return npc.hasGravity();
    }

    @Override
    public void setGravity(boolean b) {
        npc.setGravity(b);
    }

    @Override
    public int getPortalCooldown() {
        return npc.getPortalCooldown();
    }

    @Override
    public void setPortalCooldown(int i) {
        npc.setPortalCooldown(i);
    }

    @Override
    public Set<String> getScoreboardTags() {
        return npc.getScoreboardTags();
    }

    @Override
    public boolean addScoreboardTag(String s) {
        return npc.addScoreboardTag(s);
    }

    @Override
    public boolean removeScoreboardTag(String s) {
        return npc.removeScoreboardTag(s);
    }

    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return npc.getPistonMoveReaction();
    }

    @Override
    public BlockFace getFacing() {
        return npc.getFacing();
    }

    @Override
    public Pose getPose() {
        return npc.getPose();
    }

    @Override
    public SpawnCategory getSpawnCategory() {
        return npc.getSpawnCategory();
    }

    @Override
    public boolean isInWorld() {
        return npc.isInWorld();
    }

    @Override
    public EntitySnapshot createSnapshot() {
        return npc.createSnapshot();
    }

    @Override
    public Entity copy() {
        return npc.copy();
    }

    @Override
    public Entity copy(Location location) {
        return npc.copy(location);
    }

    @Override
    public Spigot spigot() {
        return npc.spigot();
    }

    @Override
    public String getCustomName() {
        return npc.getCustomName();
    }

    @Override
    public void setCustomName(String s) {
        npc.setCustomName(s);
    }

    @Override
    public List<MerchantRecipe> getRecipes() {
        return npc.getRecipes();
    }

    @Override
    public void setRecipes(List<MerchantRecipe> list) {
        npc.setRecipes(list);
    }

    @Override
    public MerchantRecipe getRecipe(int i) throws IndexOutOfBoundsException {
        return npc.getRecipe(i);
    }

    @Override
    public void setRecipe(int i, MerchantRecipe merchantRecipe) throws IndexOutOfBoundsException {
        npc.setRecipe(i, merchantRecipe);
    }

    @Override
    public int getRecipeCount() {
        return npc.getRecipeCount();
    }

    @Override
    public boolean isTrading() {
        return npc.isTrading();
    }

    @Override
    public HumanEntity getTrader() {
        return npc.getTrader();
    }

    @Override
    public void setLootTable(LootTable lootTable) {
        npc.setLootTable(lootTable);
    }

    @Override
    public LootTable getLootTable() {
        return npc.getLootTable();
    }

    @Override
    public void setSeed(long l) {
        npc.setSeed(l);
    }

    @Override
    public long getSeed() {
        return npc.getSeed();
    }

    @Override
    public void setMetadata(String s, MetadataValue metadataValue) {
        npc.setMetadata(s, metadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String s) {
        return npc.getMetadata(s);
    }

    @Override
    public boolean hasMetadata(String s) {
        return npc.hasMetadata(s);
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {
        npc.removeMetadata(s, plugin);
    }

    @Override
    public boolean isPermissionSet(String s) {
        return npc.isPermissionSet(s);
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return npc.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String s) {
        return npc.hasPermission(s);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return npc.hasPermission(permission);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b) {
        return npc.addAttachment(plugin, s, b);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return npc.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String s, boolean b, int i) {
        return npc.addAttachment(plugin, s, b, i);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int i) {
        return npc.addAttachment(plugin, i);
    }

    @Override
    public void removeAttachment(PermissionAttachment permissionAttachment) {
        npc.removeAttachment(permissionAttachment);
    }

    @Override
    public void recalculatePermissions() {
        npc.recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return npc.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return npc.isOp();
    }

    @Override
    public void setOp(boolean b) {
        npc.setOp(b);
    }

    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return npc.getPersistentDataContainer();
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass) {
        return npc.launchProjectile(aClass);
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass, Vector vector) {
        return npc.launchProjectile(aClass, vector);
    }
}
