package FootBall;

/**
 *
 * @author Rashmika
 */
public abstract class GameStage {
    private Teams team;
    private Person player;
    private double theTime;

    /**
     * @return the theTeam
     */
    public Teams getTheTeam() {
        return team;
    }

    /**
     * @param theTeam the theTeam to set
     */
    public void setTheTeam(Teams team) {
        this.team = team;
    }

    /**
     * @return the thePlayer
     */
    public Person getThePlayer() {
        return player;
    }

    /**
     * @param Player Player to set
     */
    public void setThePlayer(Person player) {
        this.player = player;
    }

    /**
     * @return the theTime
     */
    public double getTheTime() {
        return theTime;
    }

    /**
     * @param theTime the theTime to set
     */
    public void setTheTime(double theTime) {
        this.theTime = theTime;
    }
}
