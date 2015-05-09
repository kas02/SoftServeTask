import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by KAS on 08.05.2015.
 */
public class EmployeeDataWindow {

    public static void display(int id, String firstName, String secondName, float payment, double salary){
        Stage window = new Stage();
        window.setTitle("Employee information");
        Label labelId = new Label("ID: " + id);
        Label labelName1 = new Label("First name: " + firstName);
        Label labelName2 = new Label("Second name: " + secondName);
        //Label labelPayment = new Label("Payment: "+ payment + " UAH");
        Label labelSalary = new Label("Monthly salary: "+ salary + " UAH");


        VBox infVbox = new VBox(10);
        infVbox.getChildren().addAll(labelId, labelName1, labelName2, labelSalary);
        infVbox.setPadding(new Insets(10));

        Scene infScene = new Scene(infVbox, 300, 300);
        window.setScene(infScene);
        window.setResizable(false);
        window.show();

    }

}
