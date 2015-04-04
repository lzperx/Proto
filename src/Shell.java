import java.awt.*;


public class Shell {

    //globális változók a kiiratáshoz
    public static String[] kimenet = new String[10000];
    public static int outdb = 0;  //kimeneti sor számláló
    public static int round = 0;
    public static int robotdb = 0;
    public static int RobotSorszam = 0; //nem 0-tól indexelt
    public static int RobotSorszam2 = 0;

    NewGame game;


    public void CreateManager(String[] sor) {
        switch (sor[1]) {
            case "map":
                game = new NewGame(Integer.parseInt(sor[2]), Integer.parseInt(sor[3]));
                break;
            case "robot":
                game.gameMap.addPlayerRobot(
                        new PlayerRobot(
                                new Point(Integer.parseInt(sor[2]), Integer.parseInt(sor[3])),
                                10,
                                Integer.parseInt(sor[4])));
                robotdb++;
                break;
            case "clrobot":
                game.gameMap.addCleanerRobot(
                        new CleanerRobot(new Point(Integer.parseInt(sor[2]), Integer.parseInt(sor[3])), 10));
                break;
            case "glue":
                game.gameMap.addTrap(new Glue(new Point(Integer.parseInt(sor[2]), Integer.parseInt(sor[3]))));
                break;
            case "oil":
                game.gameMap.addTrap(new Oil(new Point(Integer.parseInt(sor[2]), Integer.parseInt(sor[3]))));
                break;
        }
    }


    //RoundManager: a controllMinions funkcióját tölti be a protoban
    public void RoundManager(String[] sor) {

        RobotSorszam++;
        RobotSorszam= (RobotSorszam>robotdb)? RobotSorszam=1: RobotSorszam;

        PlayerRobot robot = game.gameMap.getPlayerRobots().get(RobotSorszam-1);


        if (RobotSorszam  == 1) {
            kimenet[++outdb] = "Round" + (++round); //körök száma
            game.controller.ControlCleanerRobots();

        }


        switch (sor[0]) {
            case "speed":
                robot.Speed(Integer.parseInt(sor[1]));
                break;
            case "right":
                robot.TurnRight(Integer.parseInt(sor[1]));
                break;
            case "left":
                robot.TurnLeft(Integer.parseInt(sor[1]));
                break;
            case "putoil":
                    if(robot.PutOil())
                    game.gameMap.addTrap(new Oil(robot.getLocation()));
                break;
            case "putglue":
                if(robot.PutGlue())
                    game.gameMap.addTrap(new Glue(robot.getLocation()));
                break;
        }


        game.controller.ControlPlayerRobot(robot);

        if (RobotSorszam==robotdb) {
            game.controller.removeOldTraps();

        }



    }

}
