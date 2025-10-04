package net.leahperson.proficientmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ForgingTableBlockEntity extends BlockEntity {

    public static final int NUM_SLOTS = 9;


    class OneSlotItemHandler extends ItemStackHandler {
        public OneSlotItemHandler(int numSlots) {
            super(numSlots);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    }

    public final OneSlotItemHandler itemHandler = new OneSlotItemHandler(NUM_SLOTS);


    //public final int slotsOccupied = 0;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public ForgingTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FORGING_TABLE_BE.get(), pPos, pBlockState);



    }

    public boolean isFull(){
        for (int i = 0; i < NUM_SLOTS; i++) {
            if(itemHandler.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty(){
        for (int i = 0; i < NUM_SLOTS; i++) {
            if(!itemHandler.getStackInSlot(i).isEmpty()){
                return false;
            }
        }
        return true;
    }



    public void insertItem(Level pLevel, ItemStack pItemStack){
        if (!pLevel.isClientSide) {
            for (int i = 0; i < NUM_SLOTS; i++) {
                if (itemHandler.getStackInSlot(i).isEmpty()) {
                    itemHandler.insertItem(i, pItemStack.split(1),false);
                    return;
                }
            }
        }
    }

    public void removeLatestItem(Level pLevel, Player pPlayer){
        if (!pLevel.isClientSide) {
            for (int i = 9-1; i >= 0; i--) {
                if (!itemHandler.getStackInSlot(i).isEmpty()) {
                    ItemStack itemstack = itemHandler.getStackInSlot(i).split(1);
                    if (!pPlayer.getInventory().add(itemstack)) {
                        pPlayer.drop(itemstack, false);
                    }
                    return;
                }
            }


        }
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i,itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);

    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory",itemHandler.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public InteractionResult craftItem(Level pLevel, BlockPos pPos, Player pPlayer, ItemStack pItemStack, int pSlot){
        return InteractionResult.SUCCESS;

    }
}
