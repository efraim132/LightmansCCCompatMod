package en.efraimg.lccccompat;

import com.grebnev.lccccompat.peripheral.TerminalPeripheralProvider;
import dan200.computercraft.api.peripheral.PeripheralLookup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod(LCCCCompat.MODID)
public class LCCCCompat {
    public static final String MODID = "lccccompat";

    public LCCCCompat() {
        // Common setup is subscribed via annotation below
    }

    @EventBusSubscriber(modid = MODID, bus = Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            PeripheralLookup.get().registerForBlockEntity(
                    com.lightmanscurrency.common.blockentity.TerminalBlockEntity.class,
                    (level, pos, state, blockEntity, side) -> new com.grebnev.lccccompat.peripheral.TerminalPeripheral(blockEntity)
            );
        }
    }
}
