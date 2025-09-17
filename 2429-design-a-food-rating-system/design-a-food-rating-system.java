import java.util.*;

class FoodRatings {
    Map<String, String> foodToCuisine = new HashMap<>();
    Map<String, Integer> foodToRating = new HashMap<>();
    Map<String, TreeSet<String>> cuisineToFoods = new HashMap<>();

    public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
        for (int i = 0; i < foods.length; i++) {
            foodToCuisine.put(foods[i], cuisines[i]);
            foodToRating.put(foods[i], ratings[i]);
            cuisineToFoods.computeIfAbsent(cuisines[i], k -> new TreeSet<>((a, b) -> {
                int cmp = Integer.compare(foodToRating.get(b), foodToRating.get(a));
                return cmp != 0 ? cmp : a.compareTo(b);
            })).add(foods[i]);
        }
    }

    public void changeRating(String food, int newRating) {
        String cuisine = foodToCuisine.get(food);
        TreeSet<String> foodsSet = cuisineToFoods.get(cuisine);
        foodsSet.remove(food);
        foodToRating.put(food, newRating);
        foodsSet.add(food);
    }

    public String highestRated(String cuisine) {
        return cuisineToFoods.get(cuisine).first();
    }
}
