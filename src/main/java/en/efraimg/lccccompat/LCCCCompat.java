package en.efraimg.lccccompat;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.ComputerCraftAPI;
import en.efraimg.lccccompat.peripheral.TerminalPeripheral;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.slf4j.Logger;

@Mod(LCCCCompat.MODID)
public class LCCCCompat {
    public static final String MODID = "lccccompat";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public LCCCCompat() {
        // Common setup is subscribed via annotation below
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
