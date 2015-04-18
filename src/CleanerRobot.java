import java.awt.*;

public class CleanerRobot extends GameElements {
    Shell shell;
    public int name; //CSAK INEIGLENESEN a protoban

    protected Point nextPosition;   //A robot ahova ugrani fog legközelebb
    public int speed=1;               // A robot aktuális sebessége
    public double angle=0;         //A robot aktuális szöge

    public boolean isCleaning=false; //igaz, ha éppen takarít
    public int TimeOfCleaning=5;    //ennyi ideig(körig) takarítja a foltot
    public int cleaningcount = 0;    // ennyi kört tisztított már a TimeofCleaning-ből

    public CleanerRobot(Point location, int hitbox) {
        super(location, hitbox);
    }

    public void Jump() {
        if(!isCleaning){
            nextPosition = new Point(
                    (int) (speed * Math.cos(Math.toRadians(angle))) + (int) location.getX(),
                    (int) (speed * Math.sin(Math.toRadians(angle))) + (int) location.getY());

            location = nextPosition;
        }

    }



    /*****************************************************************************************************************/
    /*****                                         VISITOR PATTERN                                               *****/
    /*****************************************************************************************************************/


    /* A robotok csapdával való érintkezését oldja meg,
        minden csapda saját magát adja át a visit metódusnak,
        így a megfelelő kód fut le.
     */
    void visit(Oil oil){

        cleaningcount++;

    }

    void visit(Glue glue){

        cleaningcount++;

    }
    void visit(CleanerRobot cleanerRobot){
        //todo elpattanás
        /*angle-=180;
        angle =(angle)%360;
        if (angle<0) angle+=360;*/
    }
    void visit(PlayerRobot playerRobot){

    }

    // visitor
    @Override
    public void accept(PlayerRobot R2D2) {
        //a kisrobot megsemmisül, mert ütközött egy nagyrobottal
        shell.kimenet[++shell.outdb] = "    Robot" + R2D2.name + " es KisRobot"+name+" utkoztek!";
    }
    @Override
    public void accept(CleanerRobot R2D2) {
        shell.kimenet[++shell.outdb] = "    KisRobot" + name + " es KisRobot"+R2D2.name+" utkoztek!";

        R2D2.visit(this);
        //todo elpattanás
        /*angle-=180;
        angle =(angle)%360;
        if (angle<0) angle+=360;*/

    }

}