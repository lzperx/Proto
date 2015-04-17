import java.awt.*;
import java.lang.Math;


/*A teljes játékirányításért felelős objektum.*/

public class GameControl {
    Shell shell;

    /*   Referencia a játékelemeket tároló objektumra,
     *   így tudjuk hogy milyen robotok vannak, és mi hol van a pályán.
     *   Ezt a konstruktorban kell átadni.
    */

    private GameMapContainer gameMapContainer;


    public GameControl(GameMapContainer gameMapContainer) {
        this.gameMapContainer = gameMapContainer;
    }

    //irányítja 1 kör erejéig a nagyrobotot
    public void ControlPlayerRobot(PlayerRobot robot) {
        robot.Jump();
        boolean isAlive = Collision(robot);

        if (isAlive) {
            shell.kimenet[++shell.outdb] = "    Robot" + shell.RobotSorszam +
                    " [ X = " + robot.getLocation().getX() + " , Y = " + robot.getLocation().getY() +
                    ", Angle = " + robot.angle + ", " + "Speed = " + robot.speed + "]";
        }

    }

    //Minden kör elején lekezeli az összes kisrobotot
    public void ControlCleanerRobots() {

        for (CleanerRobot robot : gameMapContainer.getCleanerRobots()) {


            //beállítjuk a legközelebbi folt felé
            robot.angle = setAngleofCleanerRobot(robot);

            //mozgatjuk a robotot, ha épp takarít, akkor a takarítási idő csökken
            robot.Jump();
            //üztökést detektálunk: frissülnek az adatok
            Boolean isAlive = Collision(robot);

            //vizsgáljuk, hogy él még-e, majd kiírjuk, ha igen
            if (isAlive) {
                shell.kimenet[++shell.outdb] = "    KisRobot" + (gameMapContainer.getCleanerRobots().indexOf(robot)+1)+
                        " [ X = " + robot.getLocation().getX() + " , Y = " + robot.getLocation().getY() +
                        ", Angle = " + robot.angle +
                        ", Speed = " + robot.speed + ", " + "Takarit = " + robot.isCleaning +
                        ", Takaritasi ido = " + robot.TimeOfCleaning + ", Aktualis takaritas = " + robot.cleaningcount + "]";
            }
        }

    }


    private boolean Collision(PlayerRobot C3PO) {

        //Csapdákkal való ütközés lekezelése
        for (Trap itsATrap : gameMapContainer.getTraps()) {

            if (C3PO.getLocation().distance(itsATrap.getLocation()) < (C3PO.getHitbox() + itsATrap.getHitbox())) {
                itsATrap.accept(C3PO);
            }
        }

        //kisrobotokkal való ütközés lekezelése
        for (CleanerRobot c : gameMapContainer.getCleanerRobots()) {
            if (C3PO.getLocation().distance(c.getLocation()) < (C3PO.getHitbox() + c.getHitbox())) {
                gameMapContainer.addTrap(new Oil(c.getLocation()));
                gameMapContainer.removeCleanerRobot(c);

            }
        }


        //Nagyrobotokkal való ütközés
        for (PlayerRobot R2D2 : gameMapContainer.getPlayerRobots()) {

            if (C3PO != R2D2) {
                if (C3PO.getLocation().distance(R2D2.getLocation()) < (C3PO.getHitbox() + R2D2.getHitbox())) {
                    R2D2.accept(C3PO);


                    if (C3PO.speed > R2D2.speed)
                        gameMapContainer.removePlayerRobot(R2D2);
                    else {
                        gameMapContainer.removePlayerRobot(C3PO);
                        return false;
                    }

                }
            }
        }

        return true;
    }


    private boolean Collision(CleanerRobot C3PO) {

        //Csapdákkal való ütközés lekezelése

        for (Trap trap : gameMapContainer.getTraps()) {
            if ( trap.getLocation() == GetMinDistanceTrapLocation(C3PO) ) {
                Trap closest = trap;
                if ( C3PO.getLocation().distance(closest.getLocation()) < (closest.getHitbox())) {
                    C3PO.location = closest.getLocation();
                    CleaningTrap(C3PO);
                }
            }
        }
        //kisrobotokkal való ütközés lekezelése
        for (CleanerRobot R2D2 : gameMapContainer.getCleanerRobots()) {
            if (C3PO != R2D2)
                if (C3PO.getLocation().distance(R2D2.getLocation()) < (C3PO.getHitbox() + R2D2.getHitbox()))
                    R2D2.accept(C3PO);
        }

        //Nagyrobotokkal való ütközés
        for (PlayerRobot R2D2 : gameMapContainer.getPlayerRobots()) {
            if (C3PO.getLocation().distance(R2D2.getLocation()) < (C3PO.getHitbox() + R2D2.getHitbox())) {
                gameMapContainer.addTrap(new Oil(C3PO.getLocation()));
                gameMapContainer.removeCleanerRobot(C3PO);
                return false;
            }

        }

        return true;
    }

    /*private void CleaningTrap(CleanerRobot C3PO) {
        boolean event = false;
        //Csapdákkal való ütközés lekezelése
        for (Trap itsATrap : gameMapContainer.getTraps()) {
            if (C3PO.getLocation().distance(itsATrap.getLocation()) < (C3PO.getHitbox() + itsATrap.getHitbox())) {
                event = true;
                if (C3PO.cleaningcount == C3PO.TimeOfCleaning) {
                    gameMapContainer.removeTrap(itsATrap);
                    shell.kimenet[++shell.outdb] = "Csapda feltakaritva." + itsATrap.getLocation();
                    C3PO.cleaningcount = 0;
                    C3PO.isCleaning = false;
                } else {
                    C3PO.isCleaning = true;
                    itsATrap.accept(C3PO);
                }

            }
        }

        //ha nem állunk folton, akkor keressük a következőt
        if (!event) {
            C3PO.isCleaning = false;
        }
    }*/

/*Új cleaning by Jánoky*/
    private void CleaningTrap (CleanerRobot cleaner){
        Trap cleanupThis = null;

        for (Trap trap : gameMapContainer.getTraps()){
            if (trap.getLocation() == GetMinDistanceTrapLocation(cleaner)){
                cleanupThis = trap;
            }
        }
        if ( cleanupThis != null){
            if ( cleaner.cleaningcount == cleaner.TimeOfCleaning){
                gameMapContainer.removeTrap(cleanupThis);
                shell.kimenet[++shell.outdb] = "Csapda feltakaritva." + cleanupThis.getLocation();
                cleaner.cleaningcount = 0;
                cleaner.isCleaning = false;
            }
            else{
                cleaner.isCleaning = true;
                cleanupThis.accept(cleaner);
            }
        }
    }



    private double setAngleofCleanerRobot(CleanerRobot robot) {

        double angle = 0;
        Point trapLocation = GetMinDistanceTrapLocation(robot);

        if (trapLocation.getX() < 0) {
            shell.kimenet[++shell.outdb] = "Nincsen csapda a palyan.";
            //ekkor baktatunk a sajat szögünkkel tovább
            return robot.angle;
        }
        else {
            double x = robot.getLocation().getX() - trapLocation.getX();
            double y = robot.getLocation().getY() - trapLocation.getY();
            double atmero = robot.getLocation().distance(trapLocation);

            if (x > 0 && y >= 0)
                angle = 180+Math.toDegrees(Math.asin(y / atmero));//jó
            if (x >= 0 && y < 0)
                angle = 180+Math.toDegrees(Math.asin(y / atmero));//jó
            if (x <= 0 && y > 0)
                angle = 360 - Math.toDegrees(Math.asin(y / atmero));//jó
            if (x < 0 && y <= 0)
                angle =  (-1)*Math.toDegrees(Math.asin(y / atmero));//jó

            return Math.round(angle);
        }


    }

    //visszaadja a legközelebbi folt koordinátáit
    private Point GetMinDistanceTrapLocation(CleanerRobot robot) {
        double minValue = 10000;
        int minTrapIndex = -1;
        for (Trap trap : gameMapContainer.getTraps()) {
            if (robot.getLocation().distance(trap.getLocation()) < minValue) {
                minValue = robot.getLocation().distance(trap.getLocation());
                minTrapIndex = gameMapContainer.getTraps().indexOf(trap);
            }
        }
        return minTrapIndex == (-1) ? new Point(-1, -1) : gameMapContainer.getTraps().get(minTrapIndex).getLocation();
    }

    //szárít az olajon, és törli az száraz ill elkopott csapdákat
    //minden kör végén kell meghívni
    public void removeOldTraps() {

        for (Trap csapda : gameMapContainer.getTraps()) {

            if (csapda.getTimeToLive() <= 0) {
                gameMapContainer.removeTrap(csapda);
                break;
            }

            csapda.dry();
        }
    }
}
