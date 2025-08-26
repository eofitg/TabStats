package club.maxstats.tabstats.util;

public class ARGB {
    private int red;
    private int green;
    private int blue;
    private int opacity; // alpha
    private int value;   // 0xAARRGGBB

    public ARGB(int red, int green, int blue, int opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
        refreshValue();
    }

    public ARGB(int color) {
        this.value = color;
        this.red = getRed(color);
        this.green = getGreen(color);
        this.blue = getBlue(color);
        this.opacity = getOpacity(color);
    }

    private void refreshValue() {
        this.opacity = Math.max(0, Math.min(100, this.opacity));  // 0 ~ 100
        int alpha = Math.round(this.opacity * 255f / 100f);
        this.value = ((alpha & 0xFF) << 24) |
                     ((red & 0xFF) << 16) |
                     ((green & 0xFF) << 8) |
                     ((blue & 0xFF));
    }

    public int getRed() { return this.red; }

    public int getGreen() { return this.green; }

    public int getBlue() { return this.blue; }

    public int getOpacity() { return this.opacity; }

    public ARGB setRed(int red) {
        this.red = red;
        refreshValue();
        return this;
    }

    public ARGB setGreen(int green) {
        this.green = green;
        refreshValue();
        return this;
    }

    public ARGB setBlue(int blue) {
        this.blue = blue;
        refreshValue();
        return this;
    }

    public ARGB setOpacity(int opacity) {
        this.opacity = opacity;
        refreshValue();
        return this;
    }

    public int toARGB() {
        return this.value;
    }

    public ARGB getNew() {
        return new ARGB(this.red, this.green, this.blue, this.opacity);
    }

    public static ARGB adjustOpacity(int color, int opacity) {
        return new ARGB(getRed(color), getGreen(color), getBlue(color), opacity);
    }

    public static int getRed(int color) { return color >> 16 & 0xFF; }

    public static int getGreen(int color) { return color >> 8 & 0xFF; }

    public static int getBlue(int color) { return color & 0xFF; }

    public static int getOpacity(int color) { return color >> 24 & 0xFF; }

    public static ARGB fromHex(String hex) {
        if (hex == null || hex.isEmpty()) throw new NumberFormatException();

        hex = hex.trim();
        if (hex.startsWith("0x") || hex.startsWith("0X")) {
            hex = hex.substring(2);
        } else if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        int color;
        if (hex.length() == 8) {
            color = (int) Long.parseLong(hex, 16);
        } else {
            throw new NumberFormatException();
        }
        return new ARGB(color);
    }

    public String toHexString() {
        long unsigned = this.value & 0xFFFFFFFFL;
        return String.format("0x%08X", unsigned);
    }
}
