# Lightman's ComputerCraft Compat (LCCCCompat)

**Minecraft Mod for Forge 1.20.1**

## Overview

Lightman's ComputerCraft Compat is a Minecraft mod that extends ComputerCraft to support Lightman's Currency. It allows in-game computers to interact with currency traders and fetch their data via Lua.

## Features

- Integrates Lightman's Currency trader data into ComputerCraft.
- Provides Lua-accessible functions for:
  - Getting trader names and owner info.
  - Listing trade offers.
  - Accessing trader icons.

## Requirements

- Minecraft: 1.20.1
- Forge: 47.4.0 or higher
- ComputerCraft: 1.113.1+
- Lightman's Currency: 1.20.1-2.2.0.0 to 1.20.1-2.2.5.0

## Build Instructions

```bash
./gradlew build
```

The output JAR will be in `build/libs`.

## Installation

Place the built `.jar` file in the `mods` folder of your Minecraft Forge instance.

## Development

The mod's source is located under:

```
src/main/java/en/efraimg/lccccompat/
```

Main classes:

* `LCCCCompat.java`: Entry point of the mod.
* `TerminalPeripheral.java`: Defines the Lua API for interacting with traders.

## Terminal Peripheral & Lua API

The mod provides a custom ComputerCraft peripheral called the **Terminal Peripheral**. This peripheral exposes trader and trade data to Lua scripts running on ComputerCraft computers.

### How to Use

Attach the terminal peripheral to your ComputerCraft computer. You can then call the following Lua functions:

- `getName(trader)`: Returns the custom name of the trader.
- `getOwnerName(trader)`: Returns the name of the trader's owner.
- `getTrades()`: Returns a table of all trades for the trader. Each trade includes:
  - `items`: List of items being sold or offered (each with `item` and `count`).
  - `transactionType`: One of `sale`, `purchase`, or `barter`.
  - `price`: (For sales/purchases) The price in currency.
  - `itemsCost`: (For barters) List of items required as cost (each with `item` and `count`).

#### Example Lua Usage

```lua
local p = peripheral.wrap("back") -- change to your peripheral side
local traders = p.getTrades()

for _, trader in ipairs(traders) do
    print("Trader:", trader.name, "(ID:", trader.id .. ")")
    if #trader.trades == 0 then
        print("  No trades available.")
    else
        for i, trade in ipairs(trader.trades) do
            print("  Trade " .. i .. ":")
            print("    Type:", trade.transactionType)
            if trade.price then
                print("    Price:", trade.price)
            end
            if trade.items then
                print("    Items:")
                for _, item in ipairs(trade.items) do
                    print("      -", item.count, item.item)
                end
            end
            if trade.itemsCost then
                print("    Cost Items:")
                for _, item in ipairs(trade.itemsCost) do
                    print("      -", item.count, item.item)
                end
            end
        end
    end
    print()
end

```

See the mod source for more details on available fields and usage.

## Supported Peripheral Blocks

The Terminal Peripheral can be attached to the following blocks from Lightman's Currency:

- **Item Trader**
- **Admin Trader**
- **Vending Machine**
- **Any other block that implements the Lightman's Currency trader interface**

To use the peripheral, simply place your ComputerCraft computer adjacent to one of these blocks, or use a wired modem to connect. The peripheral will automatically expose the trader's data to your Lua scripts.

When not placed next to a network trader, the peripheral will throw an error when trying to access the owner and name of the trader.

## License

MIT License

## Authors

* EfraimG
* Easease

