package cz.waterchick.chatlogvelocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import cz.waterchick.ChatPlayer;
import cz.waterchick.Core;
import cz.waterchick.chatlogvelocity.utilities.Utils;
import cz.waterchick.configs.MainConfig;
import cz.waterchick.enums.ConfigValue;
import cz.waterchick.managers.PlayerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Command implements SimpleCommand {

    private final Core core;
    private final ProxyServer proxyServer;
    private static List<String> ARGUMENTS = List.of("reload");
    private static List<String> NUMBERS = List.of("1","5","15");

    public Command(Core core, ProxyServer proxyServer){
        this.core = core;
        this.proxyServer = proxyServer;
    }
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        // Get the arguments after the command alias
        String[] args = invocation.arguments();
        Component prefix = Utils.color(ConfigValue.PREFIX.getValue());
        Component noPermission = Utils.color(ConfigValue.NOPERMISSION.getValue());
        Component configReloaded = Utils.color(ConfigValue.CONFIGRELOADED.getValue());
        Component invalidPlayer = Utils.color(ConfigValue.INVALIDPLAYER.getValue());
        Component printHeader = Utils.color(ConfigValue.PRINTHEADER.getValue());
        Component printValue = Utils.color(ConfigValue.PRINTVALUE.getValue());
        Component notANumber = Utils.color(ConfigValue.NOTANUMBER.getValue());
        Component help = Utils.color(ConfigValue.HELP.getValue());
        Component playerHasNoMessages = Utils.color(ConfigValue.PLAYERHASNOMESSAGES.getValue());

        int limit = 10;

        switch (args.length){
            case 1:
                switch (args[0].toLowerCase()){
                    case "reload":
                        if(!source.hasPermission("chatlog.reload")){
                            source.sendMessage(prefix.append(noPermission));
                            return;
                        }
                        MainConfig mainConfig = core.getMainConfig();
                        mainConfig.reloadConfig();
                        source.sendMessage(prefix.append(configReloaded));
                        return;
                    default:
                        String playerName = args[0];
                        if(!source.hasPermission("chatlog.log")){
                            source.sendMessage(prefix.append(noPermission));
                            return;
                        }

                        Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(playerName);
                        if(optionalChatPlayer.isEmpty()){
                            source.sendMessage(prefix.append(invalidPlayer));
                            return;
                        }
                        ChatPlayer chatPlayer = optionalChatPlayer.get();

                        String name = chatPlayer.getName();
                        List<String> messagesList = chatPlayer.getMessagesList();
                        if(messagesList.isEmpty()){
                            source.sendMessage(prefix.append(playerHasNoMessages));
                            return;
                        }
                        MiniMessage miniMessage = MiniMessage.miniMessage();
                        String plainHeader = miniMessage.serialize(printHeader).replace("%player%", name).replace("%limit%", String.valueOf(limit));
                        source.sendMessage(prefix.append(miniMessage.deserialize(plainHeader)));

                        for(int i = 0; i < limit; i ++){
                            try {
                                String message = messagesList.get(i);
                                String plainValue = miniMessage.serialize(printValue).replace("%message%", message);
                                Component componentValue = miniMessage.deserialize(plainValue);
                                source.sendMessage(componentValue);
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
                    source.sendMessage(prefix.append(notANumber));
                    return;
                }
                limit = Integer.parseInt(limitString);
                if(!source.hasPermission("chatlog.log")){
                    source.sendMessage(prefix.append(noPermission));
                    return;
                }
                Optional<ChatPlayer> optionalChatPlayer = PlayerManager.getPrestigePlayer(playerName);
                if(optionalChatPlayer.isEmpty()){
                    source.sendMessage(prefix.append(invalidPlayer));
                    return;
                }
                ChatPlayer chatPlayer = optionalChatPlayer.get();

                String name = chatPlayer.getName();
                List<String> messagesList = chatPlayer.getMessagesList();
                if(messagesList.isEmpty()){
                    source.sendMessage(prefix.append(playerHasNoMessages));
                    return;
                }
                MiniMessage miniMessage = MiniMessage.miniMessage();
                String plainHeader = miniMessage.serialize(printHeader).replace("%player%", name).replace("%limit%", String.valueOf(limit));
                source.sendMessage(prefix.append(miniMessage.deserialize(plainHeader)));
                for(int i = 0; i < limit; i ++){
                    try {
                        String message = messagesList.get(i);
                        String plainValue = miniMessage.serialize(printValue).replace("%message%", message);
                        Component componentValue = miniMessage.deserialize(plainValue);
                        source.sendMessage(componentValue);
                    }catch (IndexOutOfBoundsException e){
                        break;
                    }
                }
                return;
        }
        source.sendMessage(prefix.append(help));

    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        List<String> result = new ArrayList<>();
        switch (args.length){
            case 1: {
                List<String> arguments = new ArrayList<>(ARGUMENTS);
                for (Player player : proxyServer.getAllPlayers()) {
                    arguments.add(player.getUsername());
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

    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        return SimpleCommand.super.suggestAsync(invocation);
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return SimpleCommand.super.hasPermission(invocation);
    }
}
