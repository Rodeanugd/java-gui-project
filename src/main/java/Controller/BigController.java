package Controller;

import domain.Car;
import domain.Reservation;
import javafx.beans.property.SimpleStringProperty;
import repository.CarRepositoryFile;
import repository.ReservationRepositoryFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import domain.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class BigController {
    private CarRepositoryFile carRepo;
    private ReservationRepositoryFile reservationRepo;
    private int currentCarID;
    private int currentReservationID;

//---------------------------------------------------------------FXML----DECLARATIONS-------------------------------------------------------------------------

    @FXML
    private TextField id;
    @FXML
    private TextField manufacturer;
    @FXML
    private TextField model;
    @FXML
    private TextField maxSpeed;
    @FXML
    private TextField price;
    @FXML
    private TextField yearOfManufacture;
    @FXML
    private CheckBox stillManufactured;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button updateButton;
    @FXML
    private TableView tableView = new TableView();
    @FXML
    private TableColumn<Car,String> idCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> manCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> modCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> spdCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> prCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> yCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Car,String> imCol = new TableColumn<>("abc");




    @FXML
    private TextField res_rid;
    @FXML
    private TextField res_client;
    @FXML
    private TextField res_startDate;
    @FXML
    private TextField res_endDate;
    @FXML
    private TextField res_cid;
    @FXML
    private TextField res_price;
    @FXML
    private Button res_addButton;
    @FXML
    private Button res_removeButton;
    @FXML
    private Button res_updateButton;
    @FXML
    private TableView res_tableView = new TableView();
    @FXML
    private TableColumn<Reservation,String> res_ridCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_cidCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_manCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_modCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_spdCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_prCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_yCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_imCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_stDateCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_edDateCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_clCol = new TableColumn<>("abc");
    @FXML
    private TableColumn<Reservation,String> res_rprCol = new TableColumn<>("abc");


    @FXML
    private TextArea rep_textArea;
    @FXML
    private TextField rep_cid;
    @FXML
    private TextField rep_client;
    @FXML
    private TextField rep_date;
    @FXML
    private Button rep_button1;
    @FXML
    private Button rep_button2;
    @FXML
    private Button rep_button3;


//--------------------------------------------------------------------------------------------------------------------------------------------------------------------


    public BigController(){

    }

    public void initialize(){
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("props.properties"));
            String carFile = properties.getProperty("carFile");
            System.out.println(carFile);
            if (carFile == null) {
                carFile = "file.txt";
                System.err.println("Requests file not found. Using default" + carFile);
            }
            String reservationFile = properties.getProperty("reservationFile");
            if (reservationFile == null) {
                reservationFile = "reservationfile.txt";
                System.err.println("RepairedForms file not found. Using default" + reservationFile);
            }



            CarRepositoryFile file1 = new CarRepositoryFile(carFile);
            ReservationRepositoryFile file2 = new ReservationRepositoryFile(reservationFile);
            this.carRepo = file1;
            this.reservationRepo = file2;
        }catch (IOException ex){System.err.println("Error reading the configuration file"+ex);}

        createCarTable();
        createReservationTable();
        this.currentCarID = calculateCarID();
        this.currentReservationID = calculateReservationID();

        List<Car> al = new ArrayList<Car>((Collection<? extends Car>) this.carRepo.findAll());
        for(int i = 0; i < al.size(); i++)
            addToCarTable(al.get(i));

        List<Reservation> al1 = new ArrayList<Reservation>((Collection<? extends Reservation>) this.reservationRepo.findAll());
        for(int i = 0; i < al1.size(); i++)
            addToReservationTable(al1.get(i));
    }


    private void createCarTable(){
        tableView.setEditable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        manCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        modCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        spdCol.setCellValueFactory(new PropertyValueFactory<>("maxSpeed"));
        prCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        yCol.setCellValueFactory(new PropertyValueFactory<>("manYear"));
        imCol.setCellValueFactory(new PropertyValueFactory<>("isManufactured"));
    }

    private void createReservationTable(){
        res_tableView.setEditable(true);
        res_ridCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        res_cidCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getID().toString()));
        res_manCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getManufacturer()));
        res_modCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCar().getModel()));
        res_spdCol.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCar().getMaxSpeed())));
        res_prCol.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCar().getPrice())));
        res_yCol.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCar().getManYear())));
        res_imCol.setCellValueFactory(cellData -> new SimpleStringProperty(Boolean.toString(cellData.getValue().getCar().getIsManufactured())));
        res_stDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        res_edDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        res_rprCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        res_clCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    public void addToCarTable(Car car){
        tableView.getItems().add(car);
    }

    public void addToReservationTable(Reservation res){
        res_tableView.getItems().add(res);
    }


    public void removeFromCarTable(int id){
        int i = 0;
        for(Object obj : tableView.getItems()){
            Car car = (Car) obj;
            if(car.getID() == id)
                break;
            i++;
        }

        tableView.getItems().remove(i);
    }

    public void removeFromReservationTable(int id){
        int i = 0;
        for(Object obj : res_tableView.getItems()){
            Reservation res = (Reservation) obj;
            if(res.getID() == id)
                break;
            i++;
        }

        res_tableView.getItems().remove(i);
    }


    public void updateInCarTable(Car ncar, int id){
        int i = 0;
        for(Object obj : tableView.getItems()){
            Car car = (Car) obj;
            if(car.getID() == id)
                break;
            i++;
        }

        tableView.getItems().set(i,ncar);
    }

    public void updateInReservationTable(Reservation nres, int id){
        int i = 0;
        for(Object obj : res_tableView.getItems()){
            Reservation res = (Reservation) obj;
            if(res.getID() == id)
                break;
            i++;
        }

        res_tableView.getItems().set(i,nres);
    }

    @FXML
    public void addCarHandler(ActionEvent actionEvent){
        try {
            String man = manufacturer.getText();
            String mod = model.getText();
            int mS = Integer.parseInt(maxSpeed.getText());
            int pr = Integer.parseInt(price.getText());
            int mY = Integer.parseInt(yearOfManufacture.getText());
            Boolean sM = stillManufactured.selectedProperty().get();
            System.out.println("here");
            Car car = this.addCar(man,mod,mS,pr,mY,sM);
            this.addToCarTable(car);


        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }

    @FXML
    public void addReservationHandler(ActionEvent actionEvent){
        try {
            String cl = res_client.getText();
            String sd = res_startDate.getText();
            String ed = res_endDate.getText();
            int cid = Integer.parseInt(res_cid.getText());
            int pr = Integer.parseInt(res_price.getText());

            Car car = findCarByID(cid);
            if(car == null)
                throw new NullPointerException("Car does not exist");

            System.out.println("here");

            Reservation res = this.addReservation(cl,sd,ed,pr,car);
            this.addToReservationTable(res);


        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }

    @FXML
    public void removeCarHandler(ActionEvent actionEvent){

        try {
            int ident = Integer.parseInt(id.getText());
            this.removeCar(ident);
            this.removeFromCarTable(ident);
        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }

    @FXML
    public void removeReservationHandler(ActionEvent actionEvent){

        try {
            int ident = Integer.parseInt(res_rid.getText());
            this.removeReservation(ident);
            this.removeFromReservationTable(ident);
        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }

    @FXML
    public void updateCarHandler(ActionEvent actionEvent){
        try {
            String man = manufacturer.getText();
            String mod = model.getText();
            int mS = Integer.parseInt(maxSpeed.getText());
            int pr = Integer.parseInt(price.getText());
            int mY = Integer.parseInt(yearOfManufacture.getText());
            Boolean sM = stillManufactured.selectedProperty().get();
            int ident = Integer.parseInt(id.getText());
            Car car = this.updateCar(man,mod,mS,pr,mY,sM,ident);
            car.setID(ident);
            this.updateInCarTable(car,ident);


        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }

    @FXML
    public void updateReservationHandler(ActionEvent actionEvent){
        try {
            String cl = res_client.getText();
            String sd = res_startDate.getText();
            String ed = res_endDate.getText();
            int cid = Integer.parseInt(res_cid.getText());
            int pr = Integer.parseInt(res_price.getText());
            int ident = Integer.parseInt(res_rid.getText());
            Car car = findCarByID(cid);
            if(car == null)
                throw new NullPointerException("Car does not exist");

            System.out.println("here");

            Reservation res = this.updateReservation(cl,sd,ed,pr,car,ident);
            res.setID(ident);
            this.updateInReservationTable(res,ident);


        }catch (Exception exception){
            System.out.println("No bueno!" + exception);
        }
    }



    public Car addCar(String manufacturer, String model, int maxSpeed, int price, int manYear, boolean isManufactured){
        try{
            this.currentCarID += 1;
            System.out.println(this.currentCarID);
            Car car = new Car(currentCarID,manufacturer,model,maxSpeed,price,manYear,isManufactured);
            carRepo.add(car);
            return car;
        }catch(Exception exception){
            this.currentCarID -= 1;
            System.err.println("Exception occurred:" + exception);
        }
        return null;
    }

    public Reservation addReservation(String client, String startDate, String endDate, int price, Car car){
        try{
            this.currentReservationID += 1;
            System.out.println(this.currentReservationID);
            Reservation res = new Reservation(currentReservationID,car, startDate, endDate, price, client);
            reservationRepo.add(res);
            return res;
        }catch(Exception exception){
            this.currentReservationID -= 1;
            System.err.println("Exception occurred:" + exception);
        }
        return null;
    }


    public void removeCar(int id){
        try{
            carRepo.delete(id);
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
    }

    public void removeReservation(int id){
        try{
            reservationRepo.delete(id);
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
    }


    public Car updateCar(String manufacturer, String model, int maxSpeed, int price, int manYear, boolean isManufactured, int id){
        try{
            Car car = new Car(currentCarID,manufacturer,model,maxSpeed,price,manYear,isManufactured);
            carRepo.update(car,id);
            return car;
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
        return null;
    }

    public Reservation updateReservation(String client, String startDate, String endDate, int price, Car car, int id){
        try{
            Reservation res = new Reservation(currentReservationID, car, startDate, endDate, price, client);
            reservationRepo.update(res,id);
            return res;
        }catch(Exception exception){System.err.println("Exception occurred:" + exception);}
        return null;
    }

    private int calculateCarID() {
        int cid=0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(this.carRepo.filename))) {
            String line = null;
            while ((line = buffer.readLine()) != null) {
                String[] el = line.split(";");
                cid = Integer.parseInt(el[0]);
            }
        } catch (Exception e) { System.out.println("Error:" + e); }
        return cid;
    }

    private int calculateReservationID(){
        int rid=0;
        try (BufferedReader buffer = new BufferedReader(new FileReader(this.reservationRepo.filename))) {
            String line = null;
            while ((line = buffer.readLine()) != null) {
                String[] el = line.split(";");
                rid = Integer.parseInt(el[0]);
            }
        } catch (Exception e) { System.out.println("Error:" + e); }
        return rid;
    }


    private Car findCarByID(int cid){
        for(Car cr: carRepo.findAll()) {
            if (cr.getID() == cid)
                return cr;
        }
        return null;
    }

    public void button1Handler(ActionEvent actionEvent){
        int id = Integer.parseInt(rep_cid.getText());
        Car car = findCarByID(id);
        Set <String> s = this.reservationRepo.peopleByCar(car);
        String ss = String.join("\n",s);
        rep_textArea.setText(ss);
    }

    public void button2Handler(ActionEvent actionEvent){
        String pers = rep_client.getText();
        Set <String> s = this.reservationRepo.carsByPerson(pers);
        String ss = String.join("\n",s);
        rep_textArea.setText(ss);
    }

    public void button3Handler(ActionEvent actionEvent){
        String date = rep_date.getText();
        Set <String> s = this.reservationRepo.peopleByDate(date);
        String ss = String.join("\n",s);
        rep_textArea.setText(ss);
    }






}

