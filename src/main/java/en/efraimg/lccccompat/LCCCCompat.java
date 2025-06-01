package en.efraimg.lccccompat;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.ComputerCraftAPI;
import en.efraimg.lccccompat.peripheral.TerminalPeripheral;
import en.efraimg.lccccompat.peripheral.items.ModCreativeModTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import en.efraimg.lccccompat.peripheral.items.ModItems;
import org.slf4j.Logger;

@Mod(LCCCCompat.MODID)
public class LCCCCompat {
    public static final String MODID = "lccccompat";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public LCCCCompat() {
        // Common setup is subscribed via annotation below
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register Mod Specific
        // Items
        ModItems.register(modEventBus);

        // Register Creative Tabs
        ModCreativeModTab.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.CARD_SCANNER);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        
    }

    @EventBusSubscriber(modid = MODID, bus = Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                ComputerCraftAPI.registerGenericSource(new TerminalPeripheral());
            });
        }
    }
}
