package en.efraimg.lccccompat.peripheral.blocks;

import en.efraimg.lccccompat.LCCCCompat;
import en.efraimg.lccccompat.peripheral.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    // Create a DeferredRegister for Blocks (Only one per mod)
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LCCCCompat.MODID);

    // Register the Card Scanner Block
    public static final RegistryObject<Block> CARD_SCANNER_BLOCK = registerBlock("card_scanner",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)));;


    // Register the Blocks Helper Method
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // Register the block item so that it can be browsed and inventoried in the game
    //TODO Figure out how to register the block item to the card scanner item to be the same item
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    /**
     * Registers all blocks in the mod to the event bus.
     *
     * @param eventBus The event bus to register the blocks to.
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
