package en.efraimg.lccccompat.peripheral.items;

import en.efraimg.lccccompat.LCCCCompat;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * ModItems class is responsible for registering all custom items in the mod.
 * It uses a DeferredRegister to handle item registration to the Forge item registry.
 */
public class ModItems {
    /**
     * Deferred register for all custom items in the mod.
     * Registers items to the Forge item registry using the mod ID.
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LCCCCompat.MODID);



    // Register Items Here

    //This is a placeholder for the card Scanner Item
    /**
     * Registry object for the Card Scanner item.
     * This item is used to scan cards in the game.
     */
    //public static final RegistryObject<Item> CARD_SCANNER = ITEMS.register("card_scanner", () -> new CardScannerItem(new Item.Properties().stacksTo(16)));


    /**
     * Registers all items in the mod to the event bus.
     * This method should be called during the mod's initialization phase.
     *
     * @param eventBus The event bus to register the items to.
     */
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
