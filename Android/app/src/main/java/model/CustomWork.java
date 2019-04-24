package model;

import java.util.Date;
import java.util.List;

public class CustomWork {
    private String name, date;
    private int id;
    private List<Practice> listPractice;

    public CustomWork(String name) {
        this.name = name;
        date = new Date().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Practice> getListPractice() {
        return listPractice;
    }

    public void setListPractice(List<Practice> listPractice) {
        this.listPractice = listPractice;
    }
}
