package cz.waterchick.configs;

import cz.waterchick.ChatPlayer;
import cz.waterchick.managers.PlayerManager;
import dev.dejvokep.boostedyaml.block.implementation.Section;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class DataConfig extends Config{
    public DataConfig(File dataFolder) {
        super(dataFolder, "data.yml");
    }

    @Override
    public void onLoad() {
        Section uuidSection = getConfig().getSection("");
        if(uuidSection == null){
            return;
        }
        for(Object key : uuidSection.getKeys()){
            String uuid = key.toString().split("/")[0];
            String name = key.toString().split("/")[1];
            List<String> messages = uuidSection.getStringList(key.toString());
            ChatPlayer chatPlayer = new ChatPlayer(UUID.fromString(uuid),name, messages);
            PlayerManager.addChatPlayer(chatPlayer);
        }
    }

    @Override
    public void onSave() {
        List<ChatPlayer> chatPlayers = PlayerManager.getChatPlayerList();
        for(ChatPlayer chatPlayer : chatPlayers){
            UUID uuid = chatPlayer.getUuid();
            String name = chatPlayer.getName();
            List<String> messages = chatPlayer.getMessagesList();
            getConfig().set(uuid.toString()+"/"+name, messages);
        }
    }
}
