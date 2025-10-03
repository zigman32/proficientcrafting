package net.leahperson.proficientmod.block.entity;

import net.leahperson.proficientmod.ProficientMod;
import net.leahperson.proficientmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ProficientMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<ForgingTableBlockEntity>> FORGING_TABLE_BE =
            BLOCK_ENTITIES.register("forging_table_be", () -> {
                return BlockEntityType.Builder.of(ForgingTableBlockEntity::new,ModBlocks.FORGING_TABLE.get()).build(null);
            });

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
