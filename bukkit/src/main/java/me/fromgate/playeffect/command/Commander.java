package me.fromgate.playeffect.command;

import me.fromgate.playeffect.VisualEffect;
import me.fromgate.playeffect.common.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.ChatPaginator;
import org.bukkit.util.ChatPaginator.ChatPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commander implements CommandExecutor {
    private static List<Cmd> commands = new ArrayList<>();
    private static JavaPlugin plugin;
    private static Commander commander;

    public static void init(JavaPlugin plg) {
        plugin = plg;
        commander = new Commander();
        addNewCommand(new Help());
        addNewCommand(new Play());
        addNewCommand(new Set());
        addNewCommand(new WandCmd());
        addNewCommand(new Check());
        addNewCommand(new Show());
        addNewCommand(new Hide());
        addNewCommand(new Remove());
        addNewCommand(new ListCmd());
        addNewCommand(new Info());
        addNewCommand(new Restart());
        addNewCommand(new Reload());
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static boolean addNewCommand(Cmd cmd) {
        if (cmd.getCommand() == null) return false;
        if (cmd.getCommand().isEmpty()) return false;
        plugin.getCommand(cmd.getCommand()).setExecutor(commander);
        commands.add(cmd);
        return true;
    }

	/*
    public static boolean isPluginYml(String cmdStr){
		return plugin.getDescription().getCommands().containsKey(cmdStr);
	} */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmdLabel, String[] args) {
        for (Cmd cmd : commands) {
            if (!cmd.getCommand().equalsIgnoreCase(command.getLabel())) continue;
            if (cmd.executeCommand(sender, args)) return true;
        }
        return false;
    }

    public static void printHelp(CommandSender sender, int page) {
        List<String> helpList = new ArrayList<>();
        Message.HLP_TITLE.print(sender, "PlayEffect",'6','6');
        for (Cmd cmd : commands) {
            helpList.add(cmd.getDescription());
        }
        for (VisualEffect v : VisualEffect.values()){
            if (v == VisualEffect.BASIC) continue;
            Message m = Message.getByName("HLP_"+v.name());
            if (m == null) {
                Message.logMessage("Help message not defined for effect:",v.name());
                continue;
            }
            helpList.add(m.getText("&3/playeffect "+v.name().toLowerCase()));
        }
        int pageHeight = (sender instanceof Player) ? 8 : 1000;
        ChatPage chatPage = paginate(helpList, page, 60, pageHeight);
        for (String str : chatPage.getLines()) {
            sender.sendMessage(str);
        }
        Message.LST_FOOTER.print(sender, chatPage.getPageNumber(), chatPage.getTotalPages());
    }

    public static ChatPage paginate(List<String> unpaginatedStrings, int pageNumber, int lineLength, int pageHeight) {
        List<String> lines = new ArrayList<>();
        for (String str : unpaginatedStrings) {
            lines.addAll(Arrays.asList(ChatPaginator.wordWrap(str, lineLength)));
        }
        int totalPages = lines.size() / pageHeight + (lines.size() % pageHeight == 0 ? 0 : 1);
        int actualPageNumber = pageNumber <= totalPages ? pageNumber : totalPages;
        int from = (actualPageNumber - 1) * pageHeight;
        int to = from + pageHeight <= lines.size() ? from + pageHeight : lines.size();
        String[] selectedLines = Arrays.copyOfRange(lines.toArray(new String[lines.size()]), from, to);
        return new ChatPage(selectedLines, actualPageNumber, totalPages);
    }

    public static String unsplit(String[] args) {
        return unsplit(args, 0);
    }

    public static String unsplit(String[] args, int num) {
        if (args.length <= num) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = num; i < args.length; i++) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(args[i]);
        }
        return sb.toString();
    }

    public static Player getPlayer(CommandSender sender) {
        return sender instanceof Player ? (Player) sender : null;
    }

    public static String[] getSubArgs(String[] args, int num) {
        if (args.length <= num) return new String[0];
        String[] params = new String[args.length - num];
        System.arraycopy(args, num, params, 0, args.length - num);
        return params;
    }
}
