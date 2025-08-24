package club.maxstats.tabstats.listener;

import club.maxstats.tabstats.util.ApiKeyUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JoinWorldListener {
    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (!event.world.isRemote) return;
        if (!(event.entity instanceof EntityPlayer)) return;
        if (event.entity != mc.thePlayer) return;

        ApiKeyUtil.checkApiKeyAndNotify(mc);
    }
}
