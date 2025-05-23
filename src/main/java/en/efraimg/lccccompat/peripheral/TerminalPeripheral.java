package en.efraimg.lccccompat.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import en.efraimg.lccccompat.LCCCCompat;
import io.github.lightman314.lightmanscurrency.api.traders.TraderAPI;
import io.github.lightman314.lightmanscurrency.api.traders.TraderData;
import io.github.lightman314.lightmanscurrency.common.blockentity.trader.ItemTraderBlockEntity;
import io.github.lightman314.lightmanscurrency.common.playertrading.PlayerTrade;
import io.github.lightman314.lightmanscurrency.common.playertrading.PlayerTradeManager;

import java.util.List;
import java.util.Map;


public class TerminalPeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return LCCCCompat.MODID + ":terminal";
    }

    @LuaFunction(mainThread = true)
    public String getName(ItemTraderBlockEntity trader) {
        return trader.getTraderData().getCustomName();
    }

    @LuaFunction(mainThread = true)
    public String getOwnerName(ItemTraderBlockEntity trader) {
        return trader.getTraderData().getOwner().getName().toString();
    }

    @LuaFunction(mainThread = true)
    public String getIcon(ItemTraderBlockEntity trader) {
        return trader.getRawTraderData().getIcon().toString();
    }
    @LuaFunction(mainThread = true) //TODO actually setup the trader API to properly return the trader data
    public String getTrades(ItemTraderBlockEntity trader) {
        List<PlayerTrade> trades = PlayerTradeManager.GetAllTrades();
        return trades.toString();
    }
}
