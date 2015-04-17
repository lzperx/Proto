import java.awt.*;

public class PlayerRobot extends GameElements {
    Shell shell;

    protected Point nextPosition;   //A robot ahova ugrani fog legközelebb
    public int speed;               // A robot aktuális sebessége
    public double angle;         //A robot aktuális szöge
    public double distance;    //összesen megtett távolság
    public int ammountofOil;        //robot olaj készlete
    public int ammountofGlue;       //robot ragacs készlete

    public static enum robotState {NORMAL, OILED}

    public robotState state = robotState.NORMAL;     //robot talajhoz viszonyított állapota

        /* robotState= enumeráció a robot aktuális állapotara.
          Ez határozza meg, hogy van-e lehetőség iránymódosításra az aktuális földetéréskor.
          Az olajt paraméterül váró visit metódus ezt állítja be.
       */

    public PlayerRobot(Point location, int hitbox, double angle) {
        super(location, hitbox);
        speed = 0;    //kezdősebessége
        distance = 0;
        this.angle = angle;
        ammountofGlue = 3;
        ammountofOil = 3;
    }

    public void Jump() {

        nextPosition = new Point(
                (int) (speed * Math.cos(Math.toRadians(angle))) + (int) location.getX(),
                (int) (speed * Math.sin(Math.toRadians(angle))) + (int) location.getY());

        distance += nextPosition.distance(location);
        location = nextPosition;
    }

    public void TurnLeft(int change) {
        angle = (angle - change) % 360;
        //ha mínusz érték, akkor még hozzáadom a 360-hoz, így [0-360] tartományban lesz
        if (angle < 0) angle += 360;
        shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " balra fordulas: " + change;

    }

    public void TurnRight(int change) {
        angle = (angle + change) % 360;
        shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " jobbra fordulas: " + change;

    }

    public void Speed(int change) {

        if (state == robotState.NORMAL){
            shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " sebesseg-valtoztatas: " + change;
            speed += change;
        }

    }

    public boolean PutGlue() {
        if (ammountofGlue > 0) {
            ammountofGlue--;
            shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " lerakott egy ragacsot!";
            shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " lerakhat meg [" + ammountofGlue + "] db ragacsot";
            return true;
        }
        shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " lerakhat meg [" + ammountofGlue + "] db ragacsot";
        return false;
    }

    public boolean PutOil() {
        if (ammountofOil > 0) {
            ammountofOil--;
            shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam +
                    " lerakott egy olajat[X= "+this.getLocation().getX()+" , Y= "+this.getLocation().getY()+"]";
            shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " lerakhat meg [" + ammountofOil + "] db olajat";
            return true;
        }
        shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " lerakhat meg [" + ammountofOil + "] db olajat";
        return false;
    }


    /*****************************************************************************************************************/
    /*****                                         VISITOR PATTERN                                               *****/
    /**
     * *************************************************************************************************************
     */


    /* A robotok csapdával való érintkezését oldja meg,
        minden csapda saját magát adja át a visit metódusnak,
        így a megfelelő kód fut le.
     */
    void visit(Oil oil) {
        state = robotState.OILED;
        shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " olajra lepett!";
    }

    void visit(Glue glue) {

        speed /= 2;
        shell.kimenet[++shell.outdb] = "Robot" + shell.RobotSorszam + " ragacsra lepett!";
    }

    void visit(PlayerRobot playerRobot) {
       //megsemmisült, a GameMapContainer fogja kiírni a halálát
    }

    void visit(CleanerRobot cleanerRobot){

    }
    @Override
    public void accept(PlayerRobot C3PO) {
        if (speed > C3PO.speed)
            C3PO.visit(this);
        //külöben megsemmisült, a GameMapContainer fogja kiírni a halálát


    }

    @Override
    public void accept(CleanerRobot R2D2) {
        //a kisrobot megsemmisül, mert ütközött egy nagyrobottal

    }

}
