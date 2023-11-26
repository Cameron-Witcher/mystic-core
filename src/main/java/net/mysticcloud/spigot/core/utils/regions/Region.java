package net.mysticcloud.spigot.core.utils.regions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private int x1, y1, z1, x2, y2, z2;

    public Region(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public void setPos1(Vector vec) {
        x1 = (int) vec.getX();
        y1 = (int) vec.getY();
        z1 = (int) vec.getZ();
    }

    public void setPos2(Vector vec) {
        x2 = (int) vec.getX();
        y2 = (int) vec.getY();
        z2 = (int) vec.getZ();
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getZ1() {
        return z1;
    }

    public void setZ1(int z1) {
        this.z1 = z1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getZ2() {
        return z2;
    }

    public void setZ2(int z2) {
        this.z2 = z2;
    }

    public int getArea() {
        return (int) getLength() * getWidth() * getHeight();
    }

    public int getLength() {
        return (int) Math.sqrt(Math.pow(x2 - x1, 2)) + 1;
    }

    public int getHeight() {
        return (int) Math.sqrt(Math.pow(y2 - y1, 2)) + 1;
    }

    public int getWidth() {
        return (int) Math.sqrt(Math.pow(z2 - z1, 2)) + 1;
    }

    public Block[] getBlocks(Player player) {
        List<Block> blocks = new ArrayList<>();
        for (int x = 0; x < getLength(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int z = 0; z < getWidth(); z++) {
                    Location loc = new Location(player.getWorld(), x + Math.min(x1, x2), y + Math.min(y1, y2), z + Math.min(z1, z2));
                    player.sendMessage("(" + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ") ~(" + x + ", " + y + ", " + z + "): " + (loc.getBlock() == null ? "AIR" : loc.getBlock().getType()));
                    if (loc.getBlock() != null) blocks.add(loc.getBlock());
                }
            }

        }
        return blocks.toArray(new Block[blocks.size()]);
    }
}
