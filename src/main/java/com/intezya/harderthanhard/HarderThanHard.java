package com.intezya.harderthanhard;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarderThanHard implements ModInitializer {
    public static final Logger logger = LoggerFactory.getLogger(HarderThanHard.class);

    @Override
    public void onInitialize() {
        logger.info("Mixins initialized");
    }
}
