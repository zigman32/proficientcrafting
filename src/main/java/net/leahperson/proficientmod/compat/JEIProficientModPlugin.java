package net.leahperson.proficientmod.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.leahperson.proficientmod.ProficientMod;
import net.leahperson.proficientmod.recipe.ForgingTableRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;



@JeiPlugin
public class JEIProficientModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ProficientMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ForgingTableCategory(registration.getJeiHelpers().getGuiHelper()));
    }



    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ForgingTableRecipe> forgingRecipes = recipeManager.getAllRecipesFor(ForgingTableRecipe.Type.INSTANCE);
        registration.addRecipes(ForgingTableCategory.FORGING_TABLE_TYPE, forgingRecipes);
    }


}
