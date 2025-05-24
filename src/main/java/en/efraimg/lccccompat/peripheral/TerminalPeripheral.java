package en.efraimg.lccccompat.peripheral;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    @LuaFunction(mainThread = true)
    public List<Map<String, Object>> getTrades(ItemTraderBlockEntity trader) {
        List<Map<String, Object>> allTrades = convertJsonToTable(getAllTradersWithTradesAsJson(false));
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
                if (trade instanceof ItemTradeData itemTrade) {
                    var item = itemTrade.getSellItem(0);
                    if (item.isEmpty()) continue; // Skip Air items

                    JsonObject tradeObj = new JsonObject();
                    tradeObj.addProperty("item", item.getHoverName().getString());
                    tradeObj.addProperty("count", item.getCount());
                    tradeObj.addProperty("price", itemTrade.getCost().getText().getString());
                    tradesArray.add(tradeObj);
                } else {
                    JsonObject tradeObj = new JsonObject();
                    tradeObj.addProperty("type", trade.getClass().getSimpleName());
                    tradesArray.add(tradeObj);
                }
            }
            traderObj.add("trades", tradesArray);
            tradersArray.add(traderObj);
        }

        Gson gson = new Gson();
        return gson.toJson(tradersArray);
    }

    private List<Map<String, Object>> convertJsonToTable(String json) {
        List<Map<String, Object>> tradersList = new ArrayList<>();
        JsonArray tradersArray = JsonParser.parseString(json).getAsJsonArray();

        for (JsonElement traderElement : tradersArray) {
            JsonObject traderObj = traderElement.getAsJsonObject();
            Map<String, Object> traderMap = new HashMap<>();
            traderMap.put("name", traderObj.get("name").getAsString());
            traderMap.put("id", traderObj.get("id").getAsString());

            List<Map<String, Object>> tradesList = new ArrayList<>();
            JsonArray tradesArray = traderObj.getAsJsonArray("trades");

            for (JsonElement tradeElement : tradesArray) {
                JsonObject tradeObj = tradeElement.getAsJsonObject();
                Map<String, Object> tradeMap = new HashMap<>();

                if (tradeObj.has("item")) {
                    tradeMap.put("item", tradeObj.get("item").getAsString());
                    tradeMap.put("count", tradeObj.get("count").getAsInt());
                    tradeMap.put("price", tradeObj.get("price").getAsString());
                } else {
                    tradeMap.put("type", tradeObj.get("type").getAsString());
                }

                tradesList.add(tradeMap);
            }

            traderMap.put("trades", tradesList);
            tradersList.add(traderMap);
        }

        return tradersList;
    }



}
