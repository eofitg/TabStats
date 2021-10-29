package club.maxstats.tabstats.playerapi;

import club.maxstats.tabstats.playerapi.api.games.HGameBase;
import club.maxstats.tabstats.playerapi.api.stats.Stat;
import club.maxstats.tabstats.util.ChatColor;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

/* Hypixel Player */
public class HPlayer {
    private HashMap<String, HGameBase> gameMap;
    private String playerUUID, playerName, nickName, playerRank, playerTag = "-";
    private boolean nicked;
    private int playerTagColor = -7895161;

    /**
     * @param playerUUID Player's UUID
     * @param playerName Player's Name
     * @param gameBase All HGameBase's you would like the HPlayer to contain
     * (Generally you would like all HGameBases which are complete to be added)
     */
    public HPlayer(String playerUUID, String playerName, HGameBase... gameBase) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;

        this.gameMap = new HashMap<>();
        for (HGameBase game : gameBase) {
            this.gameMap.put(game.getGame().getGameName(), game);
        }
    }

    /* Meant for nicked players */
    public HPlayer(String playerUUID, String playerName, String nickName, HGameBase... gameBase) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.nickName = nickName;
        this.nicked = true;

        this.gameMap = new HashMap<>();
        for (HGameBase game : gameBase) {
            this.gameMap.put(game.getGame().getGameName(), game);
        }
    }

    /* Meant for null api players */
    public HPlayer(String playerUUID, String playerName) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.playerRank = "§7";;

        this.gameMap = new HashMap<>();
    }

    public void addGames(HGameBase... gameBases) {
        if (this.gameMap == null) {
            this.gameMap = new HashMap<>();
        }

        for (HGameBase game : gameBases) {
            this.gameMap.put(game.getGame().getGameName(), game);
        }
    }

    public String getPlayerUUID() {
        return this.playerUUID;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getNickname() {
        return "Nickname Test";
//        return nickName == null ? playerName : nickName;
    }

    public List<Stat> getFormattedGameStats(String gameName) {
        return this.gameMap.get(gameName).getFormattedStatList();
    }

    public String getPlayerTag() { return this.playerTag; }

    public int getPlayerTagColor() { return this.playerTagColor; }

    public void setNicked(boolean nicked) { this.nicked = nicked; }

    public boolean isNicked() { return this.nicked; }

    public void setPlayerRank(String playerRank) {
        this.playerRank = playerRank;
    }

    public void setPlayerRank(JsonObject playerObject) {
        String s = "", staff, rank = "", rankColour, mvpPlusPlus;
        JsonObject player = playerObject.getAsJsonObject();

        try {
            staff = player.get("rank").getAsString();
        } catch (NullPointerException ignored) {
            staff = "NOT STAFF";
        }
        try {
            mvpPlusPlus = player.get("monthlyPackageRank").getAsString();
        } catch (Exception e) {
            mvpPlusPlus = "NEVER BROUGHT";
        }
        try {
            rank = player.get("newPackageRank").getAsString();
        } catch (NullPointerException e) {
            s = ChatColor.GRAY + "";
        }
        try {
            rankColour = player.get("rankPlusColor").getAsString();
        } catch (Exception e) {
            rankColour = "RED";
        }
        if (mvpPlusPlus.equalsIgnoreCase("SUPERSTAR")) {
            s = ChatColor.GOLD + "[MVP" + ChatColor.valueOf(rankColour) + "++" + ChatColor.GOLD + "] ";
        } else if (!mvpPlusPlus.equalsIgnoreCase("SUPERSTAR")) {
            if (rank.equalsIgnoreCase("MVP_PLUS")) {
                s = ChatColor.AQUA + "[MVP" + ChatColor.valueOf(rankColour) + "+" + ChatColor.AQUA + "] ";
            } else if (rank.equalsIgnoreCase("MVP")) {
                s = ChatColor.AQUA + "[MVP] ";
            } else if (rank.equalsIgnoreCase("VIP_PLUS")) {
                s = ChatColor.GREEN + "[VIP" + ChatColor.GOLD + "+" + ChatColor.GREEN + "] ";
            } else if (rank.equalsIgnoreCase("VIP")) {
                s = ChatColor.GREEN + "[VIP] ";
            }
        }
        try {
            if (staff.equalsIgnoreCase("HELPER")) {
                s = ChatColor.BLUE + "[HELPER] ";
            } else if (staff.equalsIgnoreCase("MODERATOR")) {
                s = ChatColor.DARK_GREEN + "[MODERATOR] ";
            } else if (staff.equalsIgnoreCase("ADMIN")) {
                s = ChatColor.RED + "[ADMIN] ";
            } else if (staff.equalsIgnoreCase("YOUTUBER")) {
                s = ChatColor.RED + "[" + ChatColor.WHITE + "YOUTUBE" + ChatColor.RED + "] ";
            }
        } catch (Exception ignored) {
        }
        this.playerRank = s;
    }

    public String getPlayerRank() {
        return this.playerRank == null || this.playerRank.isEmpty() ? "§7" : this.playerRank;
    }

    public String getPlayerRankColor() {
        return this.playerRank == null || this.playerRank.isEmpty() ? "§7" : this.playerRank.substring(0, 2);
    }

    public HGameBase getGame(String gameName) {
        return this.gameMap.get(gameName);
    }
}
