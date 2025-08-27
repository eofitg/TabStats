package club.maxstats.tabstats.util;

import club.maxstats.tabstats.config.TabStatsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class ApiKey {
    public static void checkApiKeyAndNotify(Minecraft mc) {
        if (mc == null || mc.thePlayer == null) return;
        EntityPlayer player = mc.thePlayer;

        String apiKey = TabStatsConfig.getApiKey();
        String playerUUID = player.getUniqueID().toString().replace("-", "");

        if (apiKey == null || apiKey.isEmpty() || !isValidApiKey(apiKey, playerUUID)) {
            sendInvalidKeyMessage(player);
        }
    }

    private static boolean isValidApiKey(String key, String uuid) {
        String requestURL = String.format("https://api.hypixel.net/v2/player?key=%s&uuid=%s", key, uuid.replace("-", ""));

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(requestURL);
            JsonParser parser = new JsonParser();
            StringWriter writer = new StringWriter();

            IOUtils.copy(new InputStreamReader(client.execute(request).getEntity().getContent(), StandardCharsets.UTF_8), writer);
            JsonObject obj = parser.parse(writer.toString()).getAsJsonObject();

            if (obj.get("success").getAsBoolean())
                return true;
            return !obj.get("cause").getAsString().equalsIgnoreCase("Invalid API key");
        } catch (Exception ex) {
            return false;
        }
    }

    private static void sendInvalidKeyMessage(EntityPlayer targetPlayer) {
        targetPlayer.addChatMessage(new ChatComponentText("§a[TabStats] §cYour API key is missing or invalid. You can generate a new one at the link below:"));
        // Clickable URL
        ChatComponentText link = new ChatComponentText("§9https://developer.hypixel.net/dashboard");
        link.setChatStyle(new ChatStyle()
                .setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://developer.hypixel.net/dashboard"))
                .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ChatComponentText("§eOpen Hypixel Developer Dashboard"))));
        targetPlayer.addChatMessage(link);
    }
}
