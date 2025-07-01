package com.intezya.harderthanhard.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    private void onPlayerConnect(
            ClientConnection connection,
            ServerPlayerEntity player,
            ConnectedClientData clientData,
            CallbackInfo ci
    ) {
        if (player.getWorld().getRegistryKey() == World.NETHER) {
            player.setOnFireFor(Integer.MAX_VALUE);
        }
    }
}
