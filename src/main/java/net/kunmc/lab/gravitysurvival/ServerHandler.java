package net.kunmc.lab.gravitysurvival;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerHandler {
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent e) {
        if (e.side == Side.SERVER)
            GravitySurvivalManager.getInstance().tick();
    }
}
