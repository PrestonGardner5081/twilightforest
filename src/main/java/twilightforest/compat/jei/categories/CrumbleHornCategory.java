package twilightforest.compat.jei.categories;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import twilightforest.TwilightForestMod;
import twilightforest.compat.RecipeViewerConstants;
import twilightforest.compat.jei.FakeItemEntity;
import twilightforest.compat.jei.JEICompat;
import twilightforest.compat.jei.renderers.FakeItemEntityRenderer;
import twilightforest.compat.jei.util.CrumbleRecipe;
import twilightforest.init.TFItems;

public class CrumbleHornCategory implements IRecipeCategory<CrumbleRecipe> {

	public static final RecipeType<CrumbleRecipe> CRUMBLE_HORN = RecipeType.create(TwilightForestMod.ID, "crumble_horn", CrumbleRecipe.class);
	private final IDrawable background;
	private final IDrawable arrow;
	private final IDrawable icon;
	private final Component localizedName;

	private final FakeItemEntityRenderer itemRenderer = new FakeItemEntityRenderer(32);

	public CrumbleHornCategory(IGuiHelper helper) {
		this.background = helper.createBlankDrawable(RecipeViewerConstants.GENERIC_RECIPE_WIDTH, RecipeViewerConstants.GENERIC_RECIPE_HEIGHT);
		this.arrow = helper.drawableBuilder(ResourceLocation.fromNamespaceAndPath("jei", "textures/jei/atlas/gui/recipe_arrow.png"), 0, 0, 22, 15).setTextureSize(22, 15).build();
		this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, TFItems.CRUMBLE_HORN.get().getDefaultInstance());
		this.localizedName = Component.translatable("gui.twilightforest.crumble_horn_jei");
	}

	@Override
	public RecipeType<CrumbleRecipe> getRecipeType() {
		return CRUMBLE_HORN;
	}

	@Override
	public Component getTitle() {
		return this.localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public int getWidth() {
		return RecipeViewerConstants.GENERIC_RECIPE_WIDTH;
	}

	@Override
	public int getHeight() {
		return RecipeViewerConstants.GENERIC_RECIPE_HEIGHT;
	}

	@Override
	public void draw(CrumbleRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY) {
		this.arrow.draw(graphics, 44, 18);
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, CrumbleRecipe recipe, IFocusGroup focuses) {
		builder.addSlot(RecipeIngredientRole.INPUT, 19, 19).addItemStack(new ItemStack(recipe.input().asItem()));

		if (recipe.output() != Blocks.AIR) {
			builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 19).addItemStack(new ItemStack(recipe.output().asItem()));
		} else {
			builder.addSlot(RecipeIngredientRole.OUTPUT, 75, 12)
				.setCustomRenderer(JEICompat.FAKE_ITEM_ENTITY, this.itemRenderer)
				.addIngredient(JEICompat.FAKE_ITEM_ENTITY, new FakeItemEntity(new ItemStack(recipe.input().asItem())));
		}
	}
}
