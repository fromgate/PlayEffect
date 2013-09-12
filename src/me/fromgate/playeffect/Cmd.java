package me.fromgate.playeffect;

import java.util.List;
import java.util.Map;

import me.fromgate.playeffect.effect.BasicEffect;
import me.fromgate.playeffect.effect.StaticEffect;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor{
    PlayEffect plg;
    
    public Cmd (PlayEffect plugin){
        this.plg = plugin;
    }
    
    
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if ((args.length>0)&&plg.u.checkCmdPerm(sender, args[0])){
            // play <effect>
            // play list
            // play wand
            // play check
            // play help

            // play list <page>
            // play check <radius>
            // play set <effect>
            // play wand <effect>
            // play <effect> <params>            


            // play <effect> <params>
            // play set <effect> <params>
            // play wand <effect> <params>
            switch (args.length){
            case 1:
                return executeCmd(sender,args[0]);
            case 2:
                return executeCmd(sender,args[0],args[1]);
            case 3:
                return executeCmd(sender,args[0],args[1],args[2]);
            case 4:
                return executeCmd(sender,args[0],args[1],args[2],args[3]);
            default:
                String lastarg = args[3];
                if (args.length>4)
                    for (int i=4;i<args.length; i++)
                        lastarg = lastarg+" "+args[i]; 
                return executeCmd(sender,args[0],args[1],args[2],lastarg);
            }
        } else plg.u.printMSG(sender, "cmd_cmdpermerr",'c');
        return true;
    }
    
    private boolean executeCmd(CommandSender sender, String cmd, String arg) {
        if (playEffect(sender,cmd,arg)) return true;
        return false;
    }
    
    private boolean executeCmd(CommandSender sender, String cmd, String arg1, String arg2) {
        if (playEffect(sender,cmd,arg1+" "+arg2)) return true;
        else if(cmd.equalsIgnoreCase("set")){
            if (VisualEffect.contains(arg1)){
                arg2 = processLocation(sender, arg2);
                if (setEffect (sender,arg1,arg2)) plg.u.printMSG(sender, "msg_effectset",arg1);
                else plg.u.printMSG(sender, "msg_effectnotset",arg1);
            } else plg.u.printMSG(sender, "msg_unknowneffect",arg1);
        } else return false;
        return true;
    }
    //                                                        
    private boolean executeCmd(CommandSender sender, String cmd, String arg1, String arg2, String arg3) {
        if (playEffect(sender,cmd,arg1+" "+arg2+" "+arg3)) return true;
        else if(cmd.equalsIgnoreCase("set")){
            return executeCmd(sender, arg1, arg2+" "+arg3);
        } else return false;
    }
    
    // play set <effect> time:10
    private boolean setEffect(CommandSender sender, String effect, String param) {
        if (!VisualEffect.contains(effect)) return false;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());
        if (ve == VisualEffect.BASIC) return false;
        // TODO!!!!
        String id = "test";
        Map<String,String> params = Effects.parseParams(param);
        String time = Effects.getParam(params, "time", Long.toString(ve.getRepeatTicks())+"t"); 
        BasicEffect be = Effects.createEffect(ve, params);
        Effects.addStaticEffect(be, id,time, true);
        return true;
    }
    
    private boolean playEffect(CommandSender sender, String cmd, String arg) {
        if (!VisualEffect.contains(cmd)) return false;

        VisualEffect ve = null;
        try{
            ve = VisualEffect.valueOf(cmd.toUpperCase());
        } catch (Exception e){
            return false;
        }
        if (ve == null) return false;
        if (ve == VisualEffect.BASIC) return false;
        arg = processLocation(sender, arg);
        Effects.playEffect(ve,  arg);
        return true;
    }

    private boolean executeCmd(CommandSender sender, String cmd) {
        // play <effect>
        // play list
        // play wand
        // play check
        // play help
        if (playEffect(sender,cmd,"view")) return true;
        else if (cmd.equalsIgnoreCase("list")){

        }else if (cmd.equalsIgnoreCase("test")){
            Player p = (Player)sender;
            BasicEffect e = Effects.createEffect(VisualEffect.FIREWORK, "radius:5 land:false loc:"+Util.locationToStrLoc(getTargetBlockFaceLocation (p)));
            StaticEffect se = new StaticEffect ("test",e,40L);
            se.startRepeater();
            
        }else if (cmd.equalsIgnoreCase("wand")){

        }else if (cmd.equalsIgnoreCase("check")){

        } else if (cmd.equalsIgnoreCase("help")){
            plg.u.PrintHlpList(sender, 1, 15);
        } else return false;
        return true;
    }
    
    private Location getTargetBlockFaceLocation (Player p){
        List<Block> blocks = p.getLineOfSight(null, 100);
        if (blocks.isEmpty()) return null;
        if (blocks.size()<=2) return blocks.get(0).getLocation();
        return blocks.get(blocks.size()-2).getLocation();
    }

    
    private String processLocation(CommandSender sender, String param){
        String arg = param;
        if ((sender instanceof Player)) {
            Player p = (Player)sender;
            if (arg.contains("loc:here")) arg = arg.replace("loc:here", "loc:"+Util.locationToStrLoc(p.getLocation()));
            if (arg.contains("loc:view")) arg = arg.replace("loc:view", "loc:"+Util.locationToStrLoc(getTargetBlockFaceLocation (p)));
            if (!arg.contains("loc:")) arg = "loc:"+Util.locationToStrLoc(getTargetBlockFaceLocation (p))+" "+arg;
            if (arg.contains("loc2:here")) arg = arg.replace("loc2:here", "loc2:"+Util.locationToStrLoc(p.getLocation()));
            if (arg.contains("loc2:view")) arg = arg.replace("loc2:view", "loc2:"+Util.locationToStrLoc(getTargetBlockFaceLocation (p)));
            if (!arg.contains("loc2:")) arg = "loc2:"+Util.locationToStrLoc(p.getLocation())+" "+arg;
        }
        return arg;
    }

}
