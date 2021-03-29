package repository;

import domain.Car;

import java.io.*;
import java.util.Map;

public class CarRepositorySerialization extends AbstractRepo<Integer, Car>{
    private String filename;

    public CarRepositorySerialization(String str){
        this.filename = str;
        readFromFile();
    }
    private void writeToFile() {
        try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(this.filename))) {
            o.writeObject(this.map);
        }
        catch(IOException e) {
            throw new RepositoryException(e);
        }
    }

    private void readFromFile() {
        try (ObjectInputStream o = new ObjectInputStream( new FileInputStream(this.filename))) {
            this.map = (Map<Integer, Car>) o.readObject();
        }

        catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException(e);
        }

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
