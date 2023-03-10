package muddykat.alchemia.data.generators.loot;

import muddykat.alchemia.common.blocks.BlockIngredient;
import muddykat.alchemia.registration.registers.BlockRegister;
import muddykat.alchemia.registration.registers.ItemRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

    private final Set<ResourceLocation> generatedTables = new HashSet<>();
    private BiConsumer<ResourceLocation, LootTable.Builder> out;

    private ResourceLocation toTableLoc(ResourceLocation in)
    {
        return new ResourceLocation(in.getNamespace(), "blocks/"+in.getPath());
    }
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> out) {
        this.out = out;

        for (RegistryObject<Block> blocks : BlockRegister.getRegistry().getEntries()) {
            if(blocks.get() instanceof BlockIngredient ingredient) {
                register(blocks, randomAmountItem(ItemRegister.getSeedByIngredient(ingredient.getIngredient()), 1, 4));
            }
        }

    }


    private LootTable.Builder dropProvider(ItemLike in)
    {
        return LootTable.lootTable().withPool(singleItem(in));
    }

    private LootPool.Builder singleItem(ItemLike in)
    {
        return createPoolBuilder()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(in));
    }

    private LootPool.Builder randomAmountItem(ItemLike in, int min, int max)
    {
        return createPoolBuilder()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(in)).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(LimitCount.limitCount(IntRange.lowerBound(0)));
    }

    private LootPool.Builder createPoolBuilder()
    {
        return LootPool.lootPool().when(ExplosionCondition.survivesExplosion());
    }

    private void register(Supplier<? extends Block> b, LootPool.Builder... pools)
    {
        LootTable.Builder builder = LootTable.lootTable();
        for(LootPool.Builder pool : pools)
            builder.withPool(pool);
        register(b, builder);
    }

    private void register(Supplier<? extends Block> b, LootTable.Builder table)
    {
        register(b.get().getRegistryName(), table);
    }

    private void register(ResourceLocation name, LootTable.Builder table)
    {
        ResourceLocation loc = toTableLoc(name);
        if(!generatedTables.add(loc))
            throw new IllegalStateException("Duplicate loot table "+name);
        out.accept(loc, table.setParamSet(LootContextParamSets.BLOCK));
    }
}
