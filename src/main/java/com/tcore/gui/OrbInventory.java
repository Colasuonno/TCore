package com.tcore.gui;

import com.tcore.gui.listeners.OrbInventoryListener;
import com.tcore.gui.util.InventoryUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OrbInventory {

    public static List<OrbInventory> inventories = new ArrayList<>();
    public OrbInventoryItem[] items;
    private Inventory inventory;
    private String metaData;
    private OrbInventoryListener listener;
    private boolean destroy;
    private boolean cancel;

    /**
     * Create Orb Inventory
     *
     * @param title    Title of Inventory also with &
     * @param size     size of inventory (must be 9%=0)
     * @param metaData optional meta data (tag) to inventory
     * @param destroy  if true, when u close the inv it will be destroyed else not
     */
    public OrbInventory(String title, int size, String metaData, boolean destroy, boolean cancel) {
        Validate.notNull(title, "Title cannot be null");
        if (metaData == null) metaData = "default-meta";
        assert size % 9 != 0 : "Size must be multiple of 9";
        inventory = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', title));
        items = new OrbInventoryItem[size];
        this.metaData = metaData;
        this.destroy = destroy;
        this.cancel = cancel;
        inventories.add(this);
        listener();
    }


    /**
     * Create Orb Inventory
     *
     * @param inventory Title of Inventory also with &
     * @param metaData  optional meta data (tag) to inventory
     * @param destroy   if true, when u close the inv it will be destroyed else not
     */
    public OrbInventory(Inventory inventory, String metaData, boolean destroy, boolean cancel) {
        Validate.notNull(inventory, "Inventory cannot be null");
        if (metaData == null) metaData = "default-meta";
        this.inventory = inventory;
        items = InventoryUtil.fromItemStackArray(inventory.getContents());
        this.metaData = metaData;
        this.destroy = destroy;
        this.cancel = cancel;
        inventories.add(this);
        listener();
    }

    /**
     * Create Orb Inventory
     *
     * @param metaData optional meta data (tag) to inventory
     * @param destroy  if true, when u close the inv it will be destroyed else not
     */
    protected OrbInventory(String metaData, boolean destroy, boolean cancel) {
        if (metaData == null) metaData = "default-meta";
        this.metaData = metaData;
        this.destroy = destroy;
        this.cancel = cancel;
        inventories.add(this);
        listener();
    }

    public OrbInventory(Inventory inventory, String metaData) {
        this(inventory, metaData, true, false);
    }

    public OrbInventory(Inventory inventory) {
        this(inventory, "default-meta", true, false);
    }

    public OrbInventory(String title, int size) {
        this(title, size, "default-meta", true, false);
    }

    /**
     * Converts the player inventory to a String array of Base64 strings. First string is the content and second string is the armor.
     *
     * @param playerInventory to turn into an array of strings.
     * @return Array of strings: [ main content, armor content ]
     * @throws IllegalStateException
     */
    public static String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
        //get the main content part, this doesn't return the armor
        String content = toBase64(playerInventory);
        String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

        return new String[]{content, armor};
    }

    /**
     * A method to serialize an {@link ItemStack} array to Base64 String.
     * <p>
     * <p/>
     * <p>
     * Based off of {@link #toBase64(Inventory)}.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     * @throws IllegalStateException
     */
    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * A method to serialize an inventory to Base64 string.
     * <p>
     * <p/>
     * <p>
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param inventory to serialize
     * @return Base64 string of the provided inventory
     * @throws IllegalStateException
     */
    public static String toBase64(Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(inventory.getSize());

            // Save every element in the list
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    /**
     * A method to get an {@link Inventory} from an encoded, Base64, string.
     * <p>
     * <p/>
     * <p>
     * Special thanks to Comphenix in the Bukkit forums or also known
     * as aadnk on GitHub.
     *
     * <a href="https://gist.github.com/aadnk/8138186">Original Source</a>
     *
     * @param data Base64 string of data containing an inventory.
     * @return Inventory created from the Base64 string.
     * @throws IOException
     */
    public static Inventory fromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, dataInput.readInt());

            // Read the serialized inventory
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     * <p>
     * <p/>
     * <p>
     * Base off of {@link #fromBase64(String)}.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     * @throws IOException
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public OrbInventoryItem getItem(int rawSlot) {
        return items[rawSlot];
    }

    public void addItem(OrbInventoryItem item) {
        if (inventory.getSize() / 9 < item.getY())
            throw new IllegalArgumentException("Y value is too high for inventory size (y=" + item.getY() + ",size=" + inventory.getSize() + ")");
        boolean flag = false;
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                inventory.setItem(i, item.getItemStack());
                items[i] = item;
                flag = true;
                break;
            }
        }
        if (!flag) {
            // TODO: O si droppa l'item o si da un errore oppure non si fa niente

            // this line is important
            // this line is important
        }

    }

    private void listener() {
        this.listener = new OrbInventoryListener() {
            @Override
            public void onClick(InventoryClickEvent event, Player player, OrbInventory inventory, OrbInventoryItem item) {

            }

            @Override
            public void onRightClick(InventoryClickEvent event, Player player, OrbInventory inventory, OrbInventoryItem item) {

            }

            @Override
            public void onLeftClick(InventoryClickEvent event, Player player, OrbInventory inventory, OrbInventoryItem item) {

            }

            @Override
            public void onClose(Player player, OrbInventory inventory) {

            }

            @Override
            public void onOpen(InventoryOpenEvent event, Player player, OrbInventory inventory) {

            }
        };
    }

    public void setItem(OrbInventoryItem item) {

        inventory.setItem(item.getBukkitPosition(), item.getItemStack());
        items[item.getBukkitPosition()] = item;
    }

    public void setItem(OrbInventoryItem item, int... bukkitPos) {
        for (int pos : bukkitPos) {
            inventory.setItem(pos, item.getItemStack());
            items[pos] = item;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getMetaData() {
        return metaData;
    }

    public OrbInventoryListener getListener() {
        return listener;
    }

    public void setListener(OrbInventoryListener listener) {
        this.listener = listener;
    }

    public boolean isCancel() {
        return cancel;
    }

    public OrbInventoryItem[] getItems() {
        return items;
    }

    public boolean isDestroy() {
        return destroy;
    }
}
