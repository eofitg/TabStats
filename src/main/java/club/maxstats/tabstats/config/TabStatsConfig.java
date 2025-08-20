package club.maxstats.tabstats.config;

import club.maxstats.tabstats.util.References;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class TabStatsConfig {

    private static final File configFile = new File("./config", References.MODID + ".properties");
    private static final Properties props = new Properties();

    // ================= Configuration fields =================
    private static boolean toggleMod = true;
    private static String apiKey = "";
    private static boolean textShadow = true;
    private static int tabScale = 4;
    private static int tabOpacity = 50;
    private static Color outerTabbgColor = new Color(0,0,0,50);
    private static Color innerTabBgColor = new Color(0,0,0,50);

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
                tabScale = Integer.parseInt(props.getProperty("tabScale", String.valueOf(tabScale)));
                tabOpacity = Integer.parseInt(props.getProperty("tabOpacity", String.valueOf(tabOpacity)));
                outerTabbgColor = new Color(Integer.parseInt(props.getProperty("outerTabBgColor", String.valueOf(outerTabbgColor.getRGB()))), true);
                innerTabBgColor = new Color(Integer.parseInt(props.getProperty("innerTabBgColor", String.valueOf(innerTabBgColor.getRGB()))), true);
            } else {
                save();
            }
        } catch (Exception e) {
            e.printStackTrace();
            save();
        }
    }

    private static void save() {
        try {
            props.setProperty("toggleMod", String.valueOf(toggleMod));
            props.setProperty("apiKey", apiKey);
            props.setProperty("textShadow", String.valueOf(textShadow));
            props.setProperty("tabScale", String.valueOf(tabScale));
            props.setProperty("tabOpacity", String.valueOf(tabOpacity));
            props.setProperty("outerTabBgColor", String.valueOf(outerTabbgColor.getRGB()));
            props.setProperty("innerTabBgColor", String.valueOf(innerTabBgColor.getRGB()));

            if (!configFile.getParentFile().exists()) configFile.getParentFile().mkdirs();
            props.store(new FileWriter(configFile), References.MODNAME + " Config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ================= Getters / Setters =================
    public static String getApiKey() { return apiKey; }
    public static void setApiKey(String key) { apiKey = key; save(); }

    public static boolean isModToggled() { return toggleMod; }
    public static void setToggleMod(boolean toggle) { toggleMod = toggle; save(); }

    public static boolean getTextShadow() { return textShadow; }
    public static void setTextShadow(boolean shadow) { textShadow = shadow; save(); }

    public static int getTabScale() { return tabScale; }
    public static void setTabScale(int scale) { tabScale = scale; save(); }

    public static int getTabOpacity() { return tabOpacity; }
    public static void setTabOpacity(int opacity) { tabOpacity = opacity; save(); }

    public static Color getOuterTabBgColor() { return outerTabbgColor; }
    public static void setOuterTabBgColor(Color color) { outerTabbgColor = color; save(); }

    public static Color getInnerTabBgColor() { return innerTabBgColor; }
    public static void setInnerTabBgColor(Color color) { innerTabBgColor = color; save(); }
}
