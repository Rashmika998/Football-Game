package FootBall;

import java.util.Random;

/**
 *
 * @author Rashmika
 */
public class Teams implements Comparable {
    
    private String teamName;
    private Person[] playerArray;
    private int pointsTotal;
    private int goalsTotal;
    PlayerData pd = new PlayerData();
    
    public Teams() {} //default constructor
    
    /*parameterized constructors to store team data*/
    public Teams(String teamName) {
        this.teamName = teamName;
    }
    
    public Teams(String teamName, Person[] players, PlayerData pd) {
        this(teamName);
        this.playerArray = players;
        this.pd = pd;
    }
    
    //function to increase the goals
    public void incGoalsTotal(int goals) {
        this.setGoalsTotal(this.getGoalsTotal() + goals);
    }
    
    public int compareTo(Object theTeam) {
        int returnValue = -1;
        
        if(this.getPointsTotal() < ((Teams)theTeam).getPointsTotal()) {
            returnValue = 1;
        } else if (this.getPointsTotal() == ((Teams)theTeam).getPointsTotal()) {
            if(this.getGoalsTotal() < ((Teams)theTeam).getGoalsTotal()) {
                returnValue = 1;
            }
        }
        
        return returnValue;
    }

    //function to display team players
    public Person displayTeamPlayers(int i){
        return playerArray[i];
    }
    
    /**
     * @return the teamName
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName the teamName to set
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return the playerArray
     */
    public Person[] getPlayerArray() {
        return playerArray;
    }
    
    public Person getPlayer(int i){
        return playerArray[i];
    }
    
    public Person replacePlayerOne(int i){
        Random rand=new Random();  
        int randVal=rand.nextInt(pd.reserveOne.size());
        playerArray[i] = pd.reserveOne.get(randVal);
        pd.reserveOne.remove(randVal);
        return playerArray[i];
    }
    
     public Person replacePlayerTwo(int i){
        Random rand=new Random();  
        int randVal=rand.nextInt(pd.reserveTwo.size());
        playerArray[i] = pd.reserveTwo.get(randVal);
        pd.reserveTwo.remove(randVal);
        return playerArray[i];
    }
    
    public void addName(Person goalKeeper){
        this.playerArray[10] = goalKeeper;
    }

    /**
     * @param playerArray the playerArray to set
     */
    public void setPlayerArray(Person[] playerArray) {
        this.playerArray = playerArray;
    }

    /**
     * @return the pointsTotal
     */
    public int getPointsTotal() {
        return pointsTotal;
    }

    /**
     * @param pointsTotal the pointsTotal to set
     */
    public void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    /**
     * @return the goalsTotal
     */
    public int getGoalsTotal() {
        return goalsTotal;
    }

    /**
     * @param goalsTotal the goalsTotal to set
     */
    public void setGoalsTotal(int goalsTotal) {
        this.goalsTotal = goalsTotal;
    }
}