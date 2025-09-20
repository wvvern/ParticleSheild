package wvv.particleshield.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import wvv.particleshield.config.ConfigHandler;

public class CommandShieldToggle extends CommandBase {

    @Override
    public String getCommandName() {
        return "shieldtoggle";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/shieldtoggle - Toggle the particle shield on/off";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 0) {
            throw new CommandException("Usage: " + getCommandUsage(sender));
        }

        ConfigHandler.isShieldActive = !ConfigHandler.isShieldActive;
        ConfigHandler.saveConfig();

        String status = ConfigHandler.isShieldActive ? "enabled" : "disabled";
        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText(EnumChatFormatting.GREEN + "Particle shield " + status)
        );
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