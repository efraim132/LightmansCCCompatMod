package en.efraimg.lccccompat.peripheral.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CardScannerBlockEntity extends BlockEntity implements MenuProvider {
    // As discussed previously — inventory, capability, etc.
    public CardScannerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CARD_SCANNER_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return null;
    }
}
