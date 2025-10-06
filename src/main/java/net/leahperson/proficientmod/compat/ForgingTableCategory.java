package net.leahperson.proficientmod.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.leahperson.proficientmod.ProficientMod;
import net.leahperson.proficientmod.block.ModBlocks;
import net.leahperson.proficientmod.recipe.ForgingTableRecipe;
import net.leahperson.proficientmod.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class ForgingTableCategory implements IRecipeCategory<ForgingTableRecipe> {
    public static final ResourceLocation UID =  ResourceLocation.fromNamespaceAndPath(ProficientMod.MOD_ID, "forging_table");

    public static final RecipeType<ForgingTableRecipe> FORGING_TABLE_TYPE =
            new RecipeType<>(UID, ForgingTableRecipe.class);


    private final IDrawable background;
    private final IDrawable icon;

    private static final int DISTANCE_INPUT_X = 18;
    private static final int DISTANCE_INPUT_Y = 16;

    public ForgingTableCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(ProficientMod.MOD_ID,"textures/gui/forging_table_gui.png"), 0, 0, 246, 165);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FORGING_TABLE.get()));;
    }

    @Override
    public RecipeType<ForgingTableRecipe> getRecipeType() {
        return FORGING_TABLE_TYPE;
    }

    @Override
    public int getWidth() {
        return 180;
    }

    @Override
    public int getHeight() {
        return 130;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.qualitycrafting.forging_table");
    }



    @SuppressWarnings("removal")
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ForgingTableRecipe recipe, IFocusGroup focuses) {
        for(int i = 0; i < recipe.getIngredients().size(); i++){
            IRecipeSlotBuilder slotBuilder = builder
                    .addSlot(RecipeIngredientRole.INPUT, 5+((i%3)* DISTANCE_INPUT_X), (int) (35+(Math.floor(i/3)* DISTANCE_INPUT_Y)))
                    .addRichTooltipCallback(new QualityInputTooltip(recipe));

            slotBuilder.addIngredients(recipe.getIngredients().get(i));

            //builder.addSlot().addIngredients(recipe.getIngredients().get(i));
        }

        //============





            //=========

        builder.addSlot(RecipeIngredientRole.CATALYST,90,52).addItemStack(new ItemStack(ModBlocks.FORGING_TABLE.get()));

        builder.addSlot(RecipeIngredientRole.CATALYST,90,12).addIngredients(Ingredient.of(ModTags.Items.FORGING_HAMMER));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 139, 52).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(ForgingTableRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        //IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        //guiGraphics

        //guiGraphics.blit(0,0,0,180,130);


        //Xp color: 128 252 32


        /*if(mouseX >= 10 && mouseX <= 60 && mouseY >= 25 && mouseY <= 33){
            guiGraphics.renderTooltip(Minecraft.getInstance().font,Component.literal("Some Text"),(int)mouseX,(int)mouseY);
        }
        guiGraphics.drawString(Minecraft.getInstance().font,"Quality + ?",10,25,63*256*256+252*256+252,false);
        */

        guiGraphics.drawString(Minecraft.getInstance().font,"Level Cost: 10",5,20,128*256*256+252*256+32,true);

        guiGraphics.drawString(Minecraft.getInstance().font,"Proficiency required: 10",10,90,63*256*256+63*256+63,false);
        guiGraphics.drawString(Minecraft.getInstance().font,"Quality required: 15",10,100,63*256*256+63*256+63,false);
        guiGraphics.drawString(Minecraft.getInstance().font,"+1 Output per 20 Yield",10,110,63*256*256+63*256+63,false);

        //guiGraphics.drawString(Minecraft.getInstance().font,"+",100,100,255*256*256+63*256+63,false);


    }
}
