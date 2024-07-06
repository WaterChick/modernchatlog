package cz.waterchick;


import java.util.List;
import java.util.UUID;

public class ChatPlayer {
    private final UUID uuid;
    private String name;
    private final List<String> messagesList;

    public ChatPlayer(UUID uuid, String name, List<String> messagesList){
        this.uuid = uuid;
        this.name = name;
        this.messagesList = messagesList;
    }

    public List<String> getMessagesList() {
        return messagesList;
    }


    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
