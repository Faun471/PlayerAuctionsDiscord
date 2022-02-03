# PlayerAuctionsDiscord

[![GitHub release](https://badgen.net/github/release/Faun471/PlayerAuctionsDiscord)](https://github.com/Faun471/PlayerAuctionsDiscord/releases)


## Introduction

I was using DiscordSRV's alerts.yml feature to send embeds for player auctions, but I found out that potions, tipped arrows, player heads, etc., are either impossible to implement, or would be too complicated. 
This plugin aims to solve that.

## Why the recode?

The plugin was recoded because of the limitations that it had due to how features were implemented. 

1. It was close to impossible to actually display accurate images of tinted arrows and potions.
2. The images that the old plugin uses and relies on are hosted in a cdn, meaning that if it goes down, the images won't show up.
3. I also would have to manually add every block in the game to the cdn if I wanted to maintain the plugin.
4. It is impossible to support resource packs.

All of these (*as well as the bad code quality*) have been addressed with the recode.

## What does it do?

This plugin sends an embed to a discord channel when someone sells, buys, or removes an item in the auction house. Here's a quick example.

![](https://i.imgur.com/giUvvmS.png "Embeds")

## Required Dependencies


- [InteractiveChatDiscordSRVAddon](https://www.spigotmc.org/resources/interactivechat-discordsrv-addon-show-items-and-invs-on-discord-preview-discord-images-in-game.83917/)

- [PlayerAuctions](https://www.spigotmc.org/resources/%E2%AD%90-player-auctions%E2%AD%90-%E2%9E%A2-let-your-players-sell-items-1-7-1-17-1.83073/)

These plugins are **required.** The plugin will never work without both of them.

## Support

If you somehow stumbled upon this repo and had issues with the plugin, feel free to DM me on Discord: `Faun#6901`. I'll try to help out whenever I'm available.
