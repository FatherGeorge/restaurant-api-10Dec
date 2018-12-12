package com.galvanize.restaurants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public final class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_generator")
    @SequenceGenerator(name="review_generator", sequenceName = "review_sequence", allocationSize=1)
    private long id;
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="restaurant_id", nullable=false)
    private Restaurant restaurant;


    /*public Review(String text, Restaurant restaurant) {
        this.text = text;
        this.restaurant = restaurant;
    }*/

    @JsonCreator
    Review(@JsonProperty("text") final String reviewText, @JsonProperty("restaurant") final Restaurant restaurant) {
        this.text = text;
        this.restaurant = restaurant;
    }

    private Review() {
        id = 0;
        text = null;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", restaurant=" + restaurant.getName() +
                '}';
    }
}
