package com.example.dietscheduler.dietscheduler.Model;

public class Week_diet_data {
    String food,time,day;
    int id;

    public Week_diet_data(String food, String time,String day,int id) {
        this.food = food;
        this.time = time;
        this.day=day;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Week_diet_data(){}

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
