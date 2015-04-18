import java.io.*;

public class Main {

    static Shell shell = new Shell();
    private enum ProcessingEnum{
        create, startgame, exitgame, speed, right, left, putoil, putglue
    }

    public static void main(String args[]) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Teszt file:  ");
            String[] cmdLine = in.readLine().split(" ");

            if (cmdLine[0].equals("exit")) {
                in.close();
                System.exit(0);
            }

            if (cmdLine.length == 1) {
                try {
                    String[] bemenet = Beolvasas(cmdLine[0]);
                    shell.kimenet = new String [10000];
                    shell.kimenet[0] = "Sikeres beolvasas: " + cmdLine[0] + ".txt";
                    Feldolgozas(bemenet);
                    Kiiratas(cmdLine[0]);

                } catch (IOException e) {
                	System.out.println(e.getMessage());
                }


            } else
                System.out.println("Syntax: <testname> ");
        }
    }


    public static String[] Beolvasas(String filename) throws IOException {

        //FileInputStream file = new FileInputStream("tests/" + filename + ".txt");
        //nem tudom miért, de conzolból a ../ kell neki, különben nullpointerexception
        //de legalább így IntelliJ alatt nem fut :D, szóval a másikat használd
        FileInputStream file = new FileInputStream("../tests/" + filename + ".txt");

        BufferedReader br = new BufferedReader(new InputStreamReader(file));
        String[] content = new String[10000];

        shell.outdb = 0;
        shell.round = 0;
        shell.robotdb = 0;
        shell.RobotSorszam = 0;

        int i = 0;
        content[i] = br.readLine();

        while (content[i++] != null) {
            content[i] = br.readLine();
        }

        return content;
    }

    public static void Feldolgozas(String[] bemenet) {

        String[] sor = null;
        int i = 0;

        while (bemenet[i++] != null) {
            sor = bemenet[i-1].split(" ");
            ProcessingEnum process = ProcessingEnum.valueOf(sor[0]);
            switch (process) {
                case create:
                    shell.CreateManager(sor);
                    break;
                case startgame:
                    shell.kimenet[++shell.outdb] = "A jatek sikeresen elindult!";
                    break;
                case exitgame:
                    shell.kimenet[++shell.outdb] = "A jatek sikeresen leallt!";
                    break;
                default:
                    shell.RoundManager(sor);
            }

        }
    }

    public static void Kiiratas(String filename) throws IOException {
        File yourFile = new File("../tests/"+filename+  "_out.txt");
       // File yourFile = new File("tests/"+filename+  "_out.txt");
        PrintWriter writer = new PrintWriter(yourFile, "UTF-8");
        for (int i = 0; i <=shell.outdb; i++)
            writer.println(shell.kimenet[i]);
        writer.close();

    }

}
