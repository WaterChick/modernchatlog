package cz.waterchick.chatlogbukkit.TabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandTabComplete implements TabCompleter {

    private static List<String> ARGUMENTS = Collections.singletonList("reload");
    private static List<String> NUMBERS = Arrays.asList("1","5","15");

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();
        switch (args.length){
            case 1: {
                List<String> arguments = new ArrayList<>(ARGUMENTS);
                for (Player player : Bukkit.getOnlinePlayers()) {
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
