package net.leahperson.proficientmod.datagen;

import net.leahperson.proficientmod.block.ModBlocks;
import net.leahperson.proficientmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FORGING_TABLE.get())
                .pattern("ACB")
                .pattern(" C ")
                .pattern("CCC")
                .define('A', Items.CRAFTING_TABLE)
                .define('B',Items.SMITHING_TABLE)
                .define('C',Items.IRON_INGOT)
                .unlockedBy(getHasName(ModBlocks.FORGING_TABLE.get()),has(ModBlocks.FORGING_TABLE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CRUDEHAMMER.get())
                .pattern("ABA")
                .pattern(" B ")
                .pattern(" B ")
                .define('A', Items.SMOOTH_STONE)
                .define('B',Items.STICK)
                .unlockedBy(getHasName(ModItems.CRUDEHAMMER.get()),has(ModItems.CRUDEHAMMER.get()))
                .save(pWriter);
    }
}
