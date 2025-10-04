package net.leahperson.proficientmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class ForgingTableBlockEntity extends BlockEntity {

    public static final int NUM_SLOTS = 9;

    public final ItemStackHandler itemHandler = new ItemStackHandler(NUM_SLOTS);
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

    public void insertItem(ItemStack itemStack){
        for (int i = 0; i < NUM_SLOTS; i++) {
            if(itemHandler.getStackInSlot(i).isEmpty()){
                var newStack = itemStack.split(1);
                itemHandler.insertItem(i,newStack,false);
                return;
            }
        }
    }

    public ItemStack removeLatestItem(){
        for (int i = NUM_SLOTS-1; i >= 0; i--) {
            if(!itemHandler.getStackInSlot(i).isEmpty()){



                return itemHandler.extractItem(i,64,false);
            }
        }
        return itemHandler.extractItem(0,64,false);
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
