package com.intezya.harderthanhard;

import com.intezya.harderthanhard.spawn.NetherSpawnTuner;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarderThanHard implements ModInitializer {
    public static final Logger logger = LoggerFactory.getLogger(HarderThanHard.class);

    @Override
    public void onInitialize() {
        logger.info("[HarderThanHard] Mixins initialized");

        NetherSpawnTuner.tune();

        logger.info("[HarderThanHard] Mobs spawn tuned");
    }
}
