package twilightforest.compat.jei.extension;

import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import twilightforest.item.recipe.NoTemplateSmithingRecipe;

public class NoTemplateSmithingExtension implements ISmithingCategoryExtension<NoTemplateSmithingRecipe> {

	@Override
	public <T extends IIngredientAcceptor<T>> void setTemplate(NoTemplateSmithingRecipe recipe, T ingredientAcceptor) {
		// No template needed for this recipe
	}

	@Override
	public <T extends IIngredientAcceptor<T>> void setBase(NoTemplateSmithingRecipe recipe, T ingredientAcceptor) {
		ingredientAcceptor.addIngredients(recipe.getBase());
	}

	@Override
	public <T extends IIngredientAcceptor<T>> void setAddition(NoTemplateSmithingRecipe recipe, T ingredientAcceptor) {
		ingredientAcceptor.addIngredients(recipe.getAddition());
	}
}
