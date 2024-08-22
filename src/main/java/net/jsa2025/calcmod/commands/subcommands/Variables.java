package net.jsa2025.calcmod.commands.subcommands;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.jsa2025.calcmod.commands.CalcCommand;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import net.jsa2025.calcmod.utils.CalcMessageBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class Variables {
    static DecimalFormat df = new DecimalFormat("#.##");
    static NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
    
    public static LiteralArgumentBuilder<FabricClientCommandSource> register(LiteralArgumentBuilder<FabricClientCommandSource> command) {
        command
        .then(ClientCommandManager.literal("variables")
        .executes(ctx -> {
            CalcMessageBuilder message = execute();
            CalcCommand.sendMessage(ctx.getSource(), message);
            return 1;
        }));
        return command;
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerServer(LiteralArgumentBuilder<ServerCommandSource> command) {
        command
        .then(CommandManager.literal("variables")
        .executes(ctx -> {
            CalcMessageBuilder message = execute();
            CalcCommand.sendMessageServer(ctx.getSource(), message);
            return 1;
        }));
        return command;
    }

    public static CalcMessageBuilder execute() {
        String message = """
            §bVariables§f can be used inside equations in any number field. They act as shortcuts instead of having to remember that “a double chest full of 16 stackable items is 864.”
            If no stack size is given, variables default to the contextualized stack size in each command.
            
                §edub:§f 3456 §7§o(default)§r§f
                §edub64:§f 3456
                §edub16:§f 864
                §edub1:§f 54
                §esb:§f 1728 §7§o(default)§r§f
                §esb64:§f 1728
                §esb16:§f 432
                §esb1:§f 27
                §estack:§f 64 §7§o(default)§r§f
                §estack64:§f 64
                §estack16:§f 16
                §estack1:§f 1
                §emin:§f 60
                §ehour:§f 3600
    
            §bDynamic Variables§f change depending on an in-game status. These can be particularly useful inside custom functions.
                
                §ex:§f player x coordinate
                §ey:§f player y coordinate
                §ez:§f player z coordinate
                §ehealth:§f player health
                """;
        return new CalcMessageBuilder(message);
    }
}
