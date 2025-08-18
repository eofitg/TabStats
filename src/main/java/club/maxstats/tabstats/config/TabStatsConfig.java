package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.References;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class TabStatsConfig {

    private final File configFile = new File("./config", References.MODID + ".properties");
    private final Properties props = new Properties();

    // ================= Configuration fields =================
    private boolean toggleMod = true;
    private String apiKey = "";
    private boolean textShadow = true;
    private int tabScale = 4;
    private int tabOpacity = 50;
    private Color outerTabbgColor = new Color(0,0,0,50);
    private Color innerTabBgColor = new Color(0,0,0,50);

    // ================= Constructor =================
    public TabStatsConfig() {
        load();
    }

    // ================= Load/Save =================
    public void load() {
        try {
            if (configFile.exists()) {
                props.load(java.nio.file.Files.newBufferedReader(configFile.toPath()));

                toggleMod = Boolean.parseBoolean(props.getProperty("toggleMod", String.valueOf(toggleMod)));
                apiKey = props.getProperty("apiKey", apiKey);
                textShadow = Boolean.parseBoolean(props.getProperty("textShadow", String.valueOf(textShadow)));
                tabScale = Integer.parseInt(props.getProperty("tabScale", String.valueOf(tabScale)));
                tabOpacity = Integer.parseInt(props.getProperty("tabOpacity", String.valueOf(tabOpacity)));
                outerTabbgColor = new Color(Integer.parseInt(props.getProperty("outerTabbgColor", String.valueOf(outerTabbgColor.getRGB()))), true);
                innerTabBgColor = new Color(Integer.parseInt(props.getProperty("innerTabBgColor", String.valueOf(innerTabBgColor.getRGB()))), true);
            } else {
                save();
            }
        } catch (Exception e) {
            e.printStackTrace();
            save();
        }
    }

    public void save() {
        try {
            props.setProperty("toggleMod", String.valueOf(toggleMod));
            props.setProperty("apiKey", apiKey);
            props.setProperty("textShadow", String.valueOf(textShadow));
            props.setProperty("tabScale", String.valueOf(tabScale));
            props.setProperty("tabOpacity", String.valueOf(tabOpacity));
            props.setProperty("outerTabbgColor", String.valueOf(outerTabbgColor.getRGB()));
            props.setProperty("innerTabBgColor", String.valueOf(innerTabBgColor.getRGB()));

            if (!configFile.getParentFile().exists()) configFile.getParentFile().mkdirs();
            props.store(new FileWriter(configFile), References.MODNAME + " Config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ================= Getters / Setters =================
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; save(); }

    public boolean isModToggled() { return toggleMod; }
    public void setToggleMod(boolean toggleMod) { this.toggleMod = toggleMod; save(); }

    public boolean getTextShadow() { return textShadow; }
    public void setTextShadow(boolean textShadow) { this.textShadow = textShadow; save(); }

    public int getTabScale() { return tabScale; }
    public void setTabScale(int tabScale) { this.tabScale = tabScale; save(); }

    public int getTabOpacity() { return tabOpacity; }
    public void setTabOpacity(int tabOpacity) { this.tabOpacity = tabOpacity; save(); }

    public Color getOuterTabBgColor() { return outerTabbgColor; }
    public void setOuterTabBgColor(Color outerTabbgColor) { this.outerTabbgColor = outerTabbgColor; save(); }

    public Color getInnerTabBgColor() { return innerTabBgColor; }
    public void setInnerTabBgColor(Color innerTabBgColor) { this.innerTabBgColor = innerTabBgColor; save(); }
}
