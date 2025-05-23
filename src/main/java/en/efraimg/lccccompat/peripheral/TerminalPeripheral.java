package en.efraimg.lccccompat.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public class TerminalPeripheral implements IPeripheral {
    private final TerminalBlockEntity terminal;

    public TerminalPeripheral(TerminalBlockEntity terminal) {
        this.terminal = terminal;
    }

    @Override
    public @NotNull String getType() {
        return "currency_terminal";
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return other instanceof TerminalPeripheral && ((TerminalPeripheral) other).terminal == terminal;
    }


    @LuaFunction
    public List<Map<String, Object>> getTrades() {
        List<Trade> trades = terminal.getAvailableTrades(); // Hypothetical method
        return trades.stream().map(trade -> {
            Map<String, Object> map = new HashMap<>();
            map.put("item", trade.getItem().getDisplayName().getString());
            map.put("price", trade.getPrice());
            return map;
        }).collect(Collectors.toList());
    }
}

