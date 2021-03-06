import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.*;

//The main class of Application
public class Main extends Application {

    Button buttonAdd;
    Button buttonDel;
    Button buttonShowCurrent;
    ChoiceBox<String> choiceSort;
    static Stage mainWindow;

    //Current sort type of Employees list
    public static String currentSort;

    //Collection of Employee objects
    public static ArrayList<Employee> employeesList = new ArrayList<>();

    //ListView of Employees
    ListView<String> listView = new ListView<>();

    //Entrance to the program
    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Main window's description text
        Label label = new Label("You can add, delete or view employees data");
        label.setPadding(new Insets(10));

        //Renamed primaryStage for convenience
        mainWindow = primaryStage;
        mainWindow.setTitle("Employees manager");

        //Button which open the window for adding new Employee
        buttonAdd = new Button("Add");
        buttonAdd.setMinWidth(50);

        //Button which delete current selected Employee
        buttonDel = new Button("Delete");
        buttonDel.setMinWidth(50);

        //Button which open the window with information about
        //current selected Employee
        buttonShowCurrent = new Button("Show current");
        buttonShowCurrent.setMinWidth(50);

        //Open window for adding a new Employee
        buttonAdd.setOnAction(e -> AddWindow.display());

        //Delete current selected Employee from the list
        buttonDel.setOnAction(e -> deleteEmployee());

        //Open window with information of current selected Employee
        buttonShowCurrent.setOnAction(e -> {
            //Trying to open a window with Employee information.
            //If it fail then change the description text
            if (!showEmployeeData())
                label.setText("Select an employee");
            else label.setText("You can add, delete or view employees data");
        });

        //Box with buttons
        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.getChildren().addAll(buttonAdd, buttonDel, buttonShowCurrent);

        //View for selection type to sort
        choiceSort = new ChoiceBox<>();
        choiceSort.getItems().addAll("First name", "Second name", "ID", "Monthly salary");
        currentSort = "First name";
        choiceSort.setValue(currentSort);
        //Selection a type to sort and refreshing a list by invoking showList method
        choiceSort.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            currentSort = newValue;
            showList();
        });

        try {
            //Open or create file if it not exists
            Scanner readFile = new Scanner(FileHandler.openFile());
            //Check whether the file is empty and throw exception if it is
            if (!readFile.hasNext()) {
                throw new Exception();
            }

            readFile.useDelimiter(";");
            //Cycle reading data from text file and creating an ArrayList<Employee> collection
            while (readFile.hasNext()){

                String id = readFile.next();
                String type = readFile.next();
                String firstName = readFile.next();
                String secondName = readFile.next();
                String payment = readFile.next();

                if (type.equals("0")){
                    //System.out.println(type);
                    Employee employee = new Employee(Integer.parseInt(id), firstName, secondName, Float.parseFloat(payment));
                    employeesList.add(employee);
                } else {
                    //System.out.println(type);
                    EmployeeTax employee = new EmployeeTax(Integer.parseInt(id), firstName, secondName, Float.parseFloat(payment));
                    employeesList.add(employee);
                }

            }
            showList();
            readFile.close();
        } catch (Exception e){
            //System.out.println(e);
            listView.getItems().add("No current employees");
            listView.getSelectionModel().select(-1);
            buttonShowCurrent.setDisable(true);
            choiceSort.setDisable(true);

        }

        Label labelSort = new Label("Sort by:");



        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(0, 10, 10, 10));
        vBox.getChildren().addAll(labelSort, choiceSort);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setTop(label);
        borderPane.setBottom(buttonsBox);
        borderPane.setLeft(listView);
        borderPane.setRight(vBox);

        Scene navigationScene = new Scene(borderPane, 400, 400);
        mainWindow.setScene(navigationScene);
        mainWindow.show();


        mainWindow.setOnCloseRequest(e -> {
            e.consume();
            exitProgram();
        });

    }

    public void showList() {
        listView.getItems().remove(0, listView.getItems().size());
        switch (currentSort){
            case "First name":
                Collections.sort(employeesList, Employee.FirstNameComparator);
                break;
            case "Second name":
                Collections.sort(employeesList, Employee.SecondNameComparator);
                break;
            case "ID":
                Collections.sort(employeesList, Employee.IdComparator);
                break;
            case "Monthly salary":
                Collections.sort(employeesList, Employee.PaymentComparator);
                break;
        }
        int i = 1;
        for (Employee anEmployeesList : employeesList) {
            listView.getItems().add(String.format("%s. %s", i++, anEmployeesList.toString()));
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private boolean showEmployeeData() {
        try {
            int index = listView.getSelectionModel().getSelectedIndex();
            int id = employeesList.get(index).getId();
            String firstName = employeesList.get(index).getFirstName();
            String secondName = employeesList.get(index).getSecondName();
            float payment = employeesList.get(index).getPayment();
            double salary = employeesList.get(index).getSalary();
            EmployeeDataWindow.display(id, firstName, secondName, payment, salary);
            return true;
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public static void exitProgram(){
        boolean answer = Alert.exitConfirm();
        if (answer){
            mainWindow.close();
        }
    }

    private void deleteEmployee() {
        try {
            int index = listView.getSelectionModel().getSelectedIndex();
            int id = employeesList.get(index).getId();
            employeesList.remove(index);
            showList();
        } catch (ArrayIndexOutOfBoundsException e){
           showList();
        }
    }


}
