/*  
 *  PlayEffect, Minecraft bukkit plugin
 *  (c)2013-2015, fromgate, fromgate@gmail.com
 *  http://dev.bukkit.org/bukkit-plugins/playeffect/
 *    
 *  This file is part of PlayEffect.
 *  
 *  PlayEffect is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PlayEffect is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PlayEffect.  If not, see <http://www.gnorg/licenses/>.
 * 
 */


package me.fromgate.playeffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.fromgate.playeffect.effect.BasicEffect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor{
    PlayEffectPlugin plg;

    public Cmd (PlayEffectPlugin plugin){
        this.plg = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if ((args.length>0)&&plg.u.checkCmdPerm(sender, args[0])){
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
                if (Effects.removeStaticEffect(Integer.parseInt(arg)-1)) plg.u.printMSG(sender,"msg_removed",arg);
                else plg.u.printMSG(sender,"msg_removefailed",arg);
            } else plg.u.printMSG(sender,"msg_unknowneffect",arg);
        } else if(cmd.equalsIgnoreCase("show")){
            if (Effects.setEnabled(arg, true)) plg.u.printMSG(sender,"msg_showeffect",arg);
            else plg.u.printMSG(sender,"msg_unknown",arg);
        } else if(cmd.equalsIgnoreCase("hide")){
            if (Effects.setEnabled(arg, false)) plg.u.printMSG(sender,"msg_hideeffect",arg);
            else plg.u.printMSG(sender,"msg_unknown",arg);
        } else if(cmd.equalsIgnoreCase("wand")){
            return setWandMode(sender, arg, "");
        } else if (cmd.equalsIgnoreCase("check")){
            if (!(sender instanceof Player)) return false;
            int radius = 8;
            if (plg.u.isIntegerGZ(arg)) radius = Integer.parseInt(arg);
            Effects.printAroundEffects((Player) sender, radius);
        } else plg.u.printMSG(sender, "msg_wrongeffect",cmd);//return false;
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
        } plg.u.printMSG(sender, "msg_wrongeffect",cmd); //else return false;
        return true;
    }
    //                                                        
    private boolean executeCmd(CommandSender sender, String cmd, String arg1, String arg2, String arg3) {
        if (playEffect(sender,cmd,arg1+" "+arg2+" "+arg3)) return true;
        else if(cmd.equalsIgnoreCase("set")){
            return executeCmd(sender, "set", arg1, arg2+" "+arg3);
        }else if(cmd.equalsIgnoreCase("wand")){
            return setWandMode(sender, arg1, arg2+" "+arg3);
        } else plg.u.printMSG(sender, "msg_wrongeffect",cmd); //return false;
        return true;
    }

    // play set <effect> time:10
    private boolean setEffect(CommandSender sender, String effect, String param) {
        VisualEffect ve = VisualEffect.getEffectByName(effect);
        if (ve == null) return false;
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
        Map<String,String> params = Effects.parseParams(arg);
        Player player = null;
        String paramPlayer = Effects.getParam(params, "player", "");
        if (!paramPlayer.isEmpty()) player = Bukkit.getPlayerExact(paramPlayer);
        if((player==null)&&(sender instanceof Player)) player = (Player) sender; 
        if ((player==null)&(!arg.contains("loc:"))) {
            plg.u.printMSG(sender, "msg_consoleneedcoord",cmd+" "+arg);
        }
        VisualEffect ve = VisualEffect.getEffectByName(cmd);
        if (ve == null) return false;
        params = processLocation(sender, params);
        Effects.playEffect(ve,  params);
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
        } else if (cmd.equalsIgnoreCase("check")){
            if (!(sender instanceof Player)) return false;
            Effects.printAroundEffects((Player) sender, 8);
        } else if (cmd.equalsIgnoreCase("help")){
            plg.u.PrintHlpList(sender, 1, 15);
        } else plg.u.printMSG(sender, "msg_wrongeffect",cmd);//return false;
        return true;
    }

    @SuppressWarnings("deprecation")
    private Location getTargetBlockFaceLocation (Player p){
        List<Block> blocks = p.getLineOfSight(null, 100);
        if (blocks.isEmpty()) return null;
        if (blocks.size()<=2) return blocks.get(0).getLocation();
        return blocks.get(blocks.size()-2).getLocation();
    }


    @Deprecated
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


    private Map<String,String> processLocation(CommandSender sender, Map<String,String> params){
        Map<String,String> newparams = new HashMap<String,String>();
        if (!params.containsKey("loc")) params.put("loc", "view");
        if (!params.containsKey("loc2")) params.put("loc2", "eye");
        
        if ((sender instanceof Player)) {
            Player p = (Player)sender;
            for (String key : params.keySet()){
                //newparams.put(key, params.get(key));
                String value = params.get(key);
                if (key.equalsIgnoreCase("loc")||key.equalsIgnoreCase("loc2")){
                    if (value.equalsIgnoreCase("wg1")||value.equalsIgnoreCase("wg2")){
                        if (WEGLib.isWE()){
                            List<Location> locs = WEGLib.getSelectionLocations(p);
                            if (locs.size()>0){
                                if(value.equalsIgnoreCase("wg1")) value = Util.locationToStrLoc(locs.get(0));
                                if(value.equalsIgnoreCase("wg2")) value = Util.locationToStrLoc(locs.get(1));    
                            }
                        }
                    } else if (value.equalsIgnoreCase("here")) value = Util.locationToStrLoc(p.getLocation());
                    else if (value.equalsIgnoreCase("view")) value = Util.locationToStrLoc(getTargetBlockFaceLocation (p));
                    else if (value.equalsIgnoreCase("eye")) value = Util.locationToStrLoc(p.getEyeLocation());
                }
                newparams.put(key, value);
            }
            return newparams;
        }
        return params;
    }
}
