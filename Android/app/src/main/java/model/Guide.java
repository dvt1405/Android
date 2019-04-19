package model;

import java.io.Serializable;

public class Guide implements Serializable {
    private int id, image;
    private String name, description;

    public Guide() {

    }

    public Guide(int id, String name, int image, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
    }
    public Guide(String name, int image, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
