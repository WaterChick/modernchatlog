package cz.waterchick.chatlogvelocity.utilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class Utils {



    public static Component color(String message){
        return LegacyComponentSerializer.legacy('&').deserialize(message);
    }
}
