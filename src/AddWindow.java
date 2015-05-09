import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class AddWindow {

    public static void display(){
        Stage window = new Stage();
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a new employee");

        Button buttonAdd = new Button("Add");
        buttonAdd.setMinWidth(70);

        Button buttonCancel = new Button("Cancel");
        buttonCancel.setMinWidth(70);

        Label labelName = new Label("First name:");
        Label labelName2 = new Label("Second name:");
        Label labelSelect = new Label("Select type of payment");
        Label labelPayment = new Label("Payment:");

        TextField textName = new TextField();
        TextField textName2 = new TextField();
        TextField textPayment = new TextField();


        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(buttonAdd, buttonCancel);

        ChoiceBox<String> choicePayment = new ChoiceBox<>();
        choicePayment.getItems().addAll("Fixed payment", "Hourly wage");
        choicePayment.setValue("Fixed payment");

        GridPane.setConstraints(labelName, 0, 0);
        GridPane.setConstraints(labelName2, 0, 1);
        GridPane.setConstraints(labelSelect, 0, 2);
        GridPane.setConstraints(labelPayment, 0, 3);
        GridPane.setConstraints(textName, 1, 0);
        GridPane.setConstraints(textName2, 1, 1);
        GridPane.setConstraints(choicePayment, 1, 2);
        GridPane.setConstraints(textPayment, 1, 3);
        GridPane.setConstraints(hBox, 1, 4);

        gridPane.getChildren().addAll(
                labelName, labelName2, labelSelect,
                labelPayment, textName, textName2,
                choicePayment, textPayment, hBox);

        buttonAdd.setOnAction(e -> {
            boolean added = addNewEmployee(textName, textName2, choicePayment, textPayment);

            if (added) {
                window.close();
            }
            else {
                Label labelError = new Label("Fill in all the fields. The \"Payment\" field must be a number.");
                labelError.setPadding(new Insets(5));
                borderPane.setBottom(labelError);
            }
        });

        buttonCancel.setOnAction(e -> window.close());

        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 310, 300);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

    }

    public static boolean isFloat(TextField input){
        try {
            float payment = Float.parseFloat(input.getText());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean addNewEmployee(TextField name, TextField name2, ChoiceBox<String> employeeType, TextField payment){
        String type = employeeType.getValue().equals("Hourly wage") ? "1" : "0";
        if (name.getText().equals("") || name2.getText().equals("") || payment.getText().equals("")){
            return false;
        }
        else {
            int id = incID();
            if (type.equals("0")) {
                Employee employee = new Employee();
                employee.setId(id);
                employee.setFirstName(name.getText());
                employee.setSecondName(name2.getText());
                employee.setPayment(Float.parseFloat(payment.getText()));
                Main.employeesList.add(employee);
            }
            else {
                EmployeeTax employee = new EmployeeTax();
                employee.setId(id);
                employee.setFirstName(name.getText());
                employee.setSecondName(name2.getText());
                employee.setPayment(Float.parseFloat(payment.getText()));
                Main.employeesList.add(employee);
            }
            return FileHandler.writeToFile(
                    String.format("%s;%s;%s;%s;%s;", id, type, name.getText(), name2.getText(), payment.getText()));


        }
    }

    public static int incID(){
        int id = 0;
        int nextId;
        try {
            Scanner scanner = new Scanner(FileHandler.openFile());
            scanner.useDelimiter(";");
            while (scanner.hasNext()){
                nextId = Integer.parseInt(scanner.next());
                if (id < nextId)
                    id = nextId;
                scanner.next();
                scanner.next();
                scanner.next();
                scanner.next();
            }
            scanner.close();
            return id + 1;
        }
        catch (IOException e){
            return 1;
        }

    }
}
