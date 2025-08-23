package club.maxstats.tabstats.mixin;

import club.maxstats.tabstats.config.TabStatsConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "org.polyfrost.vanillahud.hud.TabList$TabHud")
public class VanillaHudTabHudDrawBGMixin {
    @Inject(method = "drawBG", at = @At("HEAD"), cancellable = true)
    private void cancelVanillaHudDrawBG(CallbackInfo ci) {
        if (TabStatsConfig.isModToggled())
            ci.cancel();
    }
}
