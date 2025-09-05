package club.maxstats.tabstats.playerapi;

import club.maxstats.tabstats.playerapi.api.HypixelAPI;
import club.maxstats.tabstats.playerapi.api.games.bedwars.Bedwars;
import club.maxstats.tabstats.playerapi.api.games.duels.Duels;
import club.maxstats.tabstats.playerapi.exception.*;
import club.maxstats.tabstats.util.Multithreading;
import com.google.gson.JsonObject;
import net.minecraft.entity.player.EntityPlayer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class StatWorld {
    private static final Logger logger = Logger.getLogger(StatWorld.class.getName());
    private final ConcurrentHashMap<UUID, HPlayer> worldPlayers;
    protected final List<UUID> statAssembly = new ArrayList<>();
    protected final List<UUID> existedMoreThan5Seconds = new ArrayList<>();
    protected final Map<UUID, Integer> timeCheck = new HashMap<>();

    public StatWorld() {
        this.worldPlayers = new ConcurrentHashMap<>();
    }

    public void removePlayer(UUID playerUUID) {
        this.worldPlayers.remove(playerUUID);
    }

    public void addPlayer(UUID playerUUID, HPlayer player) {
        this.worldPlayers.put(playerUUID, player);
    }

    public void clearPlayers() {
        this.worldPlayers.clear();
    }

    public ConcurrentHashMap<UUID, HPlayer> getWorldPlayers() {
        return this.worldPlayers;
    }

    public void removeFromStatAssembly(UUID uuid) { this.statAssembly.remove(uuid); }

    public HPlayer getPlayerByUUID(UUID uuid) {
        return this.worldPlayers.get(uuid);
    }

    public HPlayer getPlayerByName(String name) {
        for (Map.Entry<UUID,HPlayer> playerEntry : this.worldPlayers.entrySet()) {
            if (playerEntry.getValue().getNickname().equalsIgnoreCase(name))
                return playerEntry.getValue();
        }

        return null;
    }

    public void fetchStats(EntityPlayer entityPlayer) {
        Multithreading.runAsync(() -> {
            UUID uuid = entityPlayer.getUniqueID();
            String playerName = entityPlayer.getName();
            String playerUUID = entityPlayer.getUniqueID().toString().replace("-", "");
            boolean nicked = uuid.version() == 1;

            HPlayer hPlayer = new HPlayer(playerUUID, playerName);
            hPlayer.setNicked(nicked);

            if (!nicked) {
                try {
                    // retrieves the entire json object for the player and stores it in wholeObject
                    JsonObject wholeObject = new HypixelAPI().getWholeObject(playerUUID);
                    JsonObject playerObject = wholeObject.get("player").getAsJsonObject();

                    hPlayer.setPlayerRank(playerObject);
                    hPlayer.setPlayerName(playerObject.get("displayname").getAsString());

                    // initialize which games you want the player to be created with
                    Bedwars bw = new Bedwars(playerName, playerUUID, wholeObject);
                    Duels duels = new Duels(playerName, playerUUID, wholeObject);

                    hPlayer.addGames(bw, duels);
                } catch (PlayerNullException | NonWhitelistedKeyException | ApiRequestException | InvalidKeyException | BadJsonException ex) {
                    this.addPlayer(uuid, hPlayer);
                    this.removeFromStatAssembly(uuid);

                    logger.severe("Could not retrieve player");
                    logger.log(java.util.logging.Level.SEVERE, "Exception while fetching stats", ex);
                    return;
                }
            }

            this.addPlayer(uuid, hPlayer);
            this.removeFromStatAssembly(uuid);
        });
    }
}
