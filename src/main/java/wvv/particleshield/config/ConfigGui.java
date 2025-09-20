package wvv.particleshield.config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class ConfigGui extends GuiScreen {

    private GuiTextField shieldSizeField;
    private GuiButton toggleButton;

    private final GuiScreen parentScreen;

    public ConfigGui(GuiScreen parent) {
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        // Shield size text field
        this.shieldSizeField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 2 - 40, 200, 20);
        this.shieldSizeField.setMaxStringLength(10);
        this.shieldSizeField.setText(String.valueOf(ConfigHandler.shieldSize));

        // Toggle button
        String toggleText = "Shield Active: " + (ConfigHandler.isShieldActive ? "ON" : "OFF");
        this.toggleButton = new GuiButton(1, this.width / 2 - 100, this.height / 2 - 10, 200, 20, toggleText);
        this.buttonList.add(this.toggleButton);

        // Done button
        GuiButton doneButton = new GuiButton(2, this.width / 2 - 100, this.height / 2 + 20, 200, 20, "Done");
        this.buttonList.add(doneButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) { // Toggle button
            ConfigHandler.isShieldActive = !ConfigHandler.isShieldActive;
            this.toggleButton.displayString = "Shield Active: " + (ConfigHandler.isShieldActive ? "ON" : "OFF");
        } else if (button.id == 2) { // Done button
            // Save shield size from text field
            try {
                double newSize = Double.parseDouble(this.shieldSizeField.getText());
                if (newSize >= 0) {
                    ConfigHandler.shieldSize = newSize;
                }
            } catch (NumberFormatException e) {
                // Invalid number, keep current value
            }

            // Save config
            ConfigHandler.saveConfig();
            System.out.println("ParticleShield: Config saved - shieldSize: " + ConfigHandler.shieldSize + ", isShieldActive: " + ConfigHandler.isShieldActive);

            // Close GUI
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.shieldSizeField.textboxKeyTyped(typedChar, keyCode)) {
            return;
        }

        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(this.parentScreen);
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.shieldSizeField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.shieldSizeField.updateCursorCounter();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        // Title
        this.drawCenteredString(this.fontRendererObj, "Particle Shield", this.width / 2, this.height / 2 - 80, 0xFFFFFF);

        // Shield size label
        this.drawCenteredString(this.fontRendererObj, "Shield Size:", this.width / 2, this.height / 2 - 55, 0xFFFFFF);

        // Draw text field
        this.shieldSizeField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}