package com.kongrarainforest;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KongraRainforestMod implements ModInitializer {
    public static final String MOD_ID = "kongrarainforest";

    public static final ItemGroup KONGRA_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(MOD_ID, "main"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.kongrarainforest.main"))
                    .icon(() -> new ItemStack(KongraRainforestItems.KONGRA_CHESTPLATE))
                    .build());

    // Track rain exposure per player for the "out in the rain too long" mechanic.
    private static final Map<UUID, Integer> RAIN_EXPOSURE = new HashMap<>();
    private static final int RAIN_GRACE_TICKS = 200;   // ~10 seconds before damage starts
    private static final int RAIN_DAMAGE_INTERVAL = 40; // damage tick cadence once past grace

    @Override
    public void onInitialize() {
        KongraRainforestItems.registerItems();
        KongraRainforestBlocks.registerBlocks();
        KongraRainforestEntities.registerEntities();
        KongraRainforestEntities.registerAttributes();

        // Add jungle animal & boss spawns to existing jungle biomes.
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
                SpawnGroup.CREATURE, KongraRainforestEntities.TOUCAN, 12, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
                SpawnGroup.CREATURE, KongraRainforestEntities.JAGUAR, 8, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(ConventionalBiomeTags.JUNGLE),
                SpawnGroup.MONSTER, KongraRainforestEntities.KONGRA, 2, 1, 1);

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (var world : server.getWorlds()) {
                for (PlayerEntity player : world.getPlayers()) {
                    tickRainExposure(player);
                }
            }
        });
    }

    private void tickRainExposure(PlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) {
            RAIN_EXPOSURE.remove(player.getUuid());
            return;
        }

        boolean exposedToRain = player.getWorld().isRaining()
                && player.getWorld().isSkyVisible(player.getBlockPos())
                && player.getWorld().getBiome(player.getBlockPos()).value().hasPrecipitation();

        boolean protectedByArmor = KongraArmorMaterial.isFullSetWorn(player);

        if (exposedToRain && !protectedByArmor) {
            int exposure = RAIN_EXPOSURE.getOrDefault(player.getUuid(), 0) + 1;
            RAIN_EXPOSURE.put(player.getUuid(), exposure);

            if (exposure > RAIN_GRACE_TICKS && exposure % RAIN_DAMAGE_INTERVAL == 0) {
                DamageSource source = player.getDamageSources().magic();
                player.damage(source, 1.5f);
            }
        } else {
            // Dry off gradually.
            int exposure = RAIN_EXPOSURE.getOrDefault(player.getUuid(), 0);
            if (exposure > 0) {
                RAIN_EXPOSURE.put(player.getUuid(), Math.max(0, exposure - 2));
            }
        }
    }
}