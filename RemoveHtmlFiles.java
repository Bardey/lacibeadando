import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class RemoveHtmlFiles {

    public static void delete(char partition, String pathFrom) {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", partition + ": && cd " + pathFrom + "&& DEL /S /Q *.html");
        builder.redirectErrorStream(true);
        Process p = null;
        try {
            p = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            try {
                line = r.readLine();
                if (line == null) { break; }
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        //Read the path
        Scanner sc = new Scanner(System.in);

        System.out.print("Location: ");
        String from = sc.nextLine();

        char partition = from.split("/")[0].charAt(0);

        //Delete HTML files
        System.out.println();
        System.out.println("Deleting HTML files...");

        delete(partition, from);

        System.out.println();
        System.out.println("HTML files successfully removed from " + from);
    }

}
