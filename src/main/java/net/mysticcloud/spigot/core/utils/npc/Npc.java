package net.mysticcloud.spigot.core.utils.npc;

import net.mysticcloud.spigot.core.utils.CoreUtils;
import net.mysticcloud.spigot.core.utils.MessageUtils;
import net.mysticcloud.spigot.core.utils.gui.GuiManager;
import net.mysticcloud.spigot.core.utils.misc.UID;
import net.mysticcloud.spigot.core.utils.placeholder.PlaceholderUtils;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.json2.JSONArray;
import org.json2.JSONObject;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Npc implements Entity {

    private final Entity npc;
    private final UID uid;
    private final JSONArray actions = new JSONArray();
    private Location location;


    Npc(Location location) {
        this(location, EntityType.VILLAGER);
    }
    Npc(Location location, EntityType type) {
        this.location = location;
        npc = location.getWorld().spawn(location, type.getEntityClass());
        if(npc instanceof LivingEntity)
            ((LivingEntity) npc).setAI(false);
        npc.setMetadata("npc", new FixedMetadataValue(CoreUtils.getPlugin(), this));
        uid = new UID();
    }

    public void addAction(JSONObject action) {
        actions.put(action);
    }

    public JSONArray getActions() {
        return actions;
    }

    public void move(Location location) {
        this.location = location;
        npc.teleport(location);
    }

    public void move(Vector vector) {
        this.location.add(vector);
        npc.teleport(location);
    }

    public boolean processActions(Player player) {
        for (int i = 0; i < actions.length(); i++)
            if (!processAction(player, actions.getJSONObject(i))) return false;
        return true;
    }

    private boolean processAction(Player player, JSONObject action) {
        switch (action.getString("action").toLowerCase()) {
            case "sound":
                player.playSound(player.getLocation(), Sound.valueOf(action.getString("sound")), 10F, 1F);
                return true;
//            case "sell":
//                ItemStack t = getItem(player);
//                if (action.has("amount"))
//                    t.setAmount(Integer.parseInt(action.getString("amount")));
//                if (player.getInventory().contains(t)) {
//                    player.getInventory().remove(t);
//                    Utils.getEconomy().depositPlayer(player, item.getSellPrice());
//                    return true;
//                } else
//                    return false;
            case "buy":
                int price = action.has("price") ? action.getInt("price") : 1;
                if (action.getString("buy_type").equalsIgnoreCase("inventory")) {
                    Material mat = Material.valueOf(action.getString("item"));
                    return CoreUtils.consumeItem(player, price, mat);
                }
//                if (action.getString("buy_type").equalsIgnoreCase("economy")) {
//                    if (Utils.getEconomy().has(player, price)) {
//                        Utils.getEconomy().withdrawPlayer(player, price);
//                        return true;
//                    }
//                }
                return false;
            case "send_message":
                player.sendMessage(MessageUtils.colorize(action.getString("message")));
                return true;
            case "open_gui":
                try {
                    GuiManager.openGui(player, GuiManager.getGui(action.getString("gui")));
                } catch (NullPointerException ex) {
                    player.sendMessage(MessageUtils.prefixes("gui") + "There was an error opening that GUI. Does it exist?");
                }
                return true;
            case "join_server":
                MessageUtils.sendPluginMessage(player, "BungeeCord", "Connect", action.getString("server"));
                return true;

            case "command":
                String sender = action.has("sender") ? action.getString("sender") : "player";
                String cmd = PlaceholderUtils.replace(player, action.getString("command"));
                Bukkit.dispatchCommand(sender.equalsIgnoreCase("CONSOLE") ? Bukkit.getConsoleSender() : player, cmd);
                return true;
            case "close_gui":
                player.closeInventory();
                return true;
        }


        if (action.has("error_message"))
            player.sendMessage(PlaceholderUtils.replace(player, action.getString("error_message")));

        return false;
    }

    public Entity getBukkitEntity() {
        return npc;
    }

    public UID getUid() {
        return uid;
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

}
