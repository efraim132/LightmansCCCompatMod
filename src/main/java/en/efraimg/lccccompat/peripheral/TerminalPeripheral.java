package en.efraimg.lccccompat.peripheral;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import en.efraimg.lccccompat.LCCCCompat;
import io.github.lightman314.lightmanscurrency.api.traders.TraderAPI;
import io.github.lightman314.lightmanscurrency.api.traders.TraderData;
import io.github.lightman314.lightmanscurrency.api.traders.trade.TradeData;
import io.github.lightman314.lightmanscurrency.common.blockentity.trader.ItemTraderBlockEntity;
import io.github.lightman314.lightmanscurrency.common.traders.item.tradedata.ItemTradeData;
import net.minecraft.network.chat.MutableComponent;

import java.util.*;


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
        String allTrades = getAllTradersWithTradesAsJson(false);
        return allTrades;
    }

    private String getAllTradersWithTradesAsJson(boolean isClient) {
        List<TraderData> traders = TraderAPI.API.GetAllTraders(isClient);
        JsonArray tradersArray = new JsonArray();

        for (TraderData trader : traders) {
            JsonObject traderObj = new JsonObject();
            traderObj.addProperty("name", trader.getName().getString());
            traderObj.addProperty("id", trader.getID());

            JsonArray tradesArray = new JsonArray();
            for (TradeData trade : trader.getTradeData()) {
                JsonObject tradeObj = new JsonObject();
                // Example for ItemTradeData, adjust for your trade types
                if (trade instanceof ItemTradeData itemTrade) {
                    var item = itemTrade.getSellItem(0);
                    tradeObj.addProperty("item", item.getHoverName().getString());
                    tradeObj.addProperty("count", item.getCount());
                    tradeObj.addProperty("price", itemTrade.getCost().getText().getString());
               // } else if (trade instanceof FluidTradeData fluidTrade) {
                    // TODO: Add in logic for fluid trades from addon
                //}
                } else {
                    tradeObj.addProperty("type", trade.getClass().getSimpleName());
                }
                tradesArray.add(tradeObj);
            }
            traderObj.add("trades", tradesArray);
            tradersArray.add(traderObj);
        }

        Gson gson = new Gson();
        return gson.toJson(tradersArray);
    }


}
