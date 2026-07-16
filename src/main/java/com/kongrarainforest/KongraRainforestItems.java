package com.kongrarainforest;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class KongraRainforestItems {

    public static final FoodComponent RAINFOREST_BERRY_FOOD = new FoodComponent.Builder()
            .hunger(4).saturationModifier(0.6f).alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1.0f)
            .build();

    public static Item KONGRA_SCALE;
    public static Item EMERALD_FANG;
    public static Item JUNGLE_HEART;
    public static Item RAINFOREST_BERRY;

    public static ArmorItem KONGRA_HELMET;
    public static ArmorItem KONGRA_CHESTPLATE;
    public static ArmorItem KONGRA_LEGGINGS;
    public static ArmorItem KONGRA_BOOTS;

    public static SpawnEggItem KONGRA_SPAWN_EGG;
    public static SpawnEggItem TOUCAN_SPAWN_EGG;
    public static SpawnEggItem JAGUAR_SPAWN_EGG;

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM,
                new Identifier(KongraRainforestMod.MOD_ID, name), item);
    }

    public static void registerItems() {
        KONGRA_SCALE = register("kongra_scale",
                new Item(new Item.Settings()));
        EMERALD_FANG = register("emerald_fang",
                new Item(new Item.Settings()));
        JUNGLE_HEART = register("jungle_heart",
                new Item(new Item.Settings()));
        RAINFOREST_BERRY = register("rainforest_berry",
                new Item(new Item.Settings().food(RAINFOREST_BERRY_FOOD)));

        KONGRA_HELMET = (ArmorItem) register("kongra_helmet",
                new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.HELMET,
                        new Item.Settings()));
        KONGRA_CHESTPLATE = (ArmorItem) register("kongra_chestplate",
                new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.CHESTPLATE,
                        new Item.Settings()));
        KONGRA_LEGGINGS = (ArmorItem) register("kongra_leggings",
                new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.LEGGINGS,
                        new Item.Settings()));
        KONGRA_BOOTS = (ArmorItem) register("kongra_boots",
                new KongraArmorItem(KongraArmorMaterial.INSTANCE, ArmorItem.Type.BOOTS,
                        new Item.Settings()));

        KONGRA_SPAWN_EGG = (SpawnEggItem) register("kongra_spawn_egg",
                new SpawnEggItem(KongraRainforestEntities.KONGRA, 0x1A1A1A, 0x39FF14,
                        new Item.Settings()));
        TOUCAN_SPAWN_EGG = (SpawnEggItem) register("toucan_spawn_egg",
                new SpawnEggItem(KongraRainforestEntities.TOUCAN, 0x222222, 0xFF8C00,
                        new Item.Settings()));
        JAGUAR_SPAWN_EGG = (SpawnEggItem) register("jaguar_spawn_egg",
                new SpawnEggItem(KongraRainforestEntities.JAGUAR, 0xC08A2E, 0x3B2A17,
                        new Item.Settings()));

        // Add all items to the mod's creative tab.
        ItemGroupEvents.modifyEntriesEvent(KongraRainforestMod.KONGRA_GROUP_KEY).register(entries -> {
            entries.add(KONGRA_SCALE);
            entries.add(EMERALD_FANG);
            entries.add(JUNGLE_HEART);
            entries.add(RAINFOREST_BERRY);
            entries.add(KONGRA_HELMET);
            entries.add(KONGRA_CHESTPLATE);
            entries.add(KONGRA_LEGGINGS);
            entries.add(KONGRA_BOOTS);
            entries.add(KongraRainforestBlocks.JUNGLE_HEART_BLOCK);
            entries.add(KONGRA_SPAWN_EGG);
            entries.add(TOUCAN_SPAWN_EGG);
            entries.add(JAGUAR_SPAWN_EGG);
        });
    }
}