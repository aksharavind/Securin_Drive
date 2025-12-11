# 🍽️ Recipe API  
### A Spring Boot REST API for Managing & Searching Recipes

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![MySQL](https://img.shields.io/badge/Database-MySQL-orange)


---

## 📖 Overview

This project is a **Spring Boot REST API** that loads recipe data from a JSON file, stores it into a database, and provides various **search & filter endpoints** such as rating, calories, time, cuisine, and more.

It includes:

- Recipe data ingestion from a large JSON file  
- Advanced search with operators (`=`, `>=`, `<=`)  
- Pagination  
- JPA & MySQL integration  
- Clean REST endpoints  

---

## 🛠️ Tech Stack

| Component | Technology |
|----------|------------|
| Language | Java 17 |
| Backend | Spring Boot |
| ORM | Spring Data JPA |
| Database | MySQL |
| Build Tool | Maven |
| Parser | Jackson JSON |

---

## 📂 Project Structure

src/main/java/com/example/securin_drive/
│
├── RecipeEntity.java
├── RecipeController.java
├── RecipeRepository.java
├── ReceipeService.java
├── RecipeServiceImpl.java
│
└── src/main/resources/
└── US_recipes_null.json
