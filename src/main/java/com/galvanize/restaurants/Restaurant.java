package com.galvanize.restaurants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.*;

@Entity
public final class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_generator")
    @SequenceGenerator(name="restaurant_generator", sequenceName = "restaurant_sequence", allocationSize=1)
    private final long id;
    private final String name;

    @OneToMany(mappedBy="restaurant")
    private Set<Review> reviews =  new HashSet<Review>();

    @JsonCreator
    Restaurant(@JsonProperty("id") final long id, @JsonProperty("name") final String name) {
        this.id = id;
        this.name = name;
    }

//    Restaurant(String name){
//        this(Long.MIN_VALUE, null);
//    }

    private Restaurant(){
        id = 0;
        name = null;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Set<Review> getReviews() {
        return reviews;
              /*
                Collections.singletonList(new HashMap<String, Object>() {{
            put("id",3);
            put("text", "the review text");
        }});
        */
    }
}
