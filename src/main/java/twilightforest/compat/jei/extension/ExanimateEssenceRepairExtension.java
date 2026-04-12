package twilightforest.compat.jei.extension;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFItems;
import twilightforest.item.recipe.EssenceRepairRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExanimateEssenceRepairExtension implements ICraftingCategoryExtension<EssenceRepairRecipe> {

	@Override
	public void setRecipe(RecipeHolder<EssenceRepairRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		List<ItemStack> scepters = Arrays.stream(Ingredient.of(ItemTagGenerator.SCEPTERS).getItems()).toList();

		craftingGridHelper.createAndSetOutputs(builder, scepters);

		scepters.forEach(stack -> stack.setDamageValue(stack.getMaxDamage()));
		inputs.add(scepters);
		inputs.add(List.of(TFItems.EXANIMATE_ESSENCE.toStack()));

		craftingGridHelper.createAndSetInputs(builder, inputs, 0, 0);
		builder.setShapeless();
	}
}
