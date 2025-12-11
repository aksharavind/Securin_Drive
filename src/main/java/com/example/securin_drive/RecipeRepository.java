package com.example.securin_drive;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository  extends JpaRepository<RecipeEntity,String> {

    List<RecipeEntity> findByTitleContainingIgnoreCaseAndCuisineContainingIgnoreCase(String title, String cuisine);
}
