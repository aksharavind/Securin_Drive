package com.example.securin_drive;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
@Service
public class RecipeServiceImpl implements ReceipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void fetchAndSave(int start, int end) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream input =
                    getClass().getClassLoader().getResourceAsStream("US_recipes_null.json");

            JsonNode root = mapper.readTree(input);

            for (int i = start; i <= end; i++) {

                String key = String.valueOf(i);
                JsonNode node = root.get(key);
                if (node == null) continue;

                RecipeEntity recipe = new RecipeEntity();
                recipe.setId(key);
                recipe.setCuisine(node.get("cuisine").asText());
                recipe.setTitle(node.get("title").asText());
                recipe.setRating(node.get("rating").asDouble());
                recipe.setPrep_time(node.get("prep_time").asInt());
                recipe.setCook_time(node.get("cook_time").asInt());
                recipe.setTotal_time(node.get("total_time").asInt());
                recipe.setDescription(node.get("description").asText());
                recipe.setServes(node.get("serves").asText());
                recipe.setCalories(node.get("calories").asInt());
                if (!recipeRepository.existsById(key)) {
                    recipeRepository.save(recipe);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
