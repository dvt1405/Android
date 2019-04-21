package model;

import java.io.Serializable;
import java.util.List;

public class PracticeGroup implements Serializable {
    private int id,avatar;
    private String name;
    private List<Practice> listPractice;
    private String description;

    public PracticeGroup(int id, String name, int avatar, String desciption) {
        this.name = name;
        this.avatar = avatar;
        this.description = desciption;
    }

    public PracticeGroup(int id, String name, int avatar, List<Practice> listPractice, String desciption) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.listPractice = listPractice;
        this.description = desciption;
    }

    public PracticeGroup() {
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
}
