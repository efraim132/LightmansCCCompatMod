package en.efraimg.lccccompat.peripheral;

import com.google.gson.*;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import en.efraimg.lccccompat.LCCCCompat;
import io.github.lightman314.lightmanscurrency.api.traders.TraderAPI;
import io.github.lightman314.lightmanscurrency.api.traders.TraderData;
import io.github.lightman314.lightmanscurrency.api.traders.trade.TradeData;
import io.github.lightman314.lightmanscurrency.common.blockentity.trader.ItemTraderBlockEntity;
import io.github.lightman314.lightmanscurrency.common.traders.item.tradedata.ItemTradeData;
import dan200.computercraft.api.lua.LuaException;

import java.util.*;

/**
 * TerminalPeripheral provides ComputerCraft integration for Lightman's Currency mod traders.
 * It exposes methods to retrieve trader information and trades for use in Lua scripts.
 */
public class TerminalPeripheral implements GenericPeripheral {
    /**
     * Returns the unique peripheral ID for ComputerCraft registration.
     * @return The peripheral ID string.
     */
    @Override
    public String id() {
        return LCCCCompat.MODID + ":terminal";
    }

    /**
     * Gets the display name of the given ItemTraderBlockEntity.
     * @param trader The trader block entity.
     * @return The trader's name as a String.
     * @throws LuaException If the entity is not a trader or name cannot be retrieved.
     */
    @LuaFunction(mainThread = true)
    public String getName(ItemTraderBlockEntity trader) throws LuaException {
        try {
            return trader.getTraderData().getName().getString();
        }catch (Exception e) {
            LCCCCompat.LOGGER.error("Error getting trader name: {}", e.getMessage());
            throw new LuaException("Not a trader, cannot get name", 1);
        }
    }

    /**
     * Gets the display name of the owner of the given ItemTraderBlockEntity.
     * @param trader The trader block entity.
     * @return The owner's name as a String.
     * @throws LuaException If the entity is not a trader or owner name cannot be retrieved.
     */
    @LuaFunction(mainThread = true)
    public String getOwnerName(ItemTraderBlockEntity trader) throws LuaException {
        try{
            return trader.getTraderData().getOwner().getName().getString();
        }catch (Exception e) {
            LCCCCompat.LOGGER.error("Error getting trader owner name: {}", e.getMessage());
            throw new LuaException("Not a trader, cannot get name of owner", 1);
        }

    }

    /**
     * Retrieves a list of all traders and their trades, formatted for Lua consumption.
     * @param trader The trader block entity (not used in this implementation).
     * @return A list of maps representing traders and their trades.
     */
    @LuaFunction(mainThread = true)
    public List<Map<String, Object>> getTrades(ItemTraderBlockEntity trader) {
        return convertJsonToTable(getAllTradersWithTradesAsJson());
    }


    /**
     * Gathers all traders and their trades as a JSON string.
     * @return JSON string representing all traders and their trades.
     */
    public String getAllTradersWithTradesAsJson() {
        List<TraderData> traders = TraderAPI.API.GetAllTraders(false);
        JsonArray tradersArray = new JsonArray();

        for (TraderData trader : traders) {
            JsonObject traderObj = new JsonObject();
            traderObj.addProperty("name", trader.getName().getString());
            traderObj.addProperty("id", trader.getID());

            JsonArray tradesArray = new JsonArray();
            for (TradeData trade : trader.getTradeData()) {
                if (trade instanceof ItemTradeData itemTrade) {
                    JsonArray itemsArray = new JsonArray();
                    // Support up to 2 items (as per mod capability)
                    for (int i = 0; i < 2; i++) {
                        var item = itemTrade.getSellItem(i);
                        if (item.isEmpty()) continue; // Skip Air items
                        JsonObject itemObj = new JsonObject();
                        itemObj.addProperty("item", item.getHoverName().getString());
                        itemObj.addProperty("count", item.getCount());
                        itemsArray.add(itemObj);
                    }
                    if (itemsArray.isEmpty()) continue; // Skip trades with no items

                    JsonObject tradeObj = new JsonObject();
                    tradeObj.add("items", itemsArray);

                    // Add transaction type and cost
                    String transactionType = "barter";
                    if(itemTrade.isSale()) {
                        transactionType = "sale";
                        tradeObj.addProperty("price", itemTrade.getCost().getText().getString());
                    } else if(itemTrade.isPurchase()) {
                        transactionType = "purchase";
                        tradeObj.addProperty("price", itemTrade.getCost().getText().getString());
                    } else if (itemTrade.isBarter()) {
                        transactionType = "barter";
                        // Add itemsCost array for barter (2 item slots)
                        JsonArray itemsCostArray = new JsonArray();
                        for (int i = 0; i < 2; i++) {
                            var costItem = itemTrade.getBarterItem(i);
                            if (costItem.isEmpty()) continue;
                            JsonObject costObj = new JsonObject();
                            costObj.addProperty("item", costItem.getHoverName().getString());
                            costObj.addProperty("count", costItem.getCount());
                            itemsCostArray.add(costObj);
                        }
                        tradeObj.add("itemsCost", itemsCostArray);
                    }
                    tradeObj.addProperty("transactionType", transactionType);
                    tradesArray.add(tradeObj);
                } else {
                    JsonObject tradeObj = new JsonObject();
                    tradeObj.addProperty("type", trade.getClass().getSimpleName());
                    tradeObj.addProperty("transactionType", "unknown");
                    tradesArray.add(tradeObj);
                }
            }
            traderObj.add("trades", tradesArray);
            tradersArray.add(traderObj);
        }

        Gson gson = new Gson();
        return gson.toJson(tradersArray);
    }

    /**
     * Converts a JSON string of traders and trades into a list of maps for Lua.
     * @param json The JSON string to convert.
     * @return A list of maps representing traders and their trades.
     */
    public List<Map<String, Object>> convertJsonToTable(String json) {
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

                if (tradeObj.has("items")) {
                    List<Map<String, Object>> itemsList = new ArrayList<>();
                    JsonArray itemsArray = tradeObj.getAsJsonArray("items");
                    mapItems(itemsList, itemsArray);
                    tradeMap.put("items", itemsList);
                    if (tradeObj.has("price")) {
                        tradeMap.put("price", tradeObj.get("price").getAsString());
                    }
                    if (tradeObj.has("itemsCost")) {
                        List<Map<String, Object>> itemsCostList = new ArrayList<>();
                        JsonArray itemsCostArray = tradeObj.getAsJsonArray("itemsCost");
                        mapItems(itemsCostList, itemsCostArray);
                        tradeMap.put("itemsCost", itemsCostList);
                    }
                } else {
                    tradeMap.put("type", tradeObj.get("type").getAsString());
                }

                tradeMap.put("transactionType", tradeObj.get("transactionType").getAsString());
                tradesList.add(tradeMap);
            }

            traderMap.put("trades", tradesList);
            tradersList.add(traderMap);
        }

        return tradersList;
    }

    /**
     * Helper method to map item JSON arrays to Java lists for Lua.
     * @param itemsList The list to populate.
     * @param itemsArray The JSON array of items.
     */
    public void mapItems(List<Map<String, Object>> itemsList, JsonArray itemsArray) {
        for (JsonElement itemElement : itemsArray) {
            JsonObject itemObj = itemElement.getAsJsonObject();
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("item", itemObj.get("item").getAsString());
            itemMap.put("count", itemObj.get("count").getAsInt());
            itemsList.add(itemMap);
        }
    }
}
