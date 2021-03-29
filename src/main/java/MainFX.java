import Controller.BigController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plm.fxml"));
        Parent root = loader.load();
        BigController bigController = loader.getController();
        bigController = new BigController();
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args) {

        launch(args);
    }

//    static BigController setMainController(){
//        try {
//            Properties properties = new Properties();
//            properties.load(new FileInputStream("props.properties"));
//            String carFile = properties.getProperty("carFile");
//            System.out.println(carFile);
//            if (carFile == null) {
//                carFile = "file.txt";
//                System.err.println("Requests file not found. Using default" + carFile);
//            }
//            String reservationFile = properties.getProperty("reservationFile");
//            if (reservationFile == null) {
//                reservationFile = "reservationfile.txt";
//                System.err.println("RepairedForms file not found. Using default" + reservationFile);
//            }
//
//
//
//        CarRepositoryFile file1 = new CarRepositoryFile(carFile);
//        ReservationRepositoryFile file2 = new ReservationRepositoryFile(reservationFile);
//        BigController controller = new BigController(file1,file2);
//        return controller;
//        }catch (IOException ex){System.err.println("Error reading the configuration file"+ex);}
//
//       // Controller ctrl = new Controller(null,null);
//        return null;
//    }
}
