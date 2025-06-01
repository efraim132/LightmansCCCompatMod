package en.efraimg.lccccompat.peripheral.items;

import en.efraimg.lccccompat.LCCCCompat;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LCCCCompat.MODID);

    public static final RegistryObject<Item> CARD_SCANNER = ITEMS.register("card_scanner", () -> new CardScannerItem(new Item.Properties().stacksTo(16)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
