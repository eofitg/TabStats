package club.maxstats.tabstats.util;

public class ARGB {
    public static int fromHex(String hex) {
        if (hex == null || hex.isEmpty()) throw new NumberFormatException();

        hex = hex.trim();
        if (hex.startsWith("0x") || hex.startsWith("0X")) {
            hex = hex.substring(2);
        }

        if (hex.length() == 8) {
            return (int) Long.parseLong(hex, 16);
        } else {
            throw new NumberFormatException();
        }
    }

    public static String toHex(int color) {
        long unsigned = color & 0xFFFFFFFFL;
        return String.format("0x%08X", unsigned);
    }
}
