package en.efraimg.lccccompat.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.GenericPeripheral;
import en.efraimg.lccccompat.LCCCCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class FurnacePeripheral implements GenericPeripheral {
    @Override
    public String id() {
        return new ResourceLocation(LCCCCompat.MODID, "furnace").toString();
    }

    @LuaFunction(mainThread = true)
    public int getBurnTime(AbstractFurnaceBlockEntity furnace) {
        return furnace.saveWithoutMetadata().getInt("BurnTime");
    }
}
