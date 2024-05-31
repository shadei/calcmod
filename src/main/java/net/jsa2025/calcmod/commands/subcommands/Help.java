package net.jsa2025.calcmod.commands.subcommands;



import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.jsa2025.calcmod.commands.CalcCommand;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import net.jsa2025.calcmod.utils.CalcMessageBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class Help {
    static DecimalFormat df = new DecimalFormat("#.##");
    static NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
    
    public static LiteralArgumentBuilder<FabricClientCommandSource> register(LiteralArgumentBuilder<FabricClientCommandSource> command) {
        command
        .then(ClientCommandManager.literal("help")
        .executes(ctx -> {
            CalcMessageBuilder message = execute();
            CalcCommand.sendMessage(ctx.getSource(), message);
            return 1;
        }));
        return command;
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerServer(LiteralArgumentBuilder<ServerCommandSource> command) {
        command
        .then(CommandManager.literal("help")
        .executes(ctx -> {
            CalcMessageBuilder message = execute();
            CalcCommand.sendMessageServer(ctx.getSource(), message);
            return 1;
        }));
        return command;
    }

    public static CalcMessageBuilder execute(String... hterm) {
        Map<String, String> help = new LinkedHashMap<String, String>();
        help.put("storage", Storage.helpMessage);
        help.put("nether", Nether.helpMessage);
        help.put("overworld", Overworld.helpMessage);
        help.put("sbtoitem", SbToItem.helpMessage);
        help.put("itemtosb", ItemToSb.helpMessage);
        help.put("secondstohopperclock", SecondsToHopperClock.helpMessage);
        help.put("secondstorepeater", SecondsToRepeater.helpMessage);
        help.put("itemtostack", ItemToStack.helpMessage);
        help.put("stacktoitem", StackToItem.helpMessage);
        help.put("rates", Rates.helpMessage);
        help.put("allaystorage", AllayStorage.helpMessage);
        help.put("craft", Craft.helpMessage);
        help.put("random", Random.helpMessage);
        help.put("signaltoitems", SignalToItems.helpMessage);
        help.put("barter", Piglin.helpMessage);
        help.put("custom", Custom.helpMessage);
        if (hterm.length == 0) {
            String helpMenu = "";
            for (Map.Entry<String, String> me :
             help.entrySet()) {
                helpMenu += me.getValue() + "\n";
  
              }
            CalcMessageBuilder messageBuilder = new CalcMessageBuilder(helpMenu);
            return messageBuilder;
        } else {
            CalcMessageBuilder messageBuilder = new CalcMessageBuilder(help.get(hterm[0]));
            return messageBuilder;
        }
    }

    
}
