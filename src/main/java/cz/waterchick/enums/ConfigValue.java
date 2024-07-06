package cz.waterchick.enums;


public enum ConfigValue {
    PREFIX("&8[&2ChatLog&8] &r"),
    CONFIGRELOADED("&7Config reloaded"),
    PRINTHEADER("&fMessage records of player &a%player% &7[limit=%limit%]:"),
    PRINTVALUE(" &7- &f%message%"),

    INVALIDPLAYER("&cPlayer not found"),
    NOPERMISSION("&cNo permission!"),
    NOTANUMBER("&cInvalid limit value"),

    HELP("&cInvalid usage! &7Use: &f/chatlog <player | reload> [limit]"),
    PLAYERHASNOMESSAGES("&cPlayer has no messages sent!")
    ;

    private String value;

    ConfigValue(String value){
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
