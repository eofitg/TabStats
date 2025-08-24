package club.maxstats.tabstats.listener;

import club.maxstats.tabstats.util.ApiKeyUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

public class JoinWorldListener {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final Set<String> checkedWorlds = new HashSet<>();

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (!event.world.isRemote) return;
        if (!(event.entity instanceof EntityPlayer)) return;
        if (event.entity != mc.thePlayer) return;

        String worldId = getWorldIdentifier(event.world);
        if (!checkedWorlds.contains(worldId)) {
            checkedWorlds.add(worldId);
            ApiKeyUtil.checkApiKeyAndNotify(mc);
        }
    }

    private String getWorldIdentifier(World world) {
        if (world.isRemote) {
            if (mc.getCurrentServerData() != null) {
                return mc.getCurrentServerData().serverIP;  // server ip
            }
            return mc.getIntegratedServer().getFolderName();  // save name
        }
        return "unknown";
    }
}
