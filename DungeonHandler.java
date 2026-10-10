package com.geminixalp.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DungeonHandler {

    private static int lastBatCount = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return;

        if (ConfigHandler.fpsBooster) {
            mc.gameSettings.particleSetting = 2; 
            mc.gameSettings.enableVsync = false;
        }

        if (ConfigHandler.batSound) {
            int currentBats = 0;
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityBat && !entity.isDead) {
                    currentBats++;
                }
            }
            if (lastBatCount > currentBats) {
                mc.thePlayer.playSound("note.pling", 1.0F, 1.5F);
            }
            lastBatCount = currentBats;
        }
    }

    public static String getCurrentRoomObjective() { return "Kill Mobs & Collect Secrets"; }
    public static boolean isRoomCleared() { return false; }
    public static int calculateDungeonScore() { return 300; }

    public static boolean isInFloor5BossRoom() {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return false;
        double x = mc.thePlayer.posX;
        double z = mc.thePlayer.posZ;
        return x >= 0 && x <= 20 && z >= 30 && z <= 50;
    }

    public static boolean isRealLivid(Entity entity) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null) return false;

        String name = entity.getCustomNameTag();
        if (name == null) name = entity.getName();

        BlockPos woolPos = new BlockPos(6, 109, 43);
        if (mc.theWorld.getBlockState(woolPos).getBlock() == Blocks.wool) {
            int metadata = mc.theWorld.getBlockState(woolPos).getBlock().getMetaFromState(mc.theWorld.getBlockState(woolPos));
            return name.startsWith(getLividColorPrefix(metadata));
        }

        return name.contains("Livid") && !name.contains("Vendetta");
    }

    private static String getLividColorPrefix(int woolMeta) {
        switch (woolMeta) {
            case 0: return EnumChatFormatting.WHITE.toString();
            case 4: return EnumChatFormatting.YELLOW.toString();
            case 6: return EnumChatFormatting.LIGHT_PURPLE.toString();
            case 11: return EnumChatFormatting.BLUE.toString();
            case 10: return EnumChatFormatting.DARK_PURPLE.toString();
            case 13: return EnumChatFormatting.GREEN.toString();
            case 7: return EnumChatFormatting.GRAY.toString();
            case 14: return EnumChatFormatting.RED.toString();
            default: return EnumChatFormatting.WHITE.toString();
        }
    }
}
