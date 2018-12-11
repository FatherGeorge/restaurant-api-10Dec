package com.galvanize.restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.xml.ws.http.HTTPException;

@RestController
@RequestMapping("/api/restaurants")
    final class RestaurantController {


    private final RestaurantRepository repository;

    @Autowired
    RestaurantController(final RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Restaurant> getRestaurants(){
        return repository.findAll();
    }

    @PostMapping
    public Restaurant addRestaurants(@RequestBody Restaurant newRestaurant){
        if ("".equals(newRestaurant.getName())) {
            throw new HTTPException(400);
        }

        return repository.save(newRestaurant);
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
