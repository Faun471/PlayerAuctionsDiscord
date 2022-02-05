package me.faun.playerauctionsdiscord.utils;

import com.loohp.interactivechat.api.InteractiveChatAPI;
import com.loohp.interactivechatdiscordsrvaddon.graphics.ImageGeneration;
import com.olziedev.playerauctions.api.auction.Auction;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import me.faun.playerauctionsdiscord.PlayerAuctionsDiscord;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class EmbedUtils  {

    /**
     * @param type The EmbedType that will determine what type of embed will be sent.
     * @param auction The Auction; Information for the embed will be fetched from this.
     * @param buyer The buyer. This should be null if the EmbedType is not BUY.
     * @return The generated MessageEmbed.
     */
    public static MessageEmbed getEmbedBuilder(@NotNull EmbedType type, @NotNull Auction auction, @Nullable Player buyer) {
        EmbedBuilder eb = new EmbedBuilder();

        Configuration config = PlayerAuctionsDiscord.getInstance().getConfig();

        String configType = type.toString().toLowerCase();
        OfflinePlayer seller = auction.getAuctionPlayer().getOfflinePlayer();

        eb.setThumbnail("attachment://file.png");

        ConfigurationSection mainConfig = config.getConfigurationSection(configType + "-embed");
        if (mainConfig != null) {
            Map<String, Object> values = mainConfig.getValues(false);
            eb.setAuthor(processString((String) values.get("author"), auction, buyer), null, "https://www.mc-heads.net/head/" + seller.getUniqueId() + "/100/.png");
            eb.setColor(values.containsKey("color") ? Color.decode((String) values.get("color")) : Color.CYAN);
            eb.setTitle(processString((String) values.get("title"), auction, buyer), null);
            eb.setFooter(processString((String) values.get("footer"), auction, buyer), null);
        }

        ConfigurationSection fields = config.getConfigurationSection(configType + "-embed.fields");
        if (fields != null) {
            Set<String> fieldKeys = fields.getKeys(false);
            for (String key : fieldKeys) {
                String name = config.getString(fields.getCurrentPath() + "." + key + ".fieldName", null);
                String value = config.getString(fields.getCurrentPath() + "." + key + ".fieldValue", null);
                boolean inline = config.getBoolean(fields.getCurrentPath() + "." + key + ".inline", false);
                if (name == null && value == null) {
                    eb.addBlankField(inline);
                } else {
                    eb.addField(processString(name, auction, buyer), processString(value, auction, buyer), inline);
                }
            }
        }
        return eb.build();
    }

    /**
     * @param string The string that will be processed.
     * @param auction The Auction; Information for the placeholders will be fetched from this.
     * @param buyer The buyer.
     * @return The string with placeholders replaced with their respective values.
     */
    private static String processString(String string, Auction auction, @Nullable Player buyer) {
        OfflinePlayer seller = auction.getAuctionPlayer().getOfflinePlayer();
        if (string == null) {
            return " ";
        }

        return string
                .replace("%seller%", seller.getName() != null ? seller.getName() : "seller")
                .replace("%buyer%", buyer != null ? buyer.getName() : "null")
                .replace("%item_displayname%", auction.getItem().getItemMeta().hasDisplayName() ? ChatColor.stripColor(((TextComponent) auction.getItem().getItemMeta().displayName().compact()).content()) : "%item%")
                .replace("%item%", auction.getItem().getI18NDisplayName())
                .replace("%amount%", String.valueOf(auction.getItem().getAmount()))
                .replace("%price%", String.valueOf(auction.getPrice()))
                .replace("%auction_id%", String.valueOf(auction.getID()));
    }

    /**
     * @param itemStack The item that will be generated into an image.
     * @param offlinePlayer The OfflinePlayer.
     * @return a byte array of the specified item.
     */
    public static byte[] getImageLink(ItemStack itemStack, OfflinePlayer offlinePlayer) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            BufferedImage image = ImageGeneration.getItemStackImage(itemStack, InteractiveChatAPI.getOfflineICPlayer(offlinePlayer.getUniqueId()));
            Image tmp = image.getScaledInstance(64, 72, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(64, 72, BufferedImage.TYPE_INT_ARGB);

            Graphics2D graphics = resizedImage.createGraphics();
            graphics.drawImage(tmp, 0, 0, null);
            graphics.dispose();

            ImageIO.write(resizedImage, "png", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }
}
