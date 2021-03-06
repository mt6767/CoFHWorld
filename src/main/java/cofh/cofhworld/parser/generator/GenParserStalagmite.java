package cofh.cofhworld.parser.generator;

import cofh.cofhworld.parser.generator.base.AbstractGenParserBlock;
import cofh.cofhworld.parser.variables.BlockData;
import cofh.cofhworld.util.random.WeightedBlock;
import cofh.cofhworld.world.generator.WorldGenStalactite;
import cofh.cofhworld.world.generator.WorldGenStalagmite;
import com.typesafe.config.Config;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class GenParserStalagmite extends AbstractGenParserBlock {

	private final boolean stalactite;

	public GenParserStalagmite(boolean stalactite) {

		this.stalactite = stalactite;
	}

	@Override
	@Nonnull
	public WorldGenerator parseGenerator(String generatorName, Config genObject, Logger log, List<WeightedBlock> resList, List<WeightedBlock> matList) {

		// TODO: these names need revised
		ArrayList<WeightedBlock> list = new ArrayList<>();
		if (!genObject.hasPath("gen-body")) {
			log.info("Entry does not specify gen body for 'stalagmite' generator. Using air.");
			list.add(new WeightedBlock(Blocks.AIR));
		} else {
			if (!BlockData.parseBlockList(genObject.getValue("gen-body"), list, false)) {
				log.warn("Entry specifies invalid gen body for 'stalagmite' generator! Using air!");
				list.clear();
				list.add(new WeightedBlock(Blocks.AIR));
			}
		}
		WorldGenStalagmite r = stalactite ? new WorldGenStalactite(resList, matList, list) : new WorldGenStalagmite(resList, matList, list);
		{
			if (genObject.hasPath("min-height")) {
				r.minHeight = genObject.getInt("min-height");
			}
			if (genObject.hasPath("height-variance")) {
				r.heightVariance = genObject.getInt("height-variance");
			}
			if (genObject.hasPath("size-variance")) {
				r.sizeVariance = genObject.getInt("size-variance");
			}
			if (genObject.hasPath("height-mod")) {
				r.heightMod = genObject.getInt("height-mod");
			}
			if (genObject.hasPath("gen-size")) {
				r.genSize = genObject.getInt("gen-size");
			}
			if (genObject.hasPath("smooth")) {
				r.smooth = genObject.getBoolean("smooth");
			}
			if (genObject.hasPath("fat")) {
				r.fat = genObject.getBoolean("fat");
			}
			if (genObject.hasPath("alt-sinc")) {
				r.altSinc = genObject.getBoolean("alt-sinc");
			}
		}
		return r;
	}

}
