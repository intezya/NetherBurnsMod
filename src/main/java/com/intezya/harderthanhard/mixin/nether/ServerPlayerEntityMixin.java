package com.intezya.harderthanhard.mixin.nether;

import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @Inject(method = "worldChanged", at = @At("TAIL"))
    private void onWorldChange(ServerWorld origin, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        final RegistryKey<World> originRegistryKey = origin.getRegistryKey();
        final RegistryKey<World> playerRegistryKey = player.getWorld().getRegistryKey();

        if (playerRegistryKey == World.NETHER) {
            player.setOnFireFor(Integer.MAX_VALUE);
        } else if (originRegistryKey == World.NETHER) {
            if (player.isOnFire()) {
                player.extinguish();
            }

            player.setOnFireFor(5);
        }
    }
}
