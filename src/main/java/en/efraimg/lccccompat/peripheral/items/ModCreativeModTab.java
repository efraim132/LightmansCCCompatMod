package en.efraimg.lccccompat.peripheral.items;

import en.efraimg.lccccompat.LCCCCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LCCCCompat.MODID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_TABS.register("tutorial_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CARD_SCANNER.get()))
                    .title(Component.translatable("Currency Peripherals")) //Creative Tab Title
                    .displayItems((pParameters, pOutput) -> {
                        // Add items to the creative tab here!
                        pOutput.accept(ModItems.CARD_SCANNER.get());

                        // Demo Diamond Item (just for testing purposes)
                        pOutput.accept(Items.DIAMOND);


                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }

}
