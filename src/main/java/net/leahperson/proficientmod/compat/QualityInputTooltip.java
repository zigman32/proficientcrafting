package net.leahperson.proficientmod.compat;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotRichTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import net.leahperson.proficientmod.recipe.ForgingTableRecipe;
import net.minecraft.network.chat.Component;

public class QualityInputTooltip implements IRecipeSlotRichTooltipCallback {

    public QualityInputTooltip(ForgingTableRecipe recipe) {

    }

    @Override
    public void onRichTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip) {
        tooltip.add(Component.literal("+10 Quality if Uncommon\n+15 Quality if Rare\n+25 Quality if Legendary"));


    }


}
