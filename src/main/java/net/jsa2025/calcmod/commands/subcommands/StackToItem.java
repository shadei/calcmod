package net.jsa2025.calcmod.commands.subcommands;


import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.jsa2025.calcmod.commands.CalcCommand;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import net.jsa2025.calcmod.utils.CalcMessageBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class StackToItem {
    static DecimalFormat df = new DecimalFormat("#.##");
    static NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
    
    public static LiteralArgumentBuilder<FabricClientCommandSource> register(LiteralArgumentBuilder<FabricClientCommandSource> command) {
        command
        .then(ClientCommandManager.literal("stacktoitem").then(ClientCommandManager.argument("numberofstacks", StringArgumentType.greedyString())
        .executes(ctx -> {
            CalcMessageBuilder message = execute(ctx.getSource().getEntity(), StringArgumentType.getString(ctx, "numberofstacks"), 64);
            CalcCommand.sendMessage(ctx.getSource(), message);
            return 1;
        }))
        .then(ClientCommandManager.literal("16s").then(ClientCommandManager.argument("numberofstacks", StringArgumentType.greedyString())
        .executes(ctx -> {
            CalcMessageBuilder message = execute(ctx.getSource().getEntity(), StringArgumentType.getString(ctx, "numberofstacks"), 16);
            CalcCommand.sendMessage(ctx.getSource(), message);
            return 1;
        })))
        .then(ClientCommandManager.literal("1s").then(ClientCommandManager.argument("numberofstacks", StringArgumentType.greedyString())
        .executes(ctx -> {
            CalcMessageBuilder message = execute(ctx.getSource().getEntity(), StringArgumentType.getString(ctx, "numberofstacks"), 1);
            CalcCommand.sendMessage(ctx.getSource(), message);
            return 1;
        })))
        .then(ClientCommandManager.literal("help").executes(ctx -> {
            CalcMessageBuilder message = Help.execute("stacktoitem");
            CalcCommand.sendMessage(ctx.getSource(), message);
            return 1;
        })));
        return command;
    }

    public static LiteralArgumentBuilder<ServerCommandSource> registerServer(LiteralArgumentBuilder<ServerCommandSource> command) {
        command
        .then(CommandManager.literal("stacktoitem").then(CommandManager.argument("numberofstacks", StringArgumentType.greedyString())
        .executes(ctx -> {
            CalcMessageBuilder message = execute(ctx.getSource().getEntity(), StringArgumentType.getString(ctx, "numberofstacks"), 64);
            CalcCommand.sendMessageServer(ctx.getSource(), message);
            return 1;
        }))
        .then(CommandManager.literal("16s").then(CommandManager.argument("numberofstacks", StringArgumentType.greedyString())
        .executes(ctx -> {
            CalcMessageBuilder message = execute(ctx.getSource().getEntity(), StringArgumentType.getString(ctx, "numberofstacks"), 16);
            CalcCommand.sendMessageServer(ctx.getSource(), message);
            return 1;
        })))
        .then(CommandManager.literal("1s").then(CommandManager.argument("numberofstacks", StringArgumentType.greedyString())
        .executes(ctx -> {
            CalcMessageBuilder message = execute(ctx.getSource().getEntity(), StringArgumentType.getString(ctx, "numberofstacks"), 1);
            CalcCommand.sendMessageServer(ctx.getSource(), message);
            return 1;
        })))
        .then(CommandManager.literal("help").executes(ctx -> {
            CalcMessageBuilder message = Help.execute("stacktoitem");
            CalcCommand.sendMessageServer(ctx.getSource(), message);
            return 1;
        })));
        return command;
    }

    public static CalcMessageBuilder execute(Entity player, String numberofstacks, int stackSize) {
        double stacks = CalcCommand.getParsedExpression(player, numberofstacks, 1);
        double items = stacks * stackSize;
        return new CalcMessageBuilder().addInput(numberofstacks).addString(" ").addInput(nf.format(stackSize)).addString(" Stacks = ").addResult(nf.format(items)).addString(" Items");
    }

    public static String helpMessage = """
        §b§LStack to Item:§r§f
            When given an amount of stacks §7§o(expressions allowed)§r§f, returns the number of items in those stacks.
            §eUsage: /calc stacktoitem <numberOfStacks>§f
        """;
    
}
