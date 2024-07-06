package cz.waterchick.chatlogvelocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import cz.waterchick.ChatPlayer;
import cz.waterchick.managers.PlayerManager;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class Event {

    @Subscribe
    public void PlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        UUID uuid = player.getUniqueId();
        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(uuid);
        if(optionalChatPlayer.isEmpty()) return;
        if(player.hasPermission("chatlog.bypass")) return;
        ChatPlayer chatPlayer = optionalChatPlayer.get();
        PlayerManager.addPlayerMessage(chatPlayer, message);
    }

    @Subscribe
    public void PlayerJoinEvent(ServerConnectedEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String name = player.getUsername();
        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(player.getUniqueId());
        if(optionalChatPlayer.isPresent()) return;
        ChatPlayer chatPlayer = new ChatPlayer(uuid,name,new ArrayList<>());
        PlayerManager.addChatPlayer(chatPlayer);
    }
}
