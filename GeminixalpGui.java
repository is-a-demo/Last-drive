package com.geminixalp.mod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import java.io.IOException;

public class GeminixalpGui extends GuiScreen {
    private GuiButton btnFps;
    private GuiButton btnSecrets;
    private GuiButton btnMobs;
    private GuiButton btnLivid;
    private GuiButton btnHud;
    private GuiButton btnBatSound;

    @Override
    public void initGui() {
        this.buttonList.clear();
        int buttonWidth = 230;
        int buttonHeight = 26; 
        int startX = (this.width - buttonWidth) / 2;
        int startY = this.height / 6;
        int spacing = 30;

        btnFps = new GuiButton(0, startX, startY, buttonWidth, buttonHeight, getFpsText());
        btnSecrets = new GuiButton(1, startX, startY + spacing, buttonWidth, buttonHeight, getSecretsText());
        btnMobs = new GuiButton(2, startX, startY + (spacing * 2), buttonWidth, buttonHeight, getMobsText());
        btnLivid = new GuiButton(3, startX, startY + (spacing * 3), buttonWidth, buttonHeight, getLividText());
        btnHud = new GuiButton(4, startX, startY + (spacing * 4), buttonWidth, buttonHeight, getHudText());
        btnBatSound = new GuiButton(5, startX, startY + (spacing * 5), buttonWidth, buttonHeight, getBatSoundText());

        this.buttonList.add(btnFps);
        this.buttonList.add(btnSecrets);
        this.buttonList.add(btnMobs);
        this.buttonList.add(btnLivid);
        this.buttonList.add(btnHud);
        this.buttonList.add(btnBatSound);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) { ConfigHandler.fpsBooster = !ConfigHandler.fpsBooster; btnFps.displayString = getFpsText(); }
        else if (button.id == 1) { ConfigHandler.secretHighlight = !ConfigHandler.secretHighlight; btnSecrets.displayString = getSecretsText(); }
        else if (button.id == 2) { ConfigHandler.mobHighlight = !ConfigHandler.mobHighlight; btnMobs.displayString = getMobsText(); }
        else if (button.id == 3) { ConfigHandler.lividFinder = !ConfigHandler.lividFinder; btnLivid.displayString = getLividText(); }
        else if (button.id == 4) { ConfigHandler.dungeonHud = !ConfigHandler.dungeonHud; btnHud.displayString = getHudText(); }
        else if (button.id == 5) { ConfigHandler.batSound = !ConfigHandler.batSound; btnBatSound.displayString = getBatSoundText(); }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GOLD + EnumChatFormatting.BOLD.toString() + "Geminixalp Skyblock Dungeons Mod", this.width / 2, 12, 0xFFFFFF);
        this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.GRAY + "Touch-Friendly Mobile Edition (PojavLauncher)", this.width / 2, 24, 0xAAAAAA);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }

    private String getFpsText() { return "FPS Booster Mode: " + (ConfigHandler.fpsBooster ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"); }
    private String getSecretsText() { return "Secret Tracker & X-Ray: " + (ConfigHandler.secretHighlight ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"); }
    private String getMobsText() { return "Required Mob Highlight: " + (ConfigHandler.mobHighlight ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"); }
    private String getLividText() { return "Floor 5 Livid Auto-Pilot: " + (ConfigHandler.lividFinder ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"); }
    private String getHudText() { return "Dungeon Map & HUD: " + (ConfigHandler.dungeonHud ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"); }
    private String getBatSoundText() { return "Bat Secret Sound Alert: " + (ConfigHandler.batSound ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF"); }
}
