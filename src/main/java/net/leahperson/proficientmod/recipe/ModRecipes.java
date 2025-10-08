package net.leahperson.proficientmod.recipe;

import net.leahperson.proficientmod.ProficientMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ProficientMod.MOD_ID);


    public static final RegistryObject<RecipeSerializer<ForgingTableRecipe>> FORGING_TABLE_SERIALIZER =
            SERIALIZERS.register("forging_table", () -> ForgingTableRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
