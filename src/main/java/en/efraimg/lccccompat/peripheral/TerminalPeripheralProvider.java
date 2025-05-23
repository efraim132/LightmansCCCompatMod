package en.efraimg.lccccompat.peripheral;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TerminalPeripheralProvider implements IPeripheralProvider {
    @Override
    public @Nullable IPeripheral getPeripheral(@NotNull Level world, @NotNull BlockPos pos, @NotNull Direction side) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof TerminalBlockEntity) { //TODO Find the terminal Block Entity in lightman's code once imported
            return new TerminalPeripheral((TerminalBlockEntity) tile);
        }
        return null;
    }
}
