package me.fromgate.playeffect.common;

import java.text.DecimalFormat;
import java.util.*;

public enum Message {

    //Default (lang) messages
    LNG_LOAD_FAIL("Failed to load languages from file. Default message used"),
    LNG_SAVE_FAIL("Failed to save lang file"),
    LNG_PRINT_FAIL("Failed to print message %1%. Sender object is null."),
    LNG_CONFIG("[MESSAGES] Messages: %1% Language: %2% Save translate file: %1% Debug mode: %3%"),
    WORD_UNKNOWN("Unknown"),
    WRONG_PERMISSION("You have not enough permissions to execute this command"),
    PERMISSION_FAIL("You have not enough permissions to execute this command", 'c'),
    PLAYER_COMMAD_ONLY("You can use this command in-game only!", 'c'),
    CMD_REGISTERED("Command registered: %1%"),
    CMD_FAILED("Failed to execute command. Type %1% to get help!"),
    HLP_TITLE("%1% | Help"),

    MSG_OUTDATED("%1% is outdated!"),
    MSG_PLEASEDOWNLOAD("Please download new version (%1%) from "),
    HLP_HELP("Help"),
    HLP_THISHELP("%1% - this help"),
    HLP_EXECCMD("%1% - execute command"),
    HLP_TYPECMD("Type %1% - to get additional help"),
    HLP_TYPECMDPAGE("Type %1% - to see another page of this help"),
    HLP_COMMANDS("Command list:"),
    HLP_CMDPARAM_COMMAND("command"),
    HLP_CMDPARAM_PAGE("page"),
    HLP_CMDPARAM_PARAMETER("parameter"),
    CMD_PLAY("%1% - play effect"),

    CMD_UNKNOWN("Unknown command: %1%"),
    CMD_CMDPERMERR("Something wrong (check command, permissions)"),
    ENABLED("enabled"),
    DISABLED("disabled"),

    LST_TITLE("String list:"),
    LST_FOOTER("Page: [%1% / %2%]"),
    LST_LISTISEMPTY("List is empty"),
    MSG_CONFIG("Configuration"),
    CFGMSG_GENERAL_CHECK_UPDATES("Check updates: %1%"),
    CFGMSG_GENERAL_LANGUAGE("Language: %1%"),
    CFGMSG_GENERAL_LANGUAGE_SAVE("Save translation file: %1%"),
    HLP_LIST("%1% - list of placed effects"),
    HLP_SET("%1% - setup effect"),
    HLP_POTION("%1% - play Potion break effect"),
    HLP_ENDER_SIGNAL("%1% - play Ender Signal effect"),
    HLP_ENDER_EYE("%1% - play Ender eye effect"),
    HLP_PORTAL("%1% - play Portal effect"),

    HLP_LIGHTNING("%1% - play Lightning effect"),

    HLP_EXPLOSION("%1% - play Explosion effect"),
    HLP_EXPLOSION_NORMAL("%1% - play explode effect"),
    HLP_EXPLOSION_HUGE("%1% - play Huge explosion effect"),
    HLP_EXPLOSION_LARGE("%1% - play Large explode effect"),

    HLP_FIREWORKS_EXPLODE("%1% - play Fireworks explosion effect"),
    HLP_FIREWORKS_SPARK("%1% - play Fireworks spark effect"),

    HLP_WATER_BUBBLE("%1% - play Water bubble effect (works only underwater)"),
    HLP_WATER_SPLASH("%1% - play Water splash effect"),
    HLP_WATER_WAKE("%1% - play Water wake effect"),
    HLP_WATER_DROP("%1% - play Water drop effect"),

    HLP_SUSPENDED("%1% - play Suspend effect (works only underwater)"),
    HLP_SUSPENDED_DEPTH("%1% - play Depth suspend effect"),

    HLP_CRIT("%1% - play Crit effect"),
    HLP_CRIT_MAGIC("%1% - play Magic crit effect"),


    HLP_SMOKE("%1% - play Smoke effect (you can define direction)"),
    HLP_SMOKE_NORMAL("%1% - play Normal smoke effect"),
    HLP_SMOKE_LARGE("%1% - play Large smoke effect"),


    HLP_SPELL("%1% - play Spell effect"),
    HLP_SPELL_INSTANT("%1% - play Instant spell effect"),
    HLP_SPELL_MOB("%1% - play Mob spell effect"),
    HLP_SPELL_MOB_AMBIENT("%1% - play Mob spell (ambient) effect"),
    HLP_SPELL_WITCH("%1% - play Witch magic effect"),

    HLP_DRIP_WATER("%1% - play Drip water effect"),
    HLP_DRIP_LAVA("%1% - play Drip lava effect"),

    HLP_VILLAGER_ANGRY("%1% - play Angry villager effect"),
    HLP_VILLAGER_HAPPY("%1% - play Happy villager effect"),

    HLP_FLAME("%1% - play Flame (new) effect"),
    HLP_FLAME_SPAWNER("%1% - play Flame effect"),

    HLP_ITEM_CRACK("%1% - play Icon crack effect"),
    HLP_BLOCK_CRACK("%1% - play Block crack effect"),
    HLP_BLOCK_CRACK_SOUND("%1% - play Block crack effect with break sound"),
    HLP_BLOCK_DUST("%1% - play Block dust effect"),

    HLP_TOWN_AURA("%1% - play Town aura effect"),

    HLP_NOTE("%1% - play Note effect"),
    HLP_ENCHANTMENT_TABLE("%1% - play Runes (magic book) effect"),
    HLP_LAVA("%1% - play Lava effect"),
    HLP_FOOTSTEP("%1% - play Footstep effect"),
    HLP_CLOUD("%1% - play Smoke effect"),
    HLP_REDSTONE("%1% - play Redstone dust effect"),
    HLP_SNOWBALL("%1% - play Snowball effect"),
    HLP_SNOW_SHOVEL("%1% - play Snow showel effect"),
    HLP_SLIME("%1% - play Slime effect"),
    HLP_HEART("%1% - play Heart effect"),
    HLP_BARRIER("%1% - play Barrier effect"),

    HLP_ITEM_TAKE("%1% - play Item take effect (I don''know what is it)"),
    HLP_MOB_APPEARANCE("%1% - play Mob appearance effect"),

    HLP_SOUND("%1% - play Sound effect"),
    HLP_SONG("%1% - play Song effect"),
    MSG_EFFECTSET("Effect %1% was succesfully set"),
    MSG_EFFECTONOTSET("Failed to set effect %1%"),
    MSG_UNKNOWNEFFECT("Unknown effect type %1%"),
    HLP_INFO("%1% - show info about defined effect"),
    HLP_REMOVE("%1% - remove static effect"),
    HLP_WAND("%1% - to enable/disable wand mode"),
    MSG_WANDENABLED("Wand mode enabled"),
    MSG_SETEFFECT("Effect set %1%"),
    WAND_SET_FAIL("Failed to set effect"),
    MSG_REMOVED("Effect %1% removed"),
    MSG_EFFLIST("Effect list"),
    MSG_EFFLISTEMPTY("Effect list is empty"),
    HLP_SHOW("%1% - show effect(s)"),
    HLP_HIDE("%1% - hide effect(s)"),
    HLP_RESART("%1% - restart static effects"),
    HLP_RELOAD("%1% - reload configuration"),
    HLP_CHECK("%1% - find effect around you"),
    MSG_RESTARTED("All effects restarted!"),
    MSG_RELOADED("All effects reloaded and restarted!"),
    MSG_REMOVEFAILED("Failed to remove effect %1%"),
    MSG_CONSOLENEEDCOORD("You must define effect location when executing command play <effect> by console"),
    MSG_WRONGEFFECT("Failed parse effect name %1%"),
    MSG_HIDEEFFECT("Effect %1% is hidden"),
    MSG_SHOWEFFECT("Effect %1% is shown"),
    MSG_EFFECTINFO("Effects:");


    private static Messenger messenger;

    private static boolean debugMode = false;
    private static String language = "default";
    private static boolean saveLanguage = false;
    private static char c1 = 'a';
    private static char c2 = '2';
    private static String pluginName;


    public static String colorize(String text) {
        return messenger.colorize(text);
    }


    /**
     * This is my favorite debug routine :) I use it everywhere to print out variable values
     *
     * @param s - array of any object that you need to print out.
     *          Example:
     *          Message.BC ("variable 1:",var1,"variable 2:",var2)
     */
    public static void BC(Object... s) {
        if (!debugMode) return;
        if (s.length == 0) return;
        StringBuilder sb = new StringBuilder("&3[").append(pluginName).append("]&f ");
        for (Object str : s)
            sb.append(str.toString()).append(" ");

        messenger.broadcast(colorize(sb.toString().trim()));
    }

    /**
     * Send current message to log files
     *
     * @param s
     * @return — always returns true.
     * Examples:
     * Message.ERROR_MESSAGE.log(variable1); // just print in log
     * return Message.ERROR_MESSAGE.log(variable1); // print in log and return value true
     */
    public boolean log(Object... s) {
        messenger.log(getText(s));
        return true;
    }

    /**
     * Same as log, but will printout nothing if debug mode is disabled
     *
     * @param s
     * @return — always returns true.
     */
    public boolean debug(Object... s) {
        if (debugMode)
            log(messenger.clean(getText(s)));
        return true;
    }

    /**
     * Show a message to player in center of screen (this routine unfinished yet)
     *
     * @param seconds — how much time (in seconds) to show message
     * @param sender  — Player
     * @param s
     * @return — always returns true.
     */
    public boolean tip(int seconds, Object sender, Object... s) {
        return messenger.tip(seconds, sender, getText(s));
    }
    /*
    public boolean tip(int seconds, CommandSender sender, Object... s) {
        if (sender == null) return Message.LNG_PRINT_FAIL.log(this.name());
        final Player player = sender instanceof Player ? (Player) sender : null;
        final String message = getText(s);
        if (player == null) sender.sendMessage(message);
        else for (int i = 0; i < seconds; i++)
            Server.getInstance().getScheduler().scheduleDelayedTask(new Runnable() {
                public void run() {
                    if (player.isOnline()) player.sendTip(message);
                }
            }, 20 * i);
        return true;
    } */

    /**
     * Show a message to player in center of screen
     *
     * @param sender — Player
     * @param s
     * @return — always returns true.
     */
    public boolean tip(Object sender, Object... s) {
        return messenger.tip(sender, getText(s));
    }

    /**
     * Send message to Player or to ConsoleSender
     *
     * @param sender
     * @param s
     * @return — always returns true.
     */
    public boolean print(Object sender, Object... s) {
        if (sender == null) return Message.LNG_PRINT_FAIL.log(this.name());
        return messenger.print(sender, getText(s));
    }

    /**
     * Send message to all players or to players with defined permission
     *
     * @param permission
     * @param s
     * @return — always returns true.
     * <p>
     * Examples:
     * Message.MSG_BROADCAST.broadcast ("pluginname.broadcast"); // send message to all players with permission "pluginname.broadcast"
     * Message.MSG_BROADCAST.broadcast (null); // send message to all players
     */
    public boolean broadcast(String permission, Object... s) {
        return messenger.broadcast(permission, getText(s));
    }


    /**
     * Get formated text.
     *
     * @param keys * Keys - are parameters for message and control-codes.
     *             Parameters will be shown in position in original message according for position.
     *             This keys are used in every method that prints or sends message.
     *             <p>
     *             Example:
     *             <p>
     *             EXAMPLE_MESSAGE ("Message with parameters: %1%, %2% and %3%");
     *             Message.EXAMPLE_MESSAGE.getText("one","two","three"); //will return text "Message with parameters: one, two and three"
     *             <p>
     *             * Color codes
     *             You can use two colors to define color of message, just use character symbol related for color.
     *             <p>
     *             Message.EXAMPLE_MESSAGE.getText("one","two","three",'c','4');  // this message will be red, but word one, two, three - dark red
     *             <p>
     *             * Control codes
     *             Control codes are text parameteres, that will be ignored and don't shown as ordinary parameter
     *             - "SKIPCOLOR" - use this to disable colorizing of parameters
     *             - "NOCOLOR" (or "NOCOLORS") - return uncolored text, clear all colors in text
     *             - "FULLFLOAT" - show full float number, by default it limit by two symbols after point (0.15 instead of 0.1483294829)
     * @return
     */
    public String getText(Object... keys) {
        char[] colors = new char[]{color1 == null ? c1 : color1, color2 == null ? c2 : color2};
        if (keys.length == 0) return colorize("&" + colors[0] + this.message);
        String str = this.message;
        boolean noColors = false;
        boolean skipDefaultColors = false;
        boolean fullFloat = false;
        String prefix = "";
        int count = 1;
        int c = 0;
        DecimalFormat fmt = new DecimalFormat("####0.##");
        for (int i = 0; i < keys.length; i++) {
            String s = messenger.toString(keys[i], fullFloat);//keys[i].toString();
            if (c < 2 && keys[i] instanceof Character) {
                colors[c] = (Character) keys[i];
                c++;
                continue;
            } else if (s.startsWith("prefix:")) {
                prefix = s.replace("prefix:", "");
                continue;
            } else if (s.equals("SKIPCOLOR")) {
                skipDefaultColors = true;
                continue;
            } else if (s.equals("NOCOLORS") || s.equals("NOCOLOR")) {
                noColors = true;
                continue;
            } else if (s.equals("FULLFLOAT")) {
                fullFloat = true;
                continue;
            } else if (keys[i] instanceof Double || keys[i] instanceof Float) {
                if (!fullFloat) s = fmt.format((Double) keys[i]);
            }

            String from = (new StringBuilder("%").append(count).append("%")).toString();
            String to = skipDefaultColors ? s : (new StringBuilder("&").append(colors[1]).append(s).append("&").append(colors[0])).toString();
            str = str.replace(from, to);
            count++;
        }
        str = colorize(prefix.isEmpty() ? "&" + colors[0] + str : prefix + " " + "&" + colors[0] + str);
        if (noColors) str = clean(str);
        return str;
    }

    public static String clean(String str) {
        return messenger.clean(str);
    }

    private void initMessage(String message) {
        this.message = message;
    }

    private String message;
    private Character color1;
    private Character color2;

    Message(String msg) {
        message = msg;
        this.color1 = null;
        this.color2 = null;
    }

    Message(String msg, char color1, char color2) {
        this.message = msg;
        this.color1 = color1;
        this.color2 = color2;
    }

    Message(String msg, char color) {
        this(msg, color, color);
    }

    @Override
    public String toString() {
        return this.getText("NOCOLOR");
    }

    /**
     * Initialize current class, load messages, etc.
     * Call this file in onEnable method after initializing plugin configuration
     */
    public static void init(String pluginName, Messenger mess, String lang, boolean debug, boolean save) {
        messenger = mess;
        language = lang;
        debugMode = debug;
        saveLanguage = save;
        initMessages();
        if (saveLanguage) saveMessages();
        LNG_CONFIG.debug(Message.values().length, language, true, debugMode);
    }

    /**
     * Enable debugMode
     *
     * @param debug
     */
    public static void setDebugMode(boolean debug) {
        debugMode = debug;
    }

    public static boolean isDebug() {
        return debugMode;
    }


    private static void initMessages() {
        Map<String, String> lng = messenger.load(language);
        for (Message key : Message.values()) {
            if (lng.containsKey(key.name().toLowerCase())) {
                key.initMessage(lng.get(key.name().toLowerCase()));
            }
        }
    }

    private static void saveMessages() {
        Map<String, String> messages = new LinkedHashMap<String, String>();
        for (Message msg : Message.values()) {
            messages.put(msg.name().toLowerCase(), msg.message);
        }
        messenger.save(language, messages);
    }

    /**
     * Send message (formed using join method) to server log if debug mode is enabled
     *
     * @param s
     */
    public static boolean debugMessage(Object... s) {
        if (debugMode) messenger.log(clean(join(s)));
        return true;
    }

    /**
     * Join object array to string (separated by space)
     *
     * @param s
     */
    public static String join(Object... s) {
        StringBuilder sb = new StringBuilder();
        for (Object o : s) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(messenger.toString(o, false));
        }
        return sb.toString();
    }

    public static void printLines(Object sender, Collection<String> lines) {
        for (String l : lines) {
            messenger.print(sender, colorize(l));
        }
    }

    public static void printPage(Object sender, List<String> lines, Message title, int pageNum, int linesPerPage) {
        printPage(sender, lines, title, null, pageNum, linesPerPage);
    }

    public static void printPage(Object sender, List<String> lines, Message title, Message footer, int pageNum, int linesPerPage) {
        if (lines == null || lines.isEmpty()) return;
        List<String> page = new ArrayList<String>();
        if (title != null) page.add(title.message);

        int pageCount = lines.size() / linesPerPage + 1;
        if (pageCount * linesPerPage == lines.size()) pageCount = pageCount - 1;

        int num = pageCount <= pageNum ? pageNum : 1;

        for (int i = linesPerPage * (num - 1); i < Math.min(lines.size(), num * linesPerPage); i++) {
            page.add(lines.get(i));
        }
        if (footer != null) page.add(num, footer.getText(pageCount));
        printLines(sender, page);
    }

    public static boolean logMessage(Object... s) {
        if (debugMode) messenger.log(clean(join(s)));
        return true;
    }


    public static Message getByName(String name) {
        for (Message m : values()){
            if (m.name().equalsIgnoreCase(name)) return m;
        }
        return null;
    }
}
