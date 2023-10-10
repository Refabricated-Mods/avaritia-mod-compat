package com.example;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.intellij.lang.annotations.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "avaritia-mod-compat";
	public static final String aMODID = "avaritia";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MODID +":resource-pack");
	ArrayList<CustomIngredient> customIngredients = new ArrayList<>();


	@Override
	public void onInitialize() {

		customIngredients.add(new CustomIngredient("avaritia:diamond_lattice", "item"));
		customIngredients.add(new CustomIngredient("avaritia:crystal_matrix_ingot", "item"));
		customIngredients.add(new CustomIngredient("avaritia:pile_of_neutrons", "item"));
		customIngredients.add(new CustomIngredient("avaritia:neutronium_nugget", "item"));
		customIngredients.add(new CustomIngredient("avaritia:neutronium_ingot", "item"));
		addModIngredient("ad_astra", "calorite_block");
		addModIngredient("alchemist", "aternius_fuel");
		addModIngredient("alchemist", "catalytic_lens");
		addModIngredient("bloodmagic", "archmagebloodorb");
		addModIngredient("botania", "gaia_ingot");
		addModIngredient("create", "crushing_wheel");
		addModIngredient("create_jetpack", "jetpack");
		addModIngredient("createaddition", "tesla_coil");
		addModIngredient("createdieselgenerators", "large_diesel_engine");
		addModIngredient("dml-refabricated", "glitch_ingot");
		addModIngredient("enderio", "end_steel_block");
		addModIngredient("energizedpower", "energized_gold_plate");
		addModIngredient("indrev", "enriched_nikolite_ingot");
		addModIngredient("mekanism", "alloy_atomic");
		addModIngredient("modern_industrialization", "titanium_block");
		addModIngredient("powah", "crystal_nitro");
		addModIngredient("rftoolsbase", "dimensionalshard");
		addModIngredient("techreborn", "tungstensteel_storage_block");
		addModTagIngredient("twilightforest", "fiery_vial");
		addModIngredient("ae2", "charged_certus_quartz_crystal");
		addModIngredient("ae2", "singularity");
		addModIngredient("megacells", "sky_steel_ingot");
		customIngredients.add(new CustomIngredient("avaritia:record_fragment", "item"));
		customIngredients.add(new CustomIngredient("avaritia:iron_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:gold_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:lapis_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:redstone_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:quartz_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:diamond_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:emerald_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:copper_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:netherite_singularity", "item"));
		customIngredients.add(new CustomIngredient("avaritia:amethyst_singularity", "item"));





		ArrayList<String> arrayList = new ArrayList<>();

		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) -> {
			BuiltInRegistries.ITEM.getTagOrEmpty(TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("c", "vegetables"))).iterator().forEachRemaining(t -> arrayList.add(BuiltInRegistries.ITEM.getKey(t.value()).toString()));
			String[] stewArray = new String[arrayList.size()];
			arrayList.toArray(stewArray);
			registerCustomRecipe(aMODID, "ultimate_stew", aMODID + ":shapeless", "avaritia:ultimate_stew", 1, stewArray);

			CustomIngredient[] ingredientArray = new CustomIngredient[customIngredients.size()];
			customIngredients.toArray(ingredientArray);
			registerCustomRecipe(aMODID, "infinity_catalyst", aMODID+":shapeless", "avaritia:infinity_catalyst", 1, ingredientArray);
		});
		RRPCallback.BEFORE_USER.register(a -> {

			a.add(RESOURCE_PACK);

		});



		LOGGER.info("Hello Fabric world!");


	}

	public void addModIngredient(String modid, String item){
		if (FabricLoader.getInstance().isModLoaded(modid)) customIngredients.add(new CustomIngredient(modid+":"+item, "item"));
	}

	public void addModTagIngredient(String modid, String item){
		if (FabricLoader.getInstance().isModLoaded(modid)) customIngredients.add(new CustomIngredient(modid+":"+item, "tag"));
	}


	public static void registerCustomRecipe(String MODID, String recipeName, String recipeType, String result, int count, CustomIngredient... ingredients){
		String recipe =
				"{\n\"type\": \""+recipeType+"\",\n" +
						"\"result\": {\n\"item\": \""+result+"\",\n\"count\": "+count+"\n},\n" +
						"\"ingredients\": [\n";
		for (int i = 0; i < ingredients.length; i++){
			CustomIngredient ingredient = ingredients[i];
			if (i + 1 < ingredients.length)
				recipe += "{\n\""+ingredient.ingredientType+"\": \""+ingredient.ingredient+"\"\n},\n";
			else
				recipe += "{\n\""+ingredient.ingredientType+"\": \""+ingredient.ingredient+"\"\n}\n";
		}
		recipe += "]\n}";
		RESOURCE_PACK.addData(new ResourceLocation(MODID, "recipes/"+recipeName+".json"), recipe.getBytes());
	}

	public static void registerCustomRecipe(String MODID, String recipeName, String recipeType, String result, int count, String... ingredients){
		String recipe =
				"{\n\"type\": \""+recipeType+"\",\n" +
						"\"result\": {\n\"item\": \""+result+"\",\n\"count\": "+count+"\n},\n" +
						"\"ingredients\": [\n";
		for (int i = 0; i < ingredients.length; i++){
			String ingredient = ingredients[i];
			if (i + 1 < ingredients.length)
				recipe += "{\n\"item\": \""+ingredient+"\"\n},\n";
			else
				recipe += "{\n\"item\": \""+ingredient+"\"\n}\n";
		}
		recipe += "]\n}";
		RESOURCE_PACK.addData(new ResourceLocation(MODID, "recipes/"+recipeName+".json"), recipe.getBytes());
	}
}