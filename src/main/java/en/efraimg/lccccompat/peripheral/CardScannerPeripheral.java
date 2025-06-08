package en.efraimg.lccccompat.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import dan200.computercraft.api.peripheral.PeripheralType;
import en.efraimg.lccccompat.LCCCCompat;
import en.efraimg.lccccompat.peripheral.entity.CardScannerBlockEntity;
import io.github.lightman314.lightmanscurrency.common.blockentity.trader.ItemTraderBlockEntity;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;

public class CardScannerPeripheral extends TerminalPeripheral {


    @Override
    public String id() {
        return LCCCCompat.MODID + "CardScanner";
    }


    /**
     * Retrieves a list of all traders and their trades, formatted for Lua consumption.
     * @param cardScanner The trader block entity (not used in this implementation).
     * @return A list of maps representing traders and their trades.
     */
    @LuaFunction(mainThread = true)
    public List<Map<String, Object>> getTrades(CardScannerBlockEntity cardScanner) {
        return convertJsonToTable(getAllTradersWithTradesAsJson());
    }


}
