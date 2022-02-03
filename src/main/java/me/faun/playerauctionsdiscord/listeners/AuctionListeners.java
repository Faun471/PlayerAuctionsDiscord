package me.faun.playerauctionsdiscord.listeners;

import com.olziedev.playerauctions.api.events.PlayerAuctionBuyEvent;
import com.olziedev.playerauctions.api.events.PlayerAuctionRemoveEvent;
import com.olziedev.playerauctions.api.events.PlayerAuctionSellEvent;
import github.scarsz.discordsrv.util.DiscordUtil;
import me.faun.playerauctionsdiscord.PlayerAuctionsDiscord;
import me.faun.playerauctionsdiscord.utils.EmbedType;
import me.faun.playerauctionsdiscord.utils.EmbedUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuctionListeners implements Listener {

    @EventHandler
    public void onSellEvent(PlayerAuctionSellEvent event){
        System.out.println("fired");
        if (!PlayerAuctionsDiscord.getInstance().getConfig().getBoolean("sell-embed.enabled")) {
            return;
        }

        DiscordUtil.getTextChannelById(PlayerAuctionsDiscord.getInstance().getConfig().getString("channel"))
                .sendMessageEmbeds(EmbedUtils.getEmbedBuilder(
                        EmbedType.SELL, event.getPlayerAuction(), null
                )).addFile(EmbedUtils.getImageLink(event.getPlayerAuction().getItem(), event.getSeller())).queue();
    }

    @EventHandler
    public void onBuyEvent(PlayerAuctionBuyEvent event) {
        System.out.println("fired");
        if (!PlayerAuctionsDiscord.getInstance().getConfig().getBoolean("buy-embed.enabled")) {
            return;
        }

        DiscordUtil.getTextChannelById(PlayerAuctionsDiscord.getInstance().getConfig().getString("channel"))
                .sendMessageEmbeds(EmbedUtils.getEmbedBuilder(
                        EmbedType.BUY, event.getPlayerAuction(), event.getBuyer()
                )).addFile(EmbedUtils.getImageLink(event.getItemStack(), event.getBuyer())).queue();
    }

    @EventHandler
    public void onBuyEvent(PlayerAuctionRemoveEvent event) {
        System.out.println("fired");
        if (!PlayerAuctionsDiscord.getInstance().getConfig().getBoolean("remove-embed.enabled")) {
            return;
        }

        if (event.getCause().equals(PlayerAuctionRemoveEvent.Cause.SOLD)) {
            return;
        }

        DiscordUtil.getTextChannelById(PlayerAuctionsDiscord.getInstance().getConfig().getString("channel"))
                .sendMessageEmbeds(EmbedUtils.getEmbedBuilder(
                        EmbedType.REMOVE, event.getPlayerAuction(), null
                )).addFile(EmbedUtils.getImageLink(event.getPlayerAuction().getItem(), event.getPlayerAuction().getAuctionPlayer().getOfflinePlayer())).queue();
    }
}
