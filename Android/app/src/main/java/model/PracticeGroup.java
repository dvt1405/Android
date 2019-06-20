package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PracticeGroup implements Serializable {
    private int id,avatar;
    private String name;
    private List<Practice> listPractice;
    private String description;
    private double locked;


    public PracticeGroup(int id, String name, int avatar, String desciption) {
        this.id=id;
        this.name = name;
        this.avatar = avatar;
        this.description = desciption;
        listPractice = new ArrayList<>();
        locked = 0;
    }

    public PracticeGroup(String name, int avatar, String desciption) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.listPractice = listPractice;
        this.description = desciption;
        listPractice = new ArrayList<>();
        locked= 0;
    }

    public PracticeGroup() {
        listPractice = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public List<Practice> getListPractice() {
        return listPractice;
    }

    public void setListPractice(List<Practice> listPractice) {
        this.listPractice = listPractice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desciption) {
        this.description = desciption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLocked() {
        return locked;
    }

    public void setLocked(double locked) {
        this.locked = locked;
    }
}
