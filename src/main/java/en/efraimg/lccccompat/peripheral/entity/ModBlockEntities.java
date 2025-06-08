package en.efraimg.lccccompat.peripheral.entity;

import en.efraimg.lccccompat.LCCCCompat;
import en.efraimg.lccccompat.peripheral.blocks.ModBlocks;
import en.efraimg.lccccompat.peripheral.entity.CardScannerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LCCCCompat.MODID);

    public static final RegistryObject<BlockEntityType<CardScannerBlockEntity>> CARD_SCANNER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("card_scanner_entity",
                    () -> {
                        return BlockEntityType.Builder.of(CardScannerBlockEntity::new,
                                ModBlocks.CARD_SCANNER_BLOCK.get()).build(null);
                    });

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
