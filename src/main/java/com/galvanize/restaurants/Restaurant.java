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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="id")
    private Set<Review> reviews =  new HashSet<Review>();

    @JsonCreator
    Restaurant(@JsonProperty("id") final long id, @JsonProperty("name") final String name) {
        this.id = id;
        this.name = name;
    }

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

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
