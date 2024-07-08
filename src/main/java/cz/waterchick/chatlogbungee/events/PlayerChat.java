package cz.waterchick.chatlogbungee.events;

import cz.waterchick.ChatPlayer;
import cz.waterchick.managers.PlayerManager;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class PlayerChat implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(player.getUniqueId());
        if(optionalChatPlayer.isPresent()) return;
        ChatPlayer chatPlayer = new ChatPlayer(uuid,name,new ArrayList<>());
        PlayerManager.addChatPlayer(chatPlayer);
    }
    @EventHandler
    public void PlayerChatEvent(ChatEvent event){
        Connection connection = event.getSender();
        String message = event.getMessage();
        if(message.startsWith("/")) return;
        if(!(connection instanceof ProxiedPlayer)) return;
        ProxiedPlayer player = (ProxiedPlayer) connection;
        UUID uuid = player.getUniqueId();
        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(uuid);
        if(optionalChatPlayer.isEmpty()) return;
        if(player.hasPermission("chatlog.bypass")) return;
        ChatPlayer chatPlayer = optionalChatPlayer.get();
        PlayerManager.addPlayerMessage(chatPlayer, message);
    }


}
