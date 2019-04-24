package model;

import java.io.Serializable;
import java.util.List;

public class Practice implements Serializable {
    private int id, avatar;
    private String name;
    private List<Guide> listGuide;
    private String description;

    public Practice() {
    }

    public Practice(String name, int avatar, String description) {
        this.avatar = avatar;
        this.name = name;
        this.description = description;
    }
    public Practice(String name, List<Guide> listGuide, int avatar, String description) {
        this.avatar = avatar;
        this.name = name;
        this.listGuide = listGuide;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Guide> getListGuide() {
        return listGuide;
    }

    public void setListGuide(List<Guide> listGuide) {
        this.listGuide = listGuide;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
