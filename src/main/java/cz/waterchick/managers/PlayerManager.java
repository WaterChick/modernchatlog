package cz.waterchick.managers;

import cz.waterchick.ChatPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerManager {
    private final static List<ChatPlayer> chatPlayerList = new ArrayList<>();



    public static Optional<ChatPlayer> getPrestigePlayer(UUID uuid){
        return chatPlayerList.stream().filter(chatPlayer -> chatPlayer.getUuid().equals(uuid)).findAny();
    }

    public static Optional<ChatPlayer> getPrestigePlayer(String name){
        return chatPlayerList.stream().filter(chatPlayer -> chatPlayer.getName().equals(name)).findAny();
    }

    public static void addPlayerMessage(ChatPlayer chatPlayer, String message){
        chatPlayer.getMessagesList().add(0,message);
    }

    public static void addChatPlayer(ChatPlayer chatPlayer){
        chatPlayerList.add(chatPlayer);
    }

    public static List<ChatPlayer> getChatPlayerList() {
        return chatPlayerList;
    }
}
