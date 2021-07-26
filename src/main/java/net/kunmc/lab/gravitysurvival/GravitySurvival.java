package net.kunmc.lab.gravitysurvival;

import net.minecraft.command.CommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = GravitySurvival.MODID, name = GravitySurvival.NAME, version = GravitySurvival.VERSION)
public class GravitySurvival {
    public static final String MODID = "gravitysurvival";
    public static final String NAME = "Gravity Survival";
    public static final String VERSION = "1.0";
    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(ServerHandler.class);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {

    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        ((CommandHandler) event.getServer().getCommandManager()).registerCommand(new CommandGravitySurvival());
    }

    @Mod.EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
        GravitySurvivalManager.getInstance().stop();
    }
}
