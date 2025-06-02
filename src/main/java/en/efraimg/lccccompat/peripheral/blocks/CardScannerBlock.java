package en.efraimg.lccccompat.peripheral.blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CardScannerBlock extends Block {

    // TODO Implement the block facing a specific direction
    // public static final DirectionProperty FACING =


    public CardScannerBlock(Properties properties) {
		super(properties);
    }

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 8, 16, 16);

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }






}
