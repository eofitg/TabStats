package club.maxstats.tabstats.command;

import club.maxstats.tabstats.config.TabStatsConfig;
import club.maxstats.tabstats.util.ARGB;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabStatsCommand extends CommandBase {

    private final List<String> options = Arrays.asList(
            "toggle", "apikey", "shadow", "scale", "xoffset", "yoffset",
            "opacity",  "outercolor", "innercolor"
    );

    @Override
    public String getCommandName() {
        return "tabstats";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/tabstats toggle|apikey <value>|shadow|scale <value>|xoffset <value|yoffset <value>|opacity <value>|outercolor <value>|innercolor <value>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("§eUsage: " + getCommandUsage(sender)));
            return;
        }

        String option = args[0].toLowerCase();

        switch (option) {
            case "toggle": {
                TabStatsConfig.setToggleMod(!TabStatsConfig.isModToggled());
                sender.addChatMessage(new ChatComponentText("§aTabStats mod toggled: " + TabStatsConfig.isModToggled()));
                break;
            }
            case "apikey": {
                if (args.length >= 2) {
                    TabStatsConfig.setApiKey(args[1]);
                    sender.addChatMessage(new ChatComponentText("§aAPI key set: " + TabStatsConfig.getApiKey()));
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent API key: " + TabStatsConfig.getApiKey()));
                }
                break;
            }
            case "shadow": {
                TabStatsConfig.setTextShadow(!TabStatsConfig.getTextShadow());
                sender.addChatMessage(new ChatComponentText("§aText shadow: " + TabStatsConfig.getTextShadow()));
                break;
            }
            case "scale": {
                if (args.length >= 2) {
                    try {
                        TabStatsConfig.setTabScale(Float.parseFloat(args[1]));
                        sender.addChatMessage(new ChatComponentText("§aTab scale set: " + TabStatsConfig.getTabScale()));
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent tab scale: " + TabStatsConfig.getTabScale()));
                }
                break;
            }
            case "xoffset": {
                if (args.length >= 2) {
                    try {
                        TabStatsConfig.setTabXOffset(Float.parseFloat(args[1]));
                        sender.addChatMessage(new ChatComponentText("§aTab X offset set: " + TabStatsConfig.getTabXOffset()));
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent tab X offset: " + TabStatsConfig.getTabXOffset()));
                }
                break;
            }
            case "yoffset": {
                if (args.length >= 2) {
                    try {
                        TabStatsConfig.setTabYOffset(Float.parseFloat(args[1]));
                        sender.addChatMessage(new ChatComponentText("§aTab Y offset set: " + TabStatsConfig.getTabYOffset()));
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent tab Y offset: " + TabStatsConfig.getTabYOffset()));
                }
                break;
            }
            case "opacity": {
                if (args.length >= 2) {
                    try {
                        TabStatsConfig.setTabOpacity(Integer.parseInt(args[1]));
                        sender.addChatMessage(new ChatComponentText("§aTab opacity set: " + TabStatsConfig.getTabOpacity()));
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid number!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent tab opacity: " + TabStatsConfig.getTabOpacity()));
                }
                break;
            }
            case "outercolor": {
                if (args.length >= 2) {
                    try {
                        ARGB test = ARGB.fromHex(args[1]);
                        TabStatsConfig.setOuterTabBgColor(test.toHexString());
                        sender.addChatMessage(new ChatComponentText("§aTab outer background color set: " + TabStatsConfig.getOuterTabBgColor()));
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid hexadecimal RGB number (e.g., FF0000, 0x000000)!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent tab outer background color: " + TabStatsConfig.getOuterTabBgColor()));
                }
                break;
            }
            case "innercolor": {
                if (args.length >= 2) {
                    try {
                        ARGB test = ARGB.fromHex(args[1]);
                        TabStatsConfig.setInnerTabBgColor(test.toHexString());
                        sender.addChatMessage(new ChatComponentText("§aTab inner background color set: " + TabStatsConfig.getInnerTabBgColor()));
                    } catch (NumberFormatException e) {
                        sender.addChatMessage(new ChatComponentText("§cPlease enter a valid hexadecimal RGB number (e.g., FF0000, 0x000000)!"));
                    }
                } else {
                    sender.addChatMessage(new ChatComponentText("§eCurrent tab inner background color: " + TabStatsConfig.getInnerTabBgColor()));
                }
                break;
            }
            default: {
                sender.addChatMessage(new ChatComponentText("§cUnknown option: " + option));
                sender.addChatMessage(new ChatComponentText("§eUsage: " + getCommandUsage(sender)));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            String typed = args[0].toLowerCase();
            for (String opt : options) {
                if (opt.startsWith(typed)) completions.add(opt);
            }
            return completions;
        }
        return new ArrayList<>();
    }

}
