package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.References;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabStatsConfig {
    private static final Logger logger = Logger.getLogger(TabStatsConfig.class.getName());
    private static final File configFile = new File("./config", References.MOD_ID + ".properties");
    private static final Properties props = new Properties();

    // ================= Configuration fields =================
    private static boolean toggleMod = true;
    private static String apiKey = "";
    private static boolean textShadow = true;
    private static float tabScale = 4f;
    private static float tabXOffset = 0f;
    private static float tabYOffset = 0f;
    private static String outerTabBgColor = "0x7F000000";
    private static String innerTabBgColor = "0x7F000000";

    // ================= Constructor =================
    public TabStatsConfig() {
        load();
    }

    // ================= Load/Save =================
    private void load() {
        try {
            if (configFile.exists()) {
                props.load(java.nio.file.Files.newBufferedReader(configFile.toPath()));

                toggleMod = Boolean.parseBoolean(props.getProperty("toggleMod", String.valueOf(toggleMod)));
                apiKey = props.getProperty("apiKey", apiKey);
                textShadow = Boolean.parseBoolean(props.getProperty("textShadow", String.valueOf(textShadow)));
                tabScale = Float.parseFloat(props.getProperty("tabScale", String.valueOf(tabScale)));
                tabXOffset = Float.parseFloat(props.getProperty("tabXOffset", String.valueOf(tabXOffset)));
                tabYOffset = Float.parseFloat(props.getProperty("tabYOffset", String.valueOf(tabYOffset)));
                outerTabBgColor = props.getProperty("outerTabBgColor", outerTabBgColor);
                innerTabBgColor = props.getProperty("innerTabBgColor", innerTabBgColor);
            } else {
                save();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to load TabStats configuration, using defaults", e);
            save();
        }
    }

    private static void save() {
        try {
            props.setProperty("toggleMod", String.valueOf(toggleMod));
            props.setProperty("apiKey", apiKey);
            props.setProperty("textShadow", String.valueOf(textShadow));
            props.setProperty("tabScale", String.valueOf(tabScale));
            props.setProperty("tabXOffset", String.valueOf(tabXOffset));
            props.setProperty("tabYOffset", String.valueOf(tabYOffset));
            props.setProperty("outerTabBgColor", outerTabBgColor);
            props.setProperty("innerTabBgColor", innerTabBgColor);

            if (!configFile.getParentFile().exists()) configFile.getParentFile().mkdirs();
            props.store(new FileWriter(configFile), References.MOD_NAME + " Config");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save TabStats configuration", e);
        }
    }

    // ================= Getters / Setters =================
    public static String getApiKey() { return apiKey; }
    public static void setApiKey(String key) { apiKey = key; save(); }

    public static boolean isModToggled() { return toggleMod; }
    public static void setToggleMod(boolean toggle) { toggleMod = toggle; save(); }

    public static boolean getTextShadow() { return textShadow; }
    public static void setTextShadow(boolean shadow) { textShadow = shadow; save(); }

    public static float getTabScale() { return tabScale; }
    public static void setTabScale(float scale) { tabScale = scale; save(); }

    public static float getTabXOffset() { return tabXOffset; }
    public static void setTabXOffset(float offset) { tabXOffset = offset; save(); }
    public static float getTabYOffset() { return tabYOffset; }
    public static void setTabYOffset(float offset) { tabYOffset = offset; save(); }

    public static String getOuterTabBgColor() { return outerTabBgColor; }
    public static void setOuterTabBgColor(String color) { outerTabBgColor = color; save(); }

    public static String getInnerTabBgColor() { return innerTabBgColor; }
    public static void setInnerTabBgColor(String color) { innerTabBgColor = color; save(); }
}
