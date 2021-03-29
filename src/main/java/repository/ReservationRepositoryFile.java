package repository;

import domain.Car;
import domain.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class ReservationRepositoryFile extends AbstractRepo<Integer, Reservation> implements ReservationRepo<Integer,Reservation> {
    public String filename;

    public ReservationRepositoryFile(String str){
        this.filename = str;
        readFromFile();
    }

    private void readFromFile(){
        try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
            String line = null;
            while((line = buffer.readLine()) != null)
            {
                String[] el = line.split(";");
                if (el.length != 6)
                {
                    System.err.println("Line is not valid!..." + line);
                    continue;
                }

                try{
                    String[] carel = el[1].split("-");
                    if (carel.length != 7) {
                        System.err.println("Line is not valid!..." + line);
                        System.err.println(carel.length);
                        continue;
                    }

                    Car car = new Car(0,"","",0,0,0,false);
                    try {
                        int cid = Integer.parseInt(carel[0]);
                        int cmaxSpeed = Integer.parseInt(carel[3]);
                        int cprice = Integer.parseInt(carel[4]);
                        int cmanYear = Integer.parseInt(carel[5]);
                        boolean cisManufactured = Boolean.parseBoolean(carel[6]);
                        car = new Car(cid, carel[1], carel[2], cmaxSpeed, cprice, cmanYear, cisManufactured);
                    }catch(Exception e){System.err.println("Error in data parsing:" + e); }


                    int id = Integer.parseInt(el[0]);
                    //el[1] = name
                    //int carID = Integer.parseInt(el[2]);
                    //el[3] = carManufacturer
                    //el[4] = carModel
                    // int carMaxSpeed = Integer.parseInt(el[5]);
                    //int carPrice = Integer.parseInt(el[6]);
                    //int carManYear = Integer.parseInt(el[7]);
                    //boolean carIsManufactured = Boolean.parseBoolean(el[8]);
                    //el[2] = startDate
                    //el[3] = endDate
                    int price = Integer.parseInt(el[4]);

                    Reservation reservation = new Reservation(id,car,el[2],el[3],price,el[5]);
                    //System.out.println(reservation.toString());
                    super.add(reservation);
                }catch(NumberFormatException exception){
                    System.err.println("Invalid id format!..." + el[0]);
                }
            }
        }catch (IOException exception){
            throw new RepositoryException("File reading error" + exception);
        }
    }

    private void writeToFile(){
        try ( PrintWriter p = new PrintWriter(filename)){
            for (Reservation r: findAll()){
                String s = r.getID() + ";" + r.getCar() + ";" + r.getStartDate() + ";" + r.getEndDate() + ";" + r.getPrice() + ";" + r.getName();
                p.println(s);
            }
        }
        catch (IOException exception){throw new RepositoryException("Writing error"+ exception);}
    }

    @Override
    public void add(Reservation r){
        try{
            super.add(r);
            writeToFile();
        }
        catch(RuntimeException e)
        { throw new RepositoryException (e);}
    }

    @Override
    public void delete(Integer id)
    {
        try{
            super.delete(id);
            writeToFile();
        }catch(RuntimeException e){throw new RepositoryException(e);}
    }

    @Override
    public void update(Reservation r, Integer id)
    {
        try{
            super.update(r, id);
            writeToFile();
        }catch(RuntimeException ex){throw new RepositoryException(ex);}
    }


    @Override
    //The name of the people who reserved a certain car
    public Set<String> peopleByCar(Car car){
        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
        Set<String> people = reservations.stream().filter(reservation -> reservation.getCar().equals(car)).map(reservation -> reservation.getName()).collect(Collectors.toSet());
        return people;
    }

    @Override
    //All the cars rented by a certain person
    public Set<String> carsByPerson(String name){
        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
        Set<String> cars = reservations.stream().filter(reservation -> reservation.getName().equals(name)).map(reservation -> reservation.getCar().toString()).collect(Collectors.toSet());
        return cars;
    }

    @Override
    //The names of the people renting at a certain date
    public Set<String> peopleByDate(String startDate){
        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
        Set<String> people = reservations.stream().filter(reservation -> reservation.getStartDate().equals(startDate)).map(reservation -> reservation.getName()).collect(Collectors.toSet());
        return people;
    }

    @Override
    //The cars that have been rented at a price higher than a certain number
    public Set<Car> carsByPrice(int price){
        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
        Set<Car> cars = reservations.stream().filter(reservation -> reservation.getPrice() > price).map(reservation -> reservation.getCar()).collect(Collectors.toSet());
        return cars;
    }

    @Override
    //The people that have rented a certain car at a price lower than a given number
    public Set<String> peopleByCarByPrice(Car car, int price){
        Collection<Reservation> reservations = (Collection<Reservation>) findAll();
        Set<String> people = reservations.stream().filter(reservation -> reservation.getCar().equals(car)).filter(reservation -> reservation.getPrice() < price).map(reservation -> reservation.getName()).collect(Collectors.toSet());
        return people;
    }

}
