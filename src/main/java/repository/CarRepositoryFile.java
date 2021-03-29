package repository;

import domain.Car;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class CarRepositoryFile extends AbstractRepo<Integer, Car>{
    public String filename;

    public CarRepositoryFile(String str){
        this.filename = str;
        readFromFile();
    }

    private void readFromFile(){
        try (BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
            String line = null;
            while((line = buffer.readLine()) != null)
            {
                String[] el = line.split(";");
                if (el.length != 7)
                {
                    System.err.println("Line is not valid!..." + line);
                    continue;
                }

                try{
                int id = Integer.parseInt(el[0]);
                int maxSpeed = Integer.parseInt(el[3]);
                int price = Integer.parseInt(el[4]);
                int manYear = Integer.parseInt(el[5]);
                boolean isManufactured = Boolean.parseBoolean(el[6]);
                Car car = new Car(id, el[1], el[2], maxSpeed, price, manYear, isManufactured);
                super.add(car);
                System.out.println("Car added successfully");
            }catch(NumberFormatException exception){
                System.err.println("Invalid id format!..." + el[0]);
            }
            }
        }catch (IOException exception){
            throw new RepositoryException("File reading error" + exception);
        }
    }

    private void writeToFile(){
        try ( PrintWriter p= new PrintWriter(filename)){
            for (Car c: findAll()){
                String s= c.getID() + ";" + c.getManufacturer()+ ";" +c.getModel()+ ";" + c.getMaxSpeed()+ ";" + c.getPrice()+ ";" + c.getManYear() + ";" + c.getIsManufactured();
                p.println(s);
            }
        }
        catch (IOException exception){throw new RepositoryException("Writing error"+ exception );}
    }

    @Override
    public void add(Car c)
    {
        try{
        super.add(c);
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
    public void update(Car c, Integer id)
    {
        try{
        super.update(c, id);
        writeToFile();
    }catch(RuntimeException ex){throw new RepositoryException(ex);}
    }




}

