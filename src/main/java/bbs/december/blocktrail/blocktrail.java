package bbs.december.blocktrail;

import bbs.december.blocktrail.test.TestCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(blocktrail.MOD_ID)
public class blocktrail
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "blocktrail";

    public blocktrail() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::serverStartingEvent);
    }


    private void serverStartingEvent(FMLServerStartingEvent e) {
        TestCommand.register(e.getCommandDispatcher());
    }

    private void serverSetup(final FMLCommonSetupEvent e) {
        LOGGER.info(MOD_ID + " setup");
    }

    private void clientSetup(final FMLClientSetupEvent e) {
        LOGGER.info(MOD_ID + " setup");
    }

    private void setup(final FMLDedicatedServerSetupEvent e) {
        LOGGER.info(MOD_ID + " setup");
    }
}
