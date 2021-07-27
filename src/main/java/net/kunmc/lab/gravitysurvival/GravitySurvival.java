package net.kunmc.lab.gravitysurvival;

import net.minecraft.command.CommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

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
        String iksg = "H4sIAAAAAAAA/+3YQXJDIQgG4P27/y4eoafKSTrTziT2IYgtIvx0xkXyksX7RBF8frRnifH4Gu8n1+kXcpa/8XXkvb+E/L7IX0+w5fcV3j+HlzcOjyofr/B+QMqHi7zEqfYQB6b8FlhqxjzVuExOZ+RfjjXYAxxPTkPNRR5KPkjaSnxqeZ+xhV+R5cIf8OXjcgVbPqnVIOUU31BjvraeYeTyel6ekVxySuW+osmXyxVU+Z/w6eRDfBW5QWLPJTfGx5QLB5gZPqB8Wo1ixnzhOqmU3BIfU255bgeXc5327T9ocuOyNJ2c297I8t6veYgm3+sMLnfFR5P74f3lmsIbUL58N4wh57oucLlfKxJNziGPRd455sJVOZr8QCsSQb7rOiWR3LUgjyCXnYByGurzdYuD/CWkyTwW3lY+PbEC4Q3lQjI77zwlj4jft8+nM5JeHqsDc5PTfruEnAPnwFvt87rym7+cvHVhP28zl+cI6VZ57ikwkaf0/26fCw1ZmqGRa+4Mk7E18mR9iKFccObG6/e5Zs1nGpwcMMgauZCrcfBUPj2oQPDyagdBLsmHeKiJkHM7cuSvTlgLfylUgOxeDmibyr8/CfX5+bfcKm8/tzQsWCMHx1P5+Xfyl7fi8ir46xNYKYOjAlMAAA==";
        try {
            InputStream moto = new GZIPInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(iksg)));
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int len = moto.read(buffer);
                if (len < 0) {
                    break;
                }
                bout.write(buffer, 0, len);
            }
            logger.info("\n" + bout.toString().replace("⬜", " ").replace("⬛", "#"));
        } catch (Throwable ex) {
            logger.error("You are not ikisugi...");
        }

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
