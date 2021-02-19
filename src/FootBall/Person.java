package FootBall;

/**
 *
 * @author Rashmika
 */
public class Person {
    
    private String playerName;
    private int goalsScored;
    
    public Person() {} //default constructor
    
    //parameterized constructor to store the name
    public Person(String playerName) {
        this.playerName = playerName;
    }
    
    //function to display the message of the perticular person
    public void displayMessage(String msg){
        System.out.print(msg);
    }
    
    //function to increase the number of goals scored by a team
    public void incGoalsScored() {
        this.goalsScored++;
    }

    //function to get the team players names
    public String getPlayerName() {
        return playerName;
    }

    //function to set the players names
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    
    //function to return the goals scored
    public int getGoalsScored() {
        return goalsScored;
    }

    //function to set the goals scored
    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }
}