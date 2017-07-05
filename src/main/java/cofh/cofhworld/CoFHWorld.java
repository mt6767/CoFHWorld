package cofh.cofhworld;

import cofh.cofhworld.init.FeatureParser;
import cofh.cofhworld.init.WorldHandler;
import cofh.cofhworld.init.WorldProps;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod (modid = CoFHWorld.MOD_ID, name = CoFHWorld.MOD_NAME, version = CoFHWorld.VERSION, dependencies = CoFHWorld.DEPENDENCIES, updateJSON = CoFHWorld.UPDATE_URL)
public class CoFHWorld {

	public static final String MOD_ID = "cofhworld";
	public static final String MOD_NAME = "CoFH World";

	public static final String VERSION = "1.0.0";
	public static final String VERSION_MAX = "1.1.0";
	public static final String VERSION_GROUP = "required-after:" + MOD_ID + "@[" + VERSION + "," + VERSION_MAX + ");";
	public static final String UPDATE_URL = "https://raw.github.com/cofh/version/master/" + MOD_ID + "_update.json";

	public static final String DEPENDENCIES = "required-after:forge@[" + "14.21.1.2388,14.22.0.0" + ");";

	@Instance (MOD_ID)
	public static CoFHWorld instance;

	public static Logger log = LogManager.getLogger(MOD_ID);
	public static Configuration config;

	public CoFHWorld() {

		super();

		WorldHandler.register();
	}

	/* INIT */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		WorldProps.configDir = event.getModConfigurationDirectory();

		config = new Configuration(new File(WorldProps.configDir, "/cofh/world/config.cfg"), VERSION, true);
		config.load();

		WorldProps.preInit();

		WorldHandler.initialize();
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		try {
			FeatureParser.parseGenerationFiles();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		config.save();

		log.info(MOD_NAME + ": Load Complete.");
	}

}