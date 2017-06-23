package me.fromgate.playeffect.command;

import me.fromgate.playeffect.common.Message;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CmdDefine {
    String command();

    String[] subCommands();

    String permission();

    boolean allowConsole() default false;

    Message description();

    String shortDescription();
}

