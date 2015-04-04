import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class CompareTwoFile {


    public static void main(String args[]) throws IOException {


        System.out.println("Syntax: Compare <filename1> <filename2> ");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            System.out.print("Compare ");
            String[] cmdLine = in.readLine().split(" ");

            if (cmdLine[0].equals("exit")){
                in.close();
                System.exit(0);
            }

            if (cmdLine.length == 2) {
                try {
                    CompareRowByRow(cmdLine[0], cmdLine[1]);

                } catch (IOException e) {
                    System.out.println("Elerheto fajlok:");
                    File path = new File("tests");
                    for (File directory : path.listFiles())
                        System.out.println(directory.getName());
                }


            } else
                System.out.println("Syntax: Compare <filename1> <filename2> ");

        }
    }


    public static void CompareRowByRow(String filename1, String filename2) throws IOException {
        int hiba = 0;
        String[] elso = new String[10000];
        String[] cmd1;
        String[] masodik = new String[10000];
        String[] cmd2;

        int i = 0;
        int j = 0;

        FileInputStream file1 = new FileInputStream("tests/" + filename1 + ".txt");
        FileInputStream file2 = new FileInputStream("tests/" + filename2 + ".txt");

        BufferedReader br1 = new BufferedReader(new InputStreamReader(file1));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(file2));


        elso[i] = br1.readLine();
        masodik[i] = br2.readLine();


        while (elso[i] != null && masodik[i] != null && hiba!=1 ) {

            cmd1 = elso[i].split(" ");
            cmd2 = masodik[i].split(" ");


            if (!cmd1[0].equals("KisRobot1")) {
                j = 0;
                while (j < Max(cmd1.length-1,cmd2.length-1) && cmd1[j].equals(cmd2[j])) {j++;}

                if (!cmd1[j].equals(cmd2[j])) {
                    System.out.println("Hiba: " + (i+1) + ".sor " + (j+1) + ". szo");
                    System.out.println(filename1 + ".txt " + (i+1) + ". sora: " + elso[i]);
                    System.out.println(filename2 + ".txt " + (i+1) + ". sora: " + masodik[i]);
                    hiba = 1;
                }
            }


            i++;
            elso[i] = br1.readLine();
            masodik[i] = br2.readLine();


        }
        br1.close();
        br2.close();

        if (hiba == 0)
            System.out.println("Teljes egyezes!");

    }

    static int Max(int szam1, int szam2){
        if (szam1>szam2) return szam1;
        else return szam2;
    }
}