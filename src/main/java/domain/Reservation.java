package domain;



public class Reservation implements Identifiable<Integer> {
    private int ID;
    private String name;
    private Car car;
    private String startDate;
    private String endDate;
    private int price;

    //Constructors
    public Reservation(int ID,Car car, String startDate, String endDate, int price, String name){
        this.ID = ID;
        this.name = name; //1
        this.car = car;
        this.startDate = startDate; //9
        this.endDate = endDate; //10
        this.price = price; //11
    }
    public Reservation(){
        this.ID = 0;
        this.name = "";
        this.car = new Car();
        this.startDate = "";
        this.endDate = "";
        this.price = 0;
    }

    //Getters

    public String getName(){return this.name;}
    public Car getCar(){return this.car;}
    public String getStartDate(){return this.startDate;}
    public String getEndDate(){return this.endDate;}
    public int getPrice(){return this.price;}

    //Setters


    public void setName(String name) {
        this.name = name;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return this.car.toString() + '-' + this.startDate + '-' + this.endDate + '-' + this.price + '-' + this.name;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Reservation){
            Reservation res = (Reservation) obj;
            return this.ID == res.ID;
        }
        return false;
    }

    public Integer getID(){
        return this.ID;
    }
    public void setID(Integer ID){
        this.ID = ID;
    }

}

