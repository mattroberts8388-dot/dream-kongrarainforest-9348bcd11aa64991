package com.kongrarainforest;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class KongraRainforestBlocks {
    public static Block JUNGLE_HEART_BLOCK;

    public static void registerBlocks() {
        JUNGLE_HEART_BLOCK = register("jungle_heart_block",
                new Block(AbstractBlock.Settings.copy(Blocks.EMERALD_BLOCK)
                        .strength(6.0f, 12.0f)
                        .sounds(BlockSoundGroup.STONE)));
    }

    private static Block register(String name, Block block) {
        Block registered = Registry.register(Registries.BLOCK,
                new Identifier(KongraRainforestMod.MOD_ID, name), block);
        Registry.register(Registries.ITEM,
                new Identifier(KongraRainforestMod.MOD_ID, name),
                new BlockItem(registered, new Item.Settings()));
        return registered;
    }
}