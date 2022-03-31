package org.bolivianjug.rx101.rxapp.entity;

/**
 * Created by julio.rocha on 30/3/22.
 *
 * @author julio.rocha
 */
public class Joke {
    public String category;
    public String setup;
    public String delivery;

    @Override
    public String toString() {
        return "Joke{" +
                "category='" + category + '\'' +
                ", setup='" + setup + '\'' +
                ", delivery='" + delivery + '\'' +
                '}';
    }
}
