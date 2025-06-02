package en.efraimg.lccccompat.peripheral.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CardScannerItem extends Item {



    public CardScannerItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> ptooltip, TooltipFlag isAdvanced) {
        ptooltip.add(Component.translatable("tooltip.lccccompat.card_scanner.tooltip"));
        super.appendHoverText(stack, pLevel, ptooltip, isAdvanced);
    }
}
