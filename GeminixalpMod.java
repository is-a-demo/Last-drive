package com.geminixalp.mod;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = GeminixalpMod.MODID, name = GeminixalpMod.NAME, version = GeminixalpMod.VERSION, clientSideOnly = true)
public class GeminixalpMod {
    public static final String MODID = "geminixalp";
    public static final String NAME = "Geminixalp";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static GeminixalpMod instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandGeminixalp());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new DungeonHandler());
    }
}
