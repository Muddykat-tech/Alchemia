package muddykat.alchemia.common.items;

import com.mojang.realmsclient.util.TextRenderingUtils;
import muddykat.alchemia.Alchemia;
import muddykat.alchemia.common.items.helper.IngredientAlignment;
import muddykat.alchemia.common.items.helper.IngredientType;
import muddykat.alchemia.common.items.helper.Ingredients;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class ItemIngredientSeed extends BlockItem {

    private final Ingredients ingredient;
    private final IngredientType ingredientType;
    private final IngredientAlignment primaryAlignment;
    private IngredientAlignment secondaryAlignment;
    public ItemIngredientSeed(Block block, Ingredients ingredient, IngredientType type, IngredientAlignment alignment) {
        super(block, new Properties().tab(Alchemia.ITEM_GROUP));
        this.ingredientType = type;
        this.primaryAlignment = alignment;
        this.ingredient = ingredient;
    }

    public ItemIngredientSeed(Block block, Ingredients ingredient, IngredientType type, IngredientAlignment primaryAlignment, IngredientAlignment secondaryAlignment){
        this(block, ingredient, type, primaryAlignment);
        this.secondaryAlignment = secondaryAlignment;
    }

    @Override
    public Component getName(ItemStack pStack) {

        return new TranslatableComponent("item.alchemia." + ingredientType.name().toLowerCase(), I18n.get("item.alchemia." + ingredient.getRegistryName()));
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
        return new ResourceLocation(Alchemia.MODID, "item/" + this.ingredientType.name().toLowerCase() + "s/" + ingredient.name().toLowerCase() + "_seed");
    }

    public Ingredients getIngredient() {
        return ingredient;
    }
}
