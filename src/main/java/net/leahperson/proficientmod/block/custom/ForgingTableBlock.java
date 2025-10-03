package net.leahperson.proficientmod.block.custom;

import net.leahperson.proficientmod.block.entity.ForgingTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ForgingTableBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,16,16);
    private static final Logger log = LoggerFactory.getLogger(ForgingTableBlock.class);

    public ForgingTableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ForgingTableBlockEntity(pPos,pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ForgingTableBlockEntity) {
                ((ForgingTableBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        /*if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof ForgingTableBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (GemPolishingStationBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }*/

        BlockEntity forgingTableEntity = pLevel.getBlockEntity(pPos);
        if (forgingTableEntity instanceof ForgingTableBlockEntity forgingTableBlockEntity) {
            pLevel.playSound(pPlayer, pPos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS,1f, 1f);

        }
            //Optional<Vec2> optional = getRelativeHitCoordinatesForBlockFace(pHit, pState.getValue(HorizontalDirectionalBlock.FACING));
            /*if (optional.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                int i = getHitSlot(optional.get());
                if (pState.getValue(SLOT_OCCUPIED_PROPERTIES.get(i))) {
                    removeBook(pLevel, pPos, pPlayer, chiseledbookshelfblockentity, i);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide);
                } else {
                    ItemStack itemstack = pPlayer.getItemInHand(pHand);
                    if (itemstack.is(ItemTags.BOOKSHELF_BOOKS)) {
                        addBook(pLevel, pPos, pPlayer, chiseledbookshelfblockentity, itemstack, i);
                        return InteractionResult.sidedSuccess(pLevel.isClientSide);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
            }
        } else {
            return InteractionResult.PASS;
        }*/


        return InteractionResult.SUCCESS;
    }

    public void addItem(Level pLevel, BlockPos pPos, Player pPlayer, ChiseledBookShelfBlockEntity pBlockEntity, ItemStack pItemStack){

    }

    public Item removeItem(){
        return null;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return super.getTicker(pLevel, pState, pBlockEntityType);
    }
}
