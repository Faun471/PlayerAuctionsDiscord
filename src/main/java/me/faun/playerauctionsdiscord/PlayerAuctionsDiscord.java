package me.faun.playerauctionsdiscord;

import com.olziedev.playerauctions.api.PlayerAuctionsAPI;
import me.faun.playerauctionsdiscord.commands.ReloadCommand;
import me.faun.playerauctionsdiscord.listeners.AuctionListeners;
import me.faun.playerauctionsdiscord.utils.EmbedType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerAuctionsDiscord extends JavaPlugin {

    private static PlayerAuctionsDiscord instance;

    public static PlayerAuctionsDiscord getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new AuctionListeners(), this);


        PlayerAuctionsAPI.getInstance(playerAuctionsAPI -> {
            playerAuctionsAPI.getCommandRegistry().addSubCommand(new ReloadCommand("discord"));
            System.out.println("subcommand registered");
        });


        initConfig();
    }

    private void initConfig() {
        for (EmbedType type : EmbedType.values()) {
            if (!getConfig().contains(type.toString().toLowerCase() + "-embed")) {
                getConfig().addDefault(type + "-embed", null);
            }

            saveDefaultConfig();
        }
    }



}
