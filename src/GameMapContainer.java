import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/* A játékban a pélyán találhtó objektumok összességének tárolóosztálya.
    Itt tároljuk a robotokat és a csapdákat.
 */

public class GameMapContainer {
    Shell shell;

    // Pálya felbontása
    private Dimension resolution;
    private List<CleanerRobot> cleanerRobots;
    private List<PlayerRobot> playerRobots;
    private List<Trap> traps;


    //Létrehozzuk a megfelelő tárolókat és játék elemeket
    public GameMapContainer(Dimension resolution) {

        this.resolution = resolution;
        playerRobots = new ArrayList<PlayerRobot>();
        cleanerRobots = new ArrayList<CleanerRobot>();
        traps = new ArrayList<Trap>();
        shell.kimenet[++shell.outdb] = "Sikeres letrehozas: map[" + resolution.width + "," + resolution.height + "]";
    }

    // getterek
    public List<PlayerRobot> getPlayerRobots() {
        return playerRobots;
    }

    public List<CleanerRobot> getCleanerRobots() {
        return cleanerRobots;
    }

    public List<Trap> getTraps() {
        return traps;
    }


    //nagyrobot hozzáadása
    public void addPlayerRobot(PlayerRobot playerRobot) {
        playerRobots.add(playerRobot);
        shell.kimenet[++shell.outdb] = "Sikeres letrehozas: robot[" +
                playerRobot.getLocation().getX() + " ," + playerRobot.getLocation().getY() +", "+
                playerRobot.angle+" ,"+playerRobot.speed+"]";
    }

    //kisrobot hozzáadása
    public void addCleanerRobot(CleanerRobot cleanerRobot) {
        cleanerRobots.add(cleanerRobot);
        shell.kimenet[++shell.outdb] = "Sikeres letrehozas: clrobot[" +
                cleanerRobot.getLocation().getX() + "," + cleanerRobot.getLocation().getY() + "]";
    }

    //trap hozzáadása
    public void addTrap(Trap trap) {
        traps.add(trap);
        shell.kimenet[++shell.outdb] = "Sikeres letrehozas: trap[" + trap.getLocation().getX() + "," + trap.getLocation().getY() + "]";
    }

    public void removePlayerRobot(PlayerRobot playerRobot){
        playerRobots.remove(playerRobot);
        shell.kimenet[++shell.outdb] = "Robot" + (playerRobots.indexOf(playerRobot)+1) + "megsemmisult!";
    }
    public void removeCleanerRobot(CleanerRobot cleanerRobot){
        cleanerRobots.remove(cleanerRobot);
        shell.kimenet[++shell.outdb] = "KisRobot" + (cleanerRobots.indexOf(cleanerRobot)+1) + "megsemmisult!";
    }
    public void removeTrap(Trap trap){
        shell.kimenet[++shell.outdb] = "trap" + (traps.indexOf(trap)+1) + "megsemmisult!";
        traps.remove(trap);

    }
}
