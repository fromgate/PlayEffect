package me.fromgate.playeffect.command;

import me.fromgate.playeffect.common.Message;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CmdDefine {
    public String command();

    public String[] subCommands();

    public String permission();

    public boolean allowConsole() default false;

    public Message description();

    public String shortDescription();
}

