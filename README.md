PlayEffect
==========

PlayEffect allows to spawn a visual (and sound) effects at Minecraft worlds. You can play single effect in defined location or create a "static" effect that repeatedly plays in defined area. This plugin is logical extension of my plugin [NoSmoking](http://dev.bukkit.org/bukkit-plugins/no-smoking/)! and created to replace it.

Features
--------
* 45 different types of effects
* Ability to play effect in defined area (location, line, cuboid, plain)
* Static effects repeatedly played in defined area
* Effect customization

How to use PlayEffect
---------------------

* /play <effect> <param1:value> <param2:value>... — play single effect
* /play set <effect> <param1:value> <param2:value>... — set static effect at your view poing
* /play wand <effect> <param1:value> <param2:value>... — link effect to a wand (default wand is the coal item)

Main effect parameters
----------------------
* id:<effect id> — id or name of effect, necessary to static effects only;
* draw:<normal, line, plain, circle, area> — render view of effect: normal — single location poing, line — line defined by two point (loc and loc2), circle — ring defined by center and radius, plain — rectangle, area — cuboid region;
* loc:<world,x,y,z> — main (first) location point
* loc2:<world,x,y,z> — second location point (used with draw types: plain, line, area)
* radius:<radius> — radius (used with draw types: normal, circle)
* chance:<%> — chance to play effect
* time:<time> — repeating time for static effects (time formatis fully compatible used in plugin ReActions)
* dur:<time> — duration time for single effect (could be combined with static)
* freq:<time> — repeating time if single effect (use only with duration (dur) parameter)
Every effect could have own parameters, that listed in effect definition.

Effects
-------

There are 45 different effect types. All of them listed here: [Effect list](http://dev.bukkit.org/bukkit-plugins/playeffect/pages/main/effects/)

PlayEffect API
--------------

PlayEffect contains an API that allows plugin developers easy access to develop plugins with effect features.

I created plugin PlayEffectRailgun as example of using API: [PlayEffectRailgun](http://dev.bukkit.org/bukkit-plugins/playeffectrailgun/) ([source code](http://github.com/fromgate/PlayEffectRailgun))

Plugins supported PlayEffect API:

* [Laser](http://dev.bukkit.org/bukkit-plugins/laser/): lasers and other beam weapon
* [ReActions](http://dev.bukkit.org/bukkit-plugins/reactions/): very simple custom event processing system — add new actions to buttons, plates, etc..
If you developer and your plugins is using PlayEffect API please inform me and I will add you plugin to this list.

Commands
--------

* /play help [page number] - Hmmm.... :)
* /play list [page] [effect id] — display list of static effects
* /play info [number of effect id] — display full information about effect
* /play remove [effect number] — remove effect
* /play set <effect> [param] — set the effect. If parameters loc and loc2 omitted player location and view point coordinates will used. If WorldGuard plugin is installed and you can use wand (wood axe) to select location point loc and loc2.
* /play wand <effect> [param] — line defined effect with wand (coal item)
* /play wand — disable wand mode
* /play show <effect id> — show effect(s)
* /play hide <effect id> — hide effect(s)
* /play restart — stop playing of all effects and start it again
* /play reload — reload configuration and effects from the file, restart effects

How to setup location points
----------------------------
You can define coordinates using:

* Parameters loc and loc2. You can use keyword "here" and "view" to define coordinates of player and coordinate of view point. Example: /play SMOKE loc:here, /play smoke loc:world,0,65,0 /play FIREWORK loc:here loc2:view draw:line
* WorldEdit tool. If WorlEdit is installed, after selecting a points using WE tool (wood axe - default) first (left-click) point will be used as loc parameter, second (right-click) will used as loc2.
* Default. If coordinates omitted and WordEdit was not used loc parameter will be equal to you view point loc2 to your position. Example: /play SMOKE is equal to /play SMOKE loc:view

Commands example
----------------

* /play SMOKE wind:north loc:here — plays effect with wind direction - north at player locations
* /play firework draw:line loc:here loc2:view type:burst — Oh! What a great laser effect!
* /play set driplava id:lavarain time:1s draw:plain loc:world,10,75,10 loc2:world,-10,75,-10 chance:10 — creates a lava rain
* /play set flamenew id:fireland time:5s draw:area land:true loc:world,10,50,10 loc2:world,-10,75,-10 chance:10 — creates a "flaming" land

Permissions
-----------

* playeffect.config - main for OPs
* playeffect.wand - ability to use wand command
* playeffect.set - ability to use /playeffect set command
* playeffect.play - ability to use /playeffect playe command
* playeffect.show - ability to use /playeffect show and /playeffect hide commands

How to upgrade from the NoSmoking! plugin
-----------------------------------------

PlayEffect supports NoSmoking v0.0.7 file format. To import NoSmoking! effects you just need to copy smokepoints.yml file to PlayEffect folder and restart server. NoSmoking! effects will be imported to PlayEffect and file will be renamed to smokepoints.yml.old.

Warning! Imported effects could differ from the NoSmoking! effects: explosion and ender pearl effects are changed to another, sound effect is totally new (I tried to use the same sounds) and there a difference between time representations used in the plugins.

If you need to import from earlier versions of NoSmoking! you need to use NoSmoking v0.0.7 first to get a compatible file format

Metrics and update checker
--------------------------
PlayEffect includes two features that use your server internet connection. First one is Metrics, using to collect [information about the plugin](http://mcstats.org/plugin/PlayEffect) (versions of plugin, of Java.. etc.) and second is update checker, checks new releases of plugin after PlayEffect startup and every half hour. This feature is using API provided by dev.bukkit.org. If you don't like this features you can easy disable it. To disable update checker you need to set parameter "version-check" to "false" in config.yml. Obtain more information about Metrics and learn how to switch off it, you can read [here](http://mcstats.org/learn-more/).