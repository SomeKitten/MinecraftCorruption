package space.cutekitten.corruption.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.EntityList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.cutekitten.corruption.client.ClientDB;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Shadow @Final
    List<ServerPlayerEntity> players;

    @Shadow @Final
    EntityList entityList;
    private static final Random random = new Random(Instant.now().toEpochMilli());

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerWorld world = (ServerWorld) (Object) this;
        for (ServerPlayerEntity player : players) {
            if (random.nextInt(ClientDB.sliderMax) <  ClientDB.randomizeBlocks) {
                randomizeBlock(world, player);
            }
        }

        convertEntities(world);
    }

    public void randomizeBlock(ServerWorld world, ServerPlayerEntity player) {
        int blockAmount = Registry.BLOCK.size();

//        random block position
        int bx = random.nextInt(-8, 8 + 1);
        int by = random.nextInt(-8, 8 + 1);
        int bz = random.nextInt(-8, 8 + 1);

//            TODO: allow random non-default states to be able to be placed
        world.setBlockState(player.getBlockPos().add(bx, by, bz), Registry.BLOCK.get(random.nextInt(blockAmount)).getDefaultState());
    }

    public void randomizePlayerPos(ServerPlayerEntity player) {
//        random player position
        float px = random.nextFloat(-1, 1);
        float py = random.nextFloat(-1, 1);
        float pz = random.nextFloat(-1, 1);

        player.teleport(player.getX() + px, player.getY() + py, player.getZ() + pz);
    }

    private void convertEntities(ServerWorld world) {
        int entityAmount = Registry.ENTITY_TYPE.size();
        entityList.forEach(entity -> {
            if (!(entity instanceof ServerPlayerEntity) && random.nextInt(ClientDB.sliderMax) < ClientDB.convertEntities) {
//                turn this entity into another entity
                entity.remove(Entity.RemovalReason.DISCARDED);
                Registry.ENTITY_TYPE.get(random.nextInt(entityAmount)).spawn(
                        world,
                        null,
                        null,
                        null,
                        entity.getBlockPos(),
                        SpawnReason.EVENT,
                        false,
                        false
                );
            }
        });
    }
}
