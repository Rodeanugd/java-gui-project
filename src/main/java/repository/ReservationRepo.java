package repository;

import domain.Car;
import domain.Identifiable;

import java.util.Set;

public interface ReservationRepo<ID, T extends Identifiable<ID>> extends Repository<ID, T>{
    public Set<String> peopleByCar(Car car);
    public Set carsByPerson(String name);
    public Set peopleByDate(String startDate);
    public Set carsByPrice(int price);
    public Set peopleByCarByPrice(Car car, int price);
}
