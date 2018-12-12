package com.galvanize.restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.xml.ws.http.HTTPException;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
    final class RestaurantController {

    private final RestaurantRepository repository;

    @Autowired
    RestaurantController(final RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/restaurants")
    public Iterable<Restaurant> getRestaurants(){
        return repository.findAll();
    }

    @PostMapping("/restaurants")
    public Restaurant addRestaurants(@RequestBody Restaurant newRestaurant){
        if ("".equals(newRestaurant.getName())) {
            throw new HTTPException(400);
        }
        return repository.save(newRestaurant);
    }

    @PostMapping("/restaurants/{id}/reviews")
    public Iterable<Restaurant> getRestaurantsWithReviews (@RequestBody Restaurant restaurant, @RequestBody Review review){
        System.out.println("\n ************"  + restaurant + "*************** \n");
        System.out.println("\n ************"  + review + "*************** \n");


        //final Restaurant restaurant = new Restaurant(7, "Papa Jones");
        Set reviews = new HashSet<Review>(){{
            add(review);
        }};
        restaurant.setReviews(reviews);
        repository.save(restaurant);

        return repository.findAll();
    }

     /*public ResponseEntity<String> addRestaurants(@RequestBody Restaurant newRestaurant){
        if ("".equals(newRestaurant.getName())) {
            return new ResponseEntity<>(
                    "Restaurant name cannot be empty!",
                    HttpStatus.BAD_REQUEST);
        }

        Restaurant newRest = repository.save(newRestaurant);
        String newRestaurantName = newRest.getName();
        return new ResponseEntity<>(
                newRestaurantName,
                HttpStatus.OK);
    }*/

}
