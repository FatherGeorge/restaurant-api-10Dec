package com.galvanize.restaurants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public final class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_generator")
    @SequenceGenerator(name="restaurant_generator", sequenceName = "restaurant_sequence", allocationSize=1)
    private final long id;
    private final String name;

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

    public Long getId() {
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
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
