package com.geminixalp.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.ArrayList;
import java.util.List;

public class CommandGeminixalp extends CommandBase {
    private boolean openGuiPending = false;

    public CommandGeminixalp() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public String getCommandName() { return "Geminixalp"; }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<String>();
        aliases.add("geminixalp");
        aliases.add("gx");
        return aliases;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/Geminixalp - Opens the Geminixalp setup menu"; }

    @Override
    public int getRequiredPermissionLevel() { return 0; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        openGuiPending = true;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (openGuiPending && Minecraft.getMinecraft().thePlayer != null) {
            openGuiPending = false;
            Minecraft.getMinecraft().displayGuiScreen(new GeminixalpGui());
        }
    }
}
