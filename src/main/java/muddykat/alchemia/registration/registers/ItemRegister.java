package muddykat.alchemia.registration.registers;

import muddykat.alchemia.Alchemia;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Alchemia.MODID);

    public static HashMap<String, RegistryObject<Item>> ITEM_REGISTRY = new HashMap<>();

    public static void registerItem(String registry_name,  Supplier<Item> itemSupplier){
        ITEM_REGISTRY.put(registry_name, ITEMS.register(registry_name, itemSupplier));
    }

    public static DeferredRegister<Item> getRegistry() {
        return ITEMS;
    }
}