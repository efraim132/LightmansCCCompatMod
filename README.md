
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
````

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

## License

MIT License

## Authors

* EfraimG
* Easease

