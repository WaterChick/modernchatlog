package cz.waterchick.chatlogbungee.commands;

import cz.waterchick.ChatPlayer;
import cz.waterchick.Core;
import cz.waterchick.configs.MainConfig;
import cz.waterchick.enums.ConfigValue;
import cz.waterchick.managers.PlayerManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Command extends net.md_5.bungee.api.plugin.Command implements TabExecutor {

    private final Core core;
    private final ProxyServer proxyServer;
    public Command(Core core, ProxyServer proxyServer) {
        super("chatlog");
        this.core = core;
        this.proxyServer = proxyServer;
    }

    @Override
    public void execute(CommandSender source, String[] args) {

        String prefix = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.PREFIX.getValue());
        String noPermission = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.NOPERMISSION.getValue());
        String configReloaded = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.CONFIGRELOADED.getValue());
        String invalidPlayer = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.INVALIDPLAYER.getValue());
        String printHeader = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.PRINTHEADER.getValue());
        String printValue = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.PRINTVALUE.getValue());
        String notANumber = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.NOTANUMBER.getValue());
        String help = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.HELP.getValue());
        String playerHasNoMessages = cz.waterchick.chatlogbungee.utilities.Utils.color(ConfigValue.PLAYERHASNOMESSAGES.getValue());
        int limit = 10;

        switch (args.length){
            case 1:
                switch (args[0].toLowerCase()){
                    case "reload":
                        if(!source.hasPermission("chatlog.reload")){
                            source.sendMessage(new TextComponent(prefix + noPermission));
                            return;
                        }
                        MainConfig mainConfig = core.getMainConfig();
                        mainConfig.reloadConfig();
                        source.sendMessage(new TextComponent(prefix + configReloaded));
                        return;
                    default:
                        String playerName = args[0];
                        if(!source.hasPermission("chatlog.log")){
                            source.sendMessage(new TextComponent(prefix + noPermission));
                            return;
                        }

                        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(playerName);
                        if(optionalChatPlayer.isEmpty()){
                            source.sendMessage(new TextComponent(prefix + invalidPlayer));
                            return;
                        }
                        ChatPlayer chatPlayer = optionalChatPlayer.get();

                        String name = chatPlayer.getName();
                        List<String> messagesList = chatPlayer.getMessagesList();
                        if(messagesList.isEmpty()){
                            source.sendMessage(new TextComponent(prefix + playerHasNoMessages));
                            return;
                        }
                        String plainHeader = printHeader.replace("%player%", name).replace("%limit%", String.valueOf(limit));
                        source.sendMessage(new TextComponent(prefix + plainHeader));

                        for(int i = 0; i < limit; i ++){
                            try {
                                String message = messagesList.get(i);
                                String plainValue = printValue.replace("%message%",message);
                                source.sendMessage(new TextComponent(plainValue));
                            }catch (IndexOutOfBoundsException e){
                                break;
                            }
                        }
                        return;
                }
            case 2:
                String playerName = args[0];
                String limitString = args[1];
                if(!cz.waterchick.utilities.Utils.isNumber(limitString)){
                    source.sendMessage(new TextComponent(prefix + notANumber));
                    return;
                }
                limit = Integer.parseInt(limitString);
                if(!source.hasPermission("chatlog.log")){
                    source.sendMessage(new TextComponent(prefix + noPermission));
                    return;
                }
                Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(playerName);
                if(optionalChatPlayer.isEmpty()){
                    source.sendMessage(new TextComponent(prefix + invalidPlayer));
                    return;
                }
                ChatPlayer chatPlayer = optionalChatPlayer.get();

                String name = chatPlayer.getName();
                List<String> messagesList = chatPlayer.getMessagesList();
                if(messagesList.isEmpty()){
                    source.sendMessage(new TextComponent(prefix + playerHasNoMessages));
                    return;
                }
                String plainHeader = printHeader.replace("%player%", name).replace("%limit%", String.valueOf(limit));
                source.sendMessage(new TextComponent(prefix + plainHeader));
                for(int i = 0; i < limit; i ++){
                    try {
                        String message = messagesList.get(i);
                        String plainValue = printValue.replace("%message%",message);
                        source.sendMessage(new TextComponent(plainValue));
                    }catch (IndexOutOfBoundsException e){
                        break;
                    }
                }
                return;
        }
        source.sendMessage(new TextComponent(prefix + help));

    }

    private static List<String> ARGUMENTS = List.of("reload");
    private static List<String> NUMBERS = List.of("1","5","15");

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
        List<String> result = new ArrayList<>();
        switch (args.length){
            case 1: {
                List<String> arguments = new ArrayList<>(ARGUMENTS);
                for (ProxiedPlayer player : proxyServer.getPlayers()) {
                    arguments.add(player.getName());
                }
                for (String s : arguments) {
                    if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                        result.add(s);
                    }
                }
                break;
            }
            case 2: {
                if(args[0].equalsIgnoreCase("reload")) break;
                List<String> arguments = new ArrayList<>(NUMBERS);
                for (String s : arguments) {
                    if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                        result.add(s);
                    }
                }
                break;
            }
        }

        return result;
    }
}
