package wvv.particleshield.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import wvv.particleshield.config.ConfigHandler;

public class CommandShieldSize extends CommandBase {

    @Override
    public String getCommandName() {
        return "shieldsize";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/shieldsize <size> - Set the particle shield size (minimum distance from player)";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException("Usage: " + getCommandUsage(sender));
        }

        try {
            double newSize = Double.parseDouble(args[0]);
            if (newSize < 0) {
                newSize = 0;
            }

            ConfigHandler.shieldSize = newSize;
            ConfigHandler.saveConfig();

            // Send message to client player
            Minecraft.getMinecraft().thePlayer.addChatMessage(
                    new ChatComponentText(EnumChatFormatting.GREEN + "Particle shield size set to: " + newSize)
            );

        } catch (NumberFormatException e) {
            throw new CommandException("Invalid number: " + args[0]);
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
