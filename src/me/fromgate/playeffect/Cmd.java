package me.fromgate.playeffect;

import java.util.List;
import java.util.Map;
import me.fromgate.playeffect.effect.BasicEffect;
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
            case 1: return executeCmd(sender,args[0]);
            case 2: return executeCmd(sender,args[0],args[1]);
            case 3: return executeCmd(sender,args[0],args[1],args[2]);
            case 4: return executeCmd(sender,args[0],args[1],args[2],args[3]);
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
        else if (cmd.equalsIgnoreCase("help")){
            int page = 1;
            if (plg.u.isIntegerGZ(arg)) page = Integer.parseInt(arg);
            plg.u.PrintHlpList(sender, page, 15);
        }else if (cmd.equalsIgnoreCase("list")){
            int page = 1;
            String id = "";
            if (plg.u.isIntegerGZ(arg)) page = Integer.parseInt(arg);
            else id = arg;
            Effects.printEffectsList(sender, page,id);
        } else if(cmd.equalsIgnoreCase("info")){
            Effects.printEffectsInfo(sender, arg);
        } else if(cmd.equalsIgnoreCase("remove")){
            if (plg.u.isIntegerGZ(arg)){
                if (Effects.removeStaticEffect(Integer.parseInt(arg)-1)) plg.u.printMSG(sender,"msg_effectremove",arg);
                else plg.u.printMSG(sender,"msg_effectremovefail",arg);
            } else plg.u.printMSG(sender,"msg_unknowneffect",arg);
        } else if(cmd.equalsIgnoreCase("show")){
            if (Effects.setEnabled(arg, true)) plg.u.printMSG(sender,"msg_showeffect",arg);
            else plg.u.printMSG(sender,"msg_unknown",arg);
        } else if(cmd.equalsIgnoreCase("hide")){
            if (Effects.setEnabled(arg, false)) plg.u.printMSG(sender,"msg_hideeffect",arg);
            else plg.u.printMSG(sender,"msg_unknown",arg);
        } else if(cmd.equalsIgnoreCase("wand")){
            return setWandMode(sender, arg, "");
        } else return false;
        return true;
    }

    private boolean executeCmd(CommandSender sender, String cmd, String arg1, String arg2) {
        if (playEffect(sender,cmd,arg1+" "+arg2)) return true;
        else if(cmd.equalsIgnoreCase("set")){
            if (VisualEffect.contains(arg1)){
                arg2 = processLocation(sender, arg2);
                if (setEffect (sender,arg1,arg2)) plg.u.printMSG(sender, "msg_effectset",arg1);
                else plg.u.printMSG(sender, "msg_effectnotset",arg1);
            } else plg.u.printMSG(sender, "msg_unknowneffect",arg1);
        } else if (cmd.equalsIgnoreCase("list")){
            int page = 1;
            String id="";
            if (plg.u.isIntegerGZ(arg1)) {
                page = Integer.parseInt(arg1);
                id = arg2;
            } else {
                id = arg1;
                if (plg.u.isIntegerGZ(arg2)) page = Integer.parseInt(arg2);
            }
            Effects.printEffectsList(sender, page,id);
        }else if(cmd.equalsIgnoreCase("wand")){
            return setWandMode(sender, arg1, arg2);
        } else return false;
        return true;
    }
    //                                                        
    private boolean executeCmd(CommandSender sender, String cmd, String arg1, String arg2, String arg3) {
        if (playEffect(sender,cmd,arg1+" "+arg2+" "+arg3)) return true;
        else if(cmd.equalsIgnoreCase("set")){
            return executeCmd(sender, "set", arg1, arg2+" "+arg3);
        }else if(cmd.equalsIgnoreCase("wand")){
            return setWandMode(sender, arg1, arg2+" "+arg3);
        } else return false;
    }

    // play set <effect> time:10
    private boolean setEffect(CommandSender sender, String effect, String param) {
        if (!VisualEffect.contains(effect)) return false;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());
        if (ve == VisualEffect.BASIC) return false;
        Map<String,String> params = Effects.parseParams(param);
        String id = Effects.getId(Effects.getParam(params, "id", ""));
        String time = Effects.getParam(params, "time", Long.toString(ve.getRepeatTicks())+"t"); 
        BasicEffect be = Effects.createEffect(ve, params);
        Effects.createStaticEffect(be, id,time, true);
        return true;
    }



    // play set <effect> time:10
    private boolean setWandMode (CommandSender sender, String effect, String param) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (!VisualEffect.contains(effect)) return false;
        Wand.setWand(p, effect, param);
        plg.u.printMSG(p, "msg_wandenabled",effect,param);
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
            Effects.printEffectsList(sender, 1,"");
        }else if (cmd.equalsIgnoreCase("wand")){
            if (!(sender instanceof Player)) return false;
            Player p = (Player) sender;
            if (Wand.hasWand(p)) {
                Wand.clearWand(p);
                plg.u.printMSG(p, "msg_wanddisabled");
            }
        }else if (cmd.equalsIgnoreCase("restart")){
            Effects.stopAllEffects();
            EffectQueue.clearQueue();
            Effects.startAllEffects();
            plg.u.printMSG(sender, "msg_restarted");
        }else if (cmd.equalsIgnoreCase("reload")){
            Effects.stopAllEffects();
            EffectQueue.clearQueue();
            plg.loadCfg();
            Effects.reloadEffects();
            Effects.startAllEffects();
            plg.u.printMSG(sender, "msg_reloaded");
        } else if (cmd.equalsIgnoreCase("help")){
            plg.u.PrintHlpList(sender, 1, 15);
        } else return false;
        return true;
    }

    @SuppressWarnings("deprecation")
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
            if (WEGLib.isWE()){
                List<Location> locs = WEGLib.getSelectionLocations(p);
                if (locs.size()>0){
                    if (!arg.contains("loc:"))
                        arg = "loc:"+Util.locationToStrLoc(locs.get(0))+" "+arg;
                    if ((!arg.contains("loc2:"))&&(locs.size()==2))
                        arg = "loc2:"+Util.locationToStrLoc(locs.get(1))+" "+arg;
                }
            }
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
