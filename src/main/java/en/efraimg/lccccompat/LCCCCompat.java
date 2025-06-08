package en.efraimg.lccccompat;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.ComputerCraftAPI;
import en.efraimg.lccccompat.peripheral.CardScannerPeripheral;
import en.efraimg.lccccompat.peripheral.TerminalPeripheral;
import en.efraimg.lccccompat.peripheral.blocks.ModBlocks;
import en.efraimg.lccccompat.peripheral.entity.ModBlockEntities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import en.efraimg.lccccompat.peripheral.items.ModItems;
import org.slf4j.Logger;

/**
 * Main mod class for Lightman's Currency ComputerCraft Compatibility Mod.
 * Handles mod initialization, event registration, and integration with ComputerCraft.
 */
@Mod(LCCCCompat.MODID)
public class LCCCCompat {
    /**
     * The mod ID used for registration and resource locations.
     */
    public static final String MODID = "lccccompat";

    /**
     * SLF4J logger for logging mod events and errors.
     */
    public static final Logger LOGGER = LogUtils.getLogger();

    /**
     * Constructs the mod instance, registers items, creative tabs, and event listeners.
     */
    public LCCCCompat() {
        // Common setup is subscribed via annotation below
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register Mod Specific
        // Items
        ModItems.register(modEventBus);

        // Register Creative Tabs
        ModCreativeModTab.register(modEventBus);

        // Register Blocks
        ModBlocks.register(modEventBus);

        // Register Block Entities (part of blocks)
        ModBlockEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        modEventBus.addListener(this::addCreative);
    }

    /**
     * Adds items to creative mode tabs during the BuildCreativeModeTabContentsEvent.
     * @param event The event for building creative mode tab contents.
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
//            event.accept(ModItems.CARD_SCANNER);
//        }
    }

    /**
     * Handles common setup tasks for the mod. Currently empty.
     * @param event The FML common setup event.
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        
    }

    /**
     * Static inner class for handling mod event bus subscriptions.
     */
    @EventBusSubscriber(modid = MODID, bus = Bus.MOD)
    public static class ModEvents {
        /**
         * Registers the TerminalPeripheral as a ComputerCraft generic peripheral source during common setup.
         * @param event The FML common setup event.
         */
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                ComputerCraftAPI.registerGenericSource(new TerminalPeripheral());
                ComputerCraftAPI.registerGenericSource(new CardScannerPeripheral());
            });
        }
    }
}
