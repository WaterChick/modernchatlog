package cz.waterchick.chatlogbukkit.commands;

import cz.waterchick.ChatPlayer;
import cz.waterchick.Core;
import cz.waterchick.configs.MainConfig;
import cz.waterchick.enums.ConfigValue;
import cz.waterchick.managers.PlayerManager;
import cz.waterchick.utilities.Utils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;


public class Command implements CommandExecutor {

    private final Core core;

    public Command(Core core){
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender source, org.bukkit.command.Command command, String s, String[] args) {
        String prefix = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.PREFIX.getValue());
        String noPermission = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.NOPERMISSION.getValue());
        String configReloaded = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.CONFIGRELOADED.getValue());
        String invalidPlayer = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.INVALIDPLAYER.getValue());
        String printHeader = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.PRINTHEADER.getValue());
        String printValue = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.PRINTVALUE.getValue());
        String notANumber = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.NOTANUMBER.getValue());
        String help = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.HELP.getValue());
        String playerHasNoMessages = cz.waterchick.chatlogbukkit.utilities.Utils.color(ConfigValue.PLAYERHASNOMESSAGES.getValue());
        int limit = 10;

        switch (args.length){
            case 1:
                switch (args[0].toLowerCase()){
                    case "reload":
                        if(!source.hasPermission("chatlog.reload")){
                            source.sendMessage(prefix + noPermission);
                            return false;
                        }
                        MainConfig mainConfig = core.getMainConfig();
                        mainConfig.reloadConfig();
                        source.sendMessage(prefix + configReloaded);
                        return true;
                    default:
                        String playerName = args[0];
                        if(!source.hasPermission("chatlog.log")){
                            source.sendMessage(prefix + noPermission);
                            return false;
                        }

                        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(playerName);
                        if(optionalChatPlayer.isEmpty()){
                            source.sendMessage(prefix + invalidPlayer);
                            return false;
                        }
                        ChatPlayer chatPlayer = optionalChatPlayer.get();

                        String name = chatPlayer.getName();
                        List<String> messagesList = chatPlayer.getMessagesList();
                        if(messagesList.isEmpty()){
                            source.sendMessage(prefix + playerHasNoMessages);
                            return false;
                        }
                        String plainHeader = printHeader.replace("%player%", name).replace("%limit%", String.valueOf(limit));
                        source.sendMessage(prefix + plainHeader);

                        for(int i = 0; i < limit; i ++){
                            try {
                                String message = messagesList.get(i);
                                String plainValue = printValue.replace("%message%",message);
                                source.sendMessage(plainValue);
                            }catch (IndexOutOfBoundsException e){
                                break;
                            }
                        }
                        return true;
                }
            case 2:
                String playerName = args[0];
                String limitString = args[1];
                if(!cz.waterchick.utilities.Utils.isNumber(limitString)){
                    source.sendMessage(prefix + notANumber);
                    return false;
                }
                limit = Integer.parseInt(limitString);
                if(!source.hasPermission("chatlog.log")){
                    source.sendMessage(prefix + noPermission);
                    return false;
                }
                Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(playerName);
                if(optionalChatPlayer.isEmpty()){
                    source.sendMessage(prefix + invalidPlayer);
                    return false;
                }
                ChatPlayer chatPlayer = optionalChatPlayer.get();

                String name = chatPlayer.getName();
                List<String> messagesList = chatPlayer.getMessagesList();
                if(messagesList.isEmpty()){
                    source.sendMessage(prefix + playerHasNoMessages);
                    return false;
                }
                String plainHeader = printHeader.replace("%player%", name).replace("%limit%", String.valueOf(limit));
                source.sendMessage(prefix + plainHeader);
                for(int i = 0; i < limit; i ++){
                    try {
                        String message = messagesList.get(i);
                        String plainValue = printValue.replace("%message%",message);
                        source.sendMessage(plainValue);
                    }catch (IndexOutOfBoundsException e){
                        break;
                    }
                }
                return true;
        }
        source.sendMessage(prefix + help);

        return false;
    }
}
