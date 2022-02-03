package me.faun.playerauctionsdiscord.commands;

import com.olziedev.playerauctions.api.PlayerAuctionsAPI;
import com.olziedev.playerauctions.api.auction.command.ACommand;
import com.olziedev.playerauctions.api.auction.command.CommandRegistry;
import me.faun.playerauctionsdiscord.PlayerAuctionsDiscord;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends ACommand {

    /**
     * The constructor of the command, this does not register the command, you will need to use {@link CommandRegistry#addSubCommand(ACommand)} to register the command.
     *
     * @param name The name of the sub-command that the player will have to enter after the main command.
     */
    public ReloadCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        if (arguments[1].equalsIgnoreCase("reload")) {
            PlayerAuctionsDiscord.getInstance().reloadConfig();
            PlayerAuctionsAPI.getInstance(playerAuctionsAPI -> playerAuctionsAPI.sendMessage(sender, "&aReloaded PlayerAuctionDiscord's config.yml"));
        }
    }
}
