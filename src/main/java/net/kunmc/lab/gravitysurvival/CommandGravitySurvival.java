package net.kunmc.lab.gravitysurvival;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandGravitySurvival extends CommandBase {

    @Override
    public String getName() {
        return "gravitysurvival";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.gravitysurvival.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        GravitySurvivalManager manager = GravitySurvivalManager.getInstance();
        if (args.length == 0)
            throw new WrongUsageException("commands.gravitysurvival.usage");

        if ("stop".equals(args[0])) {
            if (!manager.isRunning())
                throw new CommandException("commands.gravitysurvival.nostarted");
            sender.sendMessage(new TextComponentTranslation("commands.gravitysurvival.stop"));
            manager.stop();
            return;
        } else if ("start".equals(args[0])) {
            if (manager.isRunning())
                throw new CommandException("commands.gravitysurvival.alreadystarted");
            sender.sendMessage(new TextComponentTranslation("commands.gravitysurvival.start"));
            manager.start(args.length >= 2 ? parseInt(args[1], 1) : 30, args.length < 3 || parseBoolean(args[2]));
            return;
        } else if ("change".equals(args[0])) {
            if (!manager.isRunning())
                throw new CommandException("commands.gravitysurvival.nostarted");

            if (args.length >= 3 && "speed".equals(args[1])) {
                int sec = parseInt(args[2], 1);
                manager.setRotedSec(sec);
                manager.reset(false);
                sender.sendMessage(new TextComponentTranslation("commands.gravitysurvival.change.speed", sec));
                return;
            } else if (args.length >= 3 && "uniform".equals(args[1])) {
                boolean enable = parseBoolean(args[2]);
                manager.setUniform(enable);
                manager.reset(false);
                sender.sendMessage(new TextComponentTranslation("commands.gravitysurvival.change.uniform." + enable));
                return;
            }else if (args.length >= 3 && "forcedrotation".equals(args[1])) {
                boolean enable = parseBoolean(args[2]);
                manager.setForcedRotation(enable);
                manager.reset(false);
                sender.sendMessage(new TextComponentTranslation("commands.gravitysurvival.change.forcedrotation." + enable));
                return;
            }
        }

        throw new WrongUsageException("commands.gravitysurvival.usage");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "start", "stop", "change");
        } else if (("start".equals(args[0]) && args.length == 3) || ("change".equals(args[0]) && args.length == 3 && ("uniform".equals(args[1]) || "forcedrotation".equals(args[1])))) {
            return getListOfStringsMatchingLastWord(args, "true", "false");
        } else if ("change".equals(args[0]) && args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "speed", "uniform", "forcedrotation");
        }

        return Collections.emptyList();
    }
}
