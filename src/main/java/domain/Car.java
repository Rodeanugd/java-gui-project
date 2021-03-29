package domain;

import java.io.Serializable;

public class Car implements Comparable<Car>,Identifiable<Integer>,Serializable {
    private int ID;
    private String manufacturer;
    private String model;
    private int maxSpeed;
    private int price;
    private int manYear;
    private boolean isManufactured;

    //Constructors
    public Car(int ID, String manufacturer, String model, int maxSpeed, int price, int manYear, boolean isManufactured){
        this.ID = ID;
        this.manufacturer = manufacturer;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.price = price;
        this.manYear = manYear;
        this.isManufactured = isManufactured;
    }
    public Car(){
        this.ID = 0;
        this.manufacturer = "";
        this.model = "";
        this.maxSpeed = 0;
        this.price = 0;
        this.manYear = 0;
        this.isManufactured = false;
    }

    //Getters
    public String getManufacturer(){ return this.manufacturer; }
    public String getModel(){ return this.model; }
    public int getMaxSpeed(){
        return this.maxSpeed;
    }
    public int getPrice(){
        return this.price;
    }
    public int getManYear(){
        return this.manYear;
    }
    public boolean getIsManufactured(){
        return this.isManufactured;
    }

    //Setters
    public void setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
    }
    public void setModel(String model){
        this.model = model;
    }
    public void setMaxSpeed(int maxSpeed){
        this.maxSpeed = maxSpeed;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setManYear(int manYear){
        this.manYear = manYear;
    }
    public void setIsManufactured(boolean isManufactured){
        this.isManufactured = isManufactured;
    }

    @Override
    public String toString(){
        return this.ID + "-" + this.manufacturer + "-" + this.model + "-" + this.maxSpeed + "-" + this.price + "-" + this.manYear + "-" + this.isManufactured;
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Car) {
            Car car =(Car) obj;
            return this.manufacturer.equals(car.manufacturer) && this.model.equals(car.model) && this.maxSpeed == car.maxSpeed && this.price == car.price && this.manYear == car.manYear && this.isManufactured == car.isManufactured;
        }
        return false;
    }

    public Integer getID(){
        return this.ID;
    }
    public void setID(Integer ID){
        this.ID = ID;
    }

    public int compareTo(Car car){
        if (this.price > car.price)
            return 1;
        if (this.price < car.price)
            return -1;
        return 0;
    }

    public int compareBySpeed(Car car){
        if (this.maxSpeed > car.maxSpeed)
            return 1;
        if (this.maxSpeed < car.maxSpeed)
            return -1;
        return 0;
    }


}
