package cz.waterchick.chatlogbukkit.events;

import cz.waterchick.ChatPlayer;
import cz.waterchick.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class PlayerChat implements Listener {

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String message = event.getMessage();
        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(uuid);
        if(!optionalChatPlayer.isPresent()) return;
        if(player.hasPermission("chatlog.bypass")) return;
        ChatPlayer chatPlayer = optionalChatPlayer.get();
        PlayerManager.addPlayerMessage(chatPlayer, message);
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(player.getUniqueId());
        if(optionalChatPlayer.isPresent()) return;
        ChatPlayer chatPlayer = new ChatPlayer(uuid,name,new ArrayList<>());
        PlayerManager.addChatPlayer(chatPlayer);
    }
}
