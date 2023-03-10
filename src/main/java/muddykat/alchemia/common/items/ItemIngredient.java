package muddykat.alchemia.common.items;

import muddykat.alchemia.Alchemia;
import muddykat.alchemia.common.items.helper.IngredientAlignment;
import muddykat.alchemia.common.items.helper.IngredientType;
import muddykat.alchemia.common.items.helper.Ingredients;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ItemIngredient extends Item {

    private final Ingredients ingredient;
    private final IngredientType ingredientType;
    private final IngredientAlignment primaryAlignment;
    private IngredientAlignment secondaryAlignment;
    public ItemIngredient(Ingredients ingredient, IngredientType type, IngredientAlignment alignment) {
        super(new Properties().tab(Alchemia.ITEM_GROUP).food(ingredient.getFoodProperties()));
        this.ingredientType = type;
        this.primaryAlignment = alignment;
        this.ingredient = ingredient;
    }

    public ItemIngredient(Ingredients ingredient, IngredientType type, IngredientAlignment primaryAlignment, IngredientAlignment secondaryAlignment){
        this(ingredient, type, primaryAlignment);
        this.secondaryAlignment = secondaryAlignment;
    }

    public IngredientType getIngredientType() {
        return this.ingredientType;
    }

    public IngredientAlignment getPrimaryAlignment(){
        return this.primaryAlignment;
    }

    public IngredientAlignment getSecondaryAlignment() throws NullPointerException {
        return this.secondaryAlignment;
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(Alchemia.MODID, "item/" + this.ingredientType.name().toLowerCase() + "s/" + ingredient.name().toLowerCase());
    }

    public Ingredients getIngredient() {
        return ingredient;
    }
}
