package com.example.securin_drive;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final ReceipeService receipeService;

    public RecipeController(RecipeRepository recipeRepository, ReceipeService receipeService) {
        this.recipeRepository = recipeRepository;
        this.receipeService = receipeService;
    }

    /* Used to Fetch and Save the database in url*/
    @GetMapping("/fetch")
    public String fetchFromFile(
            @RequestParam int page,
            @RequestParam  int limit) {

        receipeService.fetchAndSave(page,limit);
        return "Data fetched successfully and stored in database";
    }


      @GetMapping("/recipes")
      public List<RecipeEntity> listAllDetails(@RequestParam int page,@RequestParam int limit )
      {
          limit=10;
          List<RecipeEntity> list=new ArrayList<>();
          for(int i=page;i<=limit;i++)
          {
              list=recipeRepository.findAll();
          }
          return  list;
      }

    @GetMapping("/recipes/search1/{rating}")
    public List<RecipeEntity>  searchByRating(@PathVariable double rating)
    {
         return  recipeRepository.findAll()
                 .stream()
                 .filter(c->c.getRating()>=rating)
                 .toList();
    }

    @GetMapping("/recipes/list-page")
    public ResponseEntity<?> getCvesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RecipeEntity> Page = recipeRepository.findAll(PageRequest.of(page, size));

        Map<String, Object> response = new HashMap<>();
        response.put("data", Page.getContent());
        response.put("currentPage", Page.getNumber());
        response.put("totalItems", Page.getTotalElements());
        response.put("totalPages", Page.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/recipes/search")
    public ResponseEntity<?> searchByCaloriesAndTitle(
            @RequestParam(required = false) String calories,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) String total_time,
            @RequestParam(required = false) String rating) {


        String finalOperator = null;
        Integer calorievalue = null;


        if (calories != null && !calories.isEmpty()) {
            if (calories.startsWith("<=")) {
                finalOperator = "<=";
                calorievalue = Integer.parseInt(calories.substring(2));
            } else if (calories.startsWith(">=")) {
                finalOperator = ">=";
                calorievalue = Integer.parseInt(calories.substring(2));
            } else {
                finalOperator = "=";
                calorievalue = Integer.parseInt(calories);
            }
        }


        Integer totalTimeValue = null;
        String totalTimeOperator = null;
        if (total_time != null && !total_time.isEmpty()) {
            if (total_time.startsWith("<=")) {
                totalTimeOperator = "<=";
                totalTimeValue = Integer.parseInt(total_time.substring(2));
            } else if (total_time.startsWith(">=")) {
                totalTimeOperator = ">=";
                totalTimeValue = Integer.parseInt(total_time.substring(2));
            } else {
                totalTimeOperator = "=";
                totalTimeValue = Integer.parseInt(total_time);
            }
        }


        Double ratingValue = null;
        String ratingOperator = null;
        if (rating != null && !rating.isEmpty()) {
            if (rating.startsWith("<=")) {
                ratingOperator = "<=";
                ratingValue = Double.parseDouble(rating.substring(2));
            } else if (rating.startsWith(">=")) {
                ratingOperator = ">=";
                ratingValue = Double.parseDouble(rating.substring(2));
            } else {
                ratingOperator = "=";
                ratingValue = Double.parseDouble(rating);
            }
        }


        String finalOperatorForFilter = finalOperator;
        Integer finalCalorievalue = calorievalue;
        Integer finalTotalTimeValue = totalTimeValue;
        String finalTotalTimeOperator = totalTimeOperator;
        Double finalRatingValue = ratingValue;
        String finalRatingOperator = ratingOperator;

        List<RecipeEntity> results = recipeRepository.findAll().stream()

                .filter(r -> {
                    if (finalCalorievalue == null) return true;
                    int c = r.getCalories();
                    return switch (finalOperatorForFilter) {
                        case "<=" -> c <= finalCalorievalue;
                        case ">=" -> c >= finalCalorievalue;
                        default -> c == finalCalorievalue;
                    };
                })

                .filter(r -> {
                    if (title == null || title.isEmpty()) return true;
                    return r.getTitle().toLowerCase().contains(title.toLowerCase());
                })

                .filter(r -> {
                    if (cuisine == null || cuisine.isEmpty()) return true;
                    return r.getCuisine().equals(cuisine);
                })

                .filter(r -> {
                    if (finalTotalTimeValue == null) return true;
                    int time = r.getTotal_time();
                    return switch (finalTotalTimeOperator) {
                        case "<=" -> time <= finalTotalTimeValue;
                        case ">=" -> time >= finalTotalTimeValue;
                        default -> time == finalTotalTimeValue;
                    };
                })

                .filter(r -> {
                    if (finalRatingValue == null) return true;
                    double recipeRating = r.getRating();
                    return switch (finalRatingOperator) {
                        case "<=" -> recipeRating <= finalRatingValue;
                        case ">=" -> recipeRating >= finalRatingValue;
                        default -> recipeRating == finalRatingValue;
                    };
                })
                .toList();

        return ResponseEntity.ok(results);
    }
}


