import java.io.*;
import java.util.Formatter;

public class FileHandler {

    public static final String PATH = "employees.txt";

    public static File openFile() throws IOException {

        File file = new File(PATH);

        if (file.exists()) {
            return file;
        }
        else {
            System.out.println("createFile");
            createFile(PATH);
            file = new File(PATH);
            return file;
        }
    }

    public static void createFile(String path) throws IOException{
        new Formatter(path);
    }

    public static boolean writeToFile(String addLine){
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(PATH, true)));
            out.print(addLine);
            out.close();
            return true;
        }
        catch (IOException e){
            return false;
        }
    }

}
