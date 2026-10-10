package com.geminixalp.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHandler {
    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld == null || mc.thePlayer == null) return;

        if (ConfigHandler.secretHighlight) {
            for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
                if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntitySkull) {
                    AxisAlignedBB box = new AxisAlignedBB(
                            tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ(),
                            tileEntity.getPos().getX() + 1, tileEntity.getPos().getY() + 1, tileEntity.getPos().getZ() + 1
                    );
                    RenderUtils.drawOutlinedBoundingBox(box, 1.0F, 0.0F, 0.0F, 1.0F, 2.0F); 
                    RenderUtils.drawBeaconLine(tileEntity.getPos().getX() + 0.5, tileEntity.getPos().getY(), tileEntity.getPos().getZ() + 0.5, 1.0F, 0.0F, 0.0F, 1.0F);
                }
            }
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityBat && !entity.isDead) {
                    RenderUtils.drawOutlinedBoundingBox(entity.getEntityBoundingBox(), 1.0F, 0.0F, 0.0F, 1.0F, 2.0F); 
                    RenderUtils.drawBeaconLine(entity.posX, entity.posY, entity.posZ, 1.0F, 0.0F, 0.0F, 1.0F);
                }
            }
        }

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity.isDead) continue;
            String name = entity.getCustomNameTag();
            if (name == null || name.isEmpty()) name = entity.getName();

            if (ConfigHandler.mobHighlight && entity instanceof EntityMob) {
                if (name.contains("Shadow Assassin") || name.contains("Key Guardian") || 
                    name.contains("Lost Adventurer") || name.contains("Frozen Adventurer") ||
                    name.contains("Angry Archaeologist") || name.contains("Withermancer")) {
                    RenderUtils.drawOutlinedBoundingBox(entity.getEntityBoundingBox(), 1.0F, 1.0F, 0.0F, 1.0F, 2.5F); 
                }
            }

            if (ConfigHandler.lividFinder && name.contains("Livid")) {
                if (DungeonHandler.isRealLivid(entity)) {
                    RenderUtils.drawOutlinedBoundingBox(entity.getEntityBoundingBox().expand(0.2, 0.2, 0.2), 0.0F, 1.0F, 0.0F, 1.0F, 4.0F); 
                    RenderUtils.drawBeaconLine(entity.posX, entity.posY, entity.posZ, 0.0F, 1.0F, 0.0F, 1.0F);
                }
            }
        }

        if (ConfigHandler.lividFinder && DungeonHandler.isInFloor5BossRoom()) {
            AxisAlignedBB spawnBox = new AxisAlignedBB(5, 107, 42, 7, 110, 44);
            RenderUtils.drawOutlinedBoundingBox(spawnBox, 1.0F, 0.5F, 0.0F, 1.0F, 3.0F); 
        }
    }

    @SubscribeEvent
    public void onRenderHUD(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        if (!ConfigHandler.dungeonHud) return;

        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fr = mc.fontRendererObj;

        String roomTaskText = DungeonHandler.isRoomCleared() ? 
                EnumChatFormatting.GREEN + EnumChatFormatting.BOLD.toString() + "✔ Room Cleared!" :
                EnumChatFormatting.YELLOW + "Objective: " + EnumChatFormatting.WHITE + DungeonHandler.getCurrentRoomObjective();
        fr.drawStringWithShadow(roomTaskText, 10, 10, 0xFFFFFF);

        int currentScore = DungeonHandler.calculateDungeonScore();
        String scoreColor = currentScore >= 300 ? EnumChatFormatting.GREEN.toString() : EnumChatFormatting.RED.toString();
        String scoreText = "Dungeon Score: " + scoreColor + currentScore + EnumChatFormatting.GRAY + " (Target: 300)";
        fr.drawStringWithShadow(scoreText, 10, 22, 0xFFFFFF);

        draw2DMapOverlay(fr);
    }

    private void draw2DMapOverlay(FontRenderer fr) {
        int x = 10;
        int y = 38;
        int width = 75;
        int height = 52;
        Gui.drawRect(x, y, x + width, y + height, 0x80000000);
        fr.drawString(EnumChatFormatting.AQUA + "Dungeon Map", x + 5, y + 4, 0xFFFFFF);
        Gui.drawRect(x + 8, y + 18, x + 23, y + 30, 0xFF00FF00);  
        Gui.drawRect(x + 26, y + 18, x + 41, y + 30, 0xFFFFFF00); 
        Gui.drawRect(x + 44, y + 18, x + 59, y + 30, 0xFFFF0000); 
        fr.drawString(EnumChatFormatting.GRAY + "Grid: Active", x + 8, y + 36, 0xAAAAAA);
    }
}
