package net.leahperson.proficientmod.block.entity;

import net.leahperson.proficientmod.item.ModItems;
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
import net.minecraft.world.item.Item;
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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



    public void insertItem(Level pLevel, BlockPos pPos, BlockState pState, ItemStack pItemStack){
        if (!pLevel.isClientSide) {
            for (int i = 0; i < NUM_SLOTS; i++) {
                if (itemHandler.getStackInSlot(i).isEmpty()) {
                    itemHandler.insertItem(i, pItemStack.split(1),false);
                    setChanged(pLevel,pPos,pState);
                    return;
                }
            }
        }
    }

    public void removeLatestItem(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer){
        if (!pLevel.isClientSide) {
            for (int i = 9-1; i >= 0; i--) {
                if (!itemHandler.getStackInSlot(i).isEmpty()) {
                    ItemStack itemstack = itemHandler.getStackInSlot(i).split(1);
                    if (!pPlayer.getInventory().add(itemstack)) {
                        pPlayer.drop(itemstack, false);
                    }
                    setChanged(pLevel,pPos,pState);
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

    public boolean hasRecipe(){

        boolean hasItemHandler = this.itemHandler.getStackInSlot(0).getItem() == Items.RAW_IRON;
        boolean isOnlyItem = this.itemHandler.getStackInSlot(1).isEmpty();

        if(hasItemHandler && isOnlyItem){
            return true;
        }


        //itemHandler.

        return false;
    }

    public boolean attemptCraft(Level pLevel, BlockPos pPos, BlockState pState,Player pPlayer){
        if(hasRecipe()){
            craftItem();
            setChanged(pLevel,pPos,pState);
            return true;
        }else{
            return false;
        }
    }

    public void craftItem(){
        itemHandler.extractItem(0,1,false);
        ItemStack result = new ItemStack(Items.IRON_INGOT,1);
        itemHandler.setStackInSlot(0,new ItemStack(result.getItem(),result.getCount()));

        //itemHandler.insertItem(0,1,new ItemStack(Items.IRON_INGOT))
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
