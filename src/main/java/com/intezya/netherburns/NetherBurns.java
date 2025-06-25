package com.intezya.netherburns;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetherBurns implements ModInitializer {
    public static final Logger logger = LoggerFactory.getLogger(NetherBurns.class);

    @Override
    public void onInitialize() {
        logger.info("[NetherBurns] Mixins initialized");
    }
}
