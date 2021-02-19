package FootBall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rashmika
 */
public class PlayGame {
    
    //private attributes to store data
    private Teams teamOne;
    private Teams teamTwo;
    private Teams prevTeam;
    private Person refree;
    private Person doctor;
    private Person prevPlayer;
    private Person lastGoalplayer;
    private Teams lastGoalTeam;
    private GameStage currentState;
    private GameStage prevState;
    private GameStage tempState;
    private int teamOneGoals;
    private int teamTwoGoals;
    public ArrayList<Person> injuredPlayersOne; //store injured players of team 01
    public ArrayList<Person> injuredPlayersTwo; //store injured players of team 02
    showPic pic = new showPic();
    
    public PlayGame(){} //default constructor
    
    //parameterized constructor to store tha game data
    public PlayGame (Teams teamOne, Teams teamTwo, Person refree, Person doctor) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.refree = refree;
        this.doctor = doctor;
    }
    
    public void playGame() {
        injuredPlayersOne = new ArrayList();
        injuredPlayersTwo = new ArrayList();
        
        prevPlayer = null;
        prevTeam = null;
        currentState = null; //handles all the game stages
        tempState = null; //temporary game stage to store when a goal is missed by a player
        prevState = null; //handles all the game stages
        int noOfGoals = 0;
        
        //number of goals in both teams
        teamOneGoals = 0;
        teamTwoGoals = 0;
        
        boolean missed; //attribute to check whether the goal is missed
        boolean injured; //attribute to check whether a player is injured
        boolean goal;
        boolean ballOut;
        for(int i = 1; i <= 90; i++) {
            
            //assign false initially
            goal = false;
            missed = false;
            injured = false;
            ballOut = false;
            
            //print console to the first half and the match begins
            if(i == 1){
                refree.displayMessage("\n\n\t\t\tFIRST HALF\t\t\n\n");
                refree.displayMessage("Refree: \"TIME STARTED\"\n\n");
            }
            
            //generate a random number to find a random player in a team(without goalkeeper)
            Random rand=new Random();  
            int randVal=rand.nextInt(10);
            double val = Math.random();
            //if some action happend in the corresponding minute(goal, injury, ball passing)
            if(val > 0.7) {
      
                //if a player tries to hit a goal
                if(Math.random() > 0.7){
                    if(Math.random() > 0.8){ //if a player hits a goal set the current stage as GOAL
                        currentState = new GoalScored();
                        goal = true;
                        noOfGoals++;
                    }
                    
                    //if not the goal is saved by the goal keeper and set the current stage as goal missed
                    else{
                        currentState = new GoalMissed();
                        missed = true;
                    }
                }
                
                //if the ball is with some player without any stage
                else
                    currentState =  new Possession();
                
                //if an player gets injury set the current stage as injured
                if(Math.random() > 0.9 && !goal && !missed && !ballOut){
                    injured = true;
                    currentState = new Injured();
                }
                
                //copy the current stage to temporary created stage
                tempState = currentState;
                
                //select the team who gets the ball and set a random player from the team to find who has the ball
                currentState.setTheTeam(Math.random() > 0.5 ? teamOne: teamTwo);       
                currentState.setThePlayer(currentState.getTheTeam().getPlayerArray()[(int) (randVal)]);
                currentState.setTheTime(i); //set the time
                
                //if the goal was missed by a player display who missed it and his team and the time he missed it
                if(missed == true){
                    System.out.print(currentState+" after "+currentState.getTheTime()+" mins by "+
                    currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+" and Goal was saved by ");
                
                    //store the goal keeper's team using temporary created game stage 
                    if(currentState.getTheTeam() == teamOne)
                        tempState.setTheTeam(teamTwo);
                
                    else
                        tempState.setTheTeam(teamOne);
                
                    //set the goal keepers name of the opponent team
                    tempState.setThePlayer(currentState.getTheTeam().getPlayer(10));
                    
                    //display which goalkeeper from which team saved the goal
                    System.out.print(tempState.getThePlayer().getPlayerName()+" of "+tempState.getTheTeam().getTeamName()+"!\n\n");
                }
                
                /*if a player was injured display which team, which player at which time is injured and 
                 then replace the player with reserved player*/
                else if(injured == true){
                    doctor.displayMessage(currentState+" , "+currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()
                    +" was injured after "+currentState.getTheTime()+" mins and "+doctor.getPlayerName()+" treated him but he was replaced by ");
                    
                    //check the team and then add the injured player to correct variable and replace him with new player
                    if(currentState.getTheTeam() == teamOne){
                        injuredPlayersOne.add(currentState.getTheTeam().getPlayerArray()[(int) (randVal)]); //add the injured player
                        currentState.setThePlayer(currentState.getTheTeam().replacePlayerOne(randVal)); //replace him with a reserved player
                    }
                
                    else{
                        injuredPlayersTwo.add(currentState.getTheTeam().getPlayerArray()[(int) (randVal)]);
                        currentState.setThePlayer(currentState.getTheTeam().replacePlayerTwo(randVal));
                    }
                    
                    doctor.displayMessage(currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+"!\n\n");
                }
                 
                //if the ball was passed to another player in the same team or opponent team or a goal was scored
                else{
                    prevTeam = currentState.getTheTeam();
                    prevPlayer = currentState.getTheTeam().getPlayerArray()[(int) (randVal)];
                    System.out.print(currentState+" after "+currentState.getTheTime()+" mins by "+
                    currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+"!\n\n");
                }
                
                //if a goal was scored, increase the number of goals of the team
                if (currentState instanceof GoalScored) {
                    if (currentState.getTheTeam() == teamOne) {
                        lastGoalTeam = teamOne; //store the team name who scored the last goal
                        teamOneGoals++;
                        teamOne.incGoalsTotal(1);
                    }
                    
                    else {
                        lastGoalTeam = teamTwo; //store the team name who scored the last goal
                        teamTwoGoals++;
                        teamTwo.incGoalsTotal(1);
                    }
                    
                    //increase the goals scored by the player
                    currentState.getThePlayer().incGoalsScored();
                    lastGoalplayer = currentState.getThePlayer(); //store the name of player who scored last goal
                }
                
                prevState = currentState;
            }
            
            else if(val <= 0.1){
                refree.displayMessage("Refree: \"BALL GOES OUT OF FIELD after "+i+" mins\"\n\n");
            }
            
            else if(prevState instanceof Possession){
                int randVal1=rand.nextInt(10);
                while(randVal==randVal1)
                    randVal1=rand.nextInt(10);
                
                prevState = new BallReceived();
                currentState = new BallReceived();
                    
                //select the team who gets the ball and set a random player from the team to find who has the ball
                currentState.setTheTeam(Math.random() > 0.5 ? teamOne: teamTwo);       
                currentState.setThePlayer(currentState.getTheTeam().getPlayerArray()[(int) (randVal1)]);
                currentState.setTheTime(i); //set the time
                    
                System.out.print(currentState+" after "+currentState.getTheTime()+" mins by "+
                currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+" which passed by "
                +prevPlayer.getPlayerName()+" of "+prevTeam.getTeamName()+"!\n\n");
            }
            
            //display the half time and the stats of the match
            if(i == 45){
                try {
                    refree.displayMessage("Refree: \"TIME STOPPED, 1st Half is Finished, 15 mins Break Given!\"");
                    refree.displayMessage("\n\n\t\t\t  TIME : 45 mins\t\t\n");
                    System.out.print("\t\t     "+teamOne.getTeamName()+" ("+ teamOneGoals + " - " + teamTwoGoals + ") "+teamTwo.getTeamName());
                    if(noOfGoals == 0)
                        System.out.println("\n\t\tNo goals were scored by any team");
                    else
                        System.out.println("\n\tLast Goal was scored by "+lastGoalplayer.getPlayerName()+" of "+lastGoalTeam.getTeamName());
                    System.out.println("\t\t\tTIME LEFT: "+(90-i)+" mins");
                    
                    //print the injured players
                    System.out.print("\t  INJURED PLAYERS: ");
                    if(injuredPlayersOne.isEmpty() && injuredPlayersTwo.isEmpty()) //if there are no inured players
                        System.out.println("No players were injured in both teams!");
                    
                    else{
                        //if there are injured players in team 01
                        if(!injuredPlayersOne.isEmpty()){
                            for(int j=0;j<injuredPlayersOne.size();j++){
                                System.out.print(injuredPlayersOne.get(j).getPlayerName()+"( "+teamOne.getTeamName()+" ) ");
                            }
                        }
                        
                        //if there are injured players in team 02
                        if(!injuredPlayersTwo.isEmpty()){
                            for(int j=0;j<injuredPlayersTwo.size();j++)
                                System.out.print(injuredPlayersTwo.get(j).getPlayerName()+"("+teamTwo.getTeamName()+" ) ");
                        }
                            
                    }
                    refree.displayMessage("\n\n*************************HALF TIME*************************\n\n");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
                
                //display the second half
                refree.displayMessage("\n\t\t\tSECOND HALF\t\t\n\n");
                refree.displayMessage("Refree: \"TIME STARTED\"\n\n");
                
            }
            
            //end of the match and display the stats
            if(i == 90){
                refree.displayMessage("\nRefree: \"TIME STOPPED, 2nd Half is Finished!\"\n");
                refree.displayMessage("\n\t\t\t  TIME : 90 mins\t\t\n");
                System.out.print("\t\t     "+teamOne.getTeamName()+" ("+ teamOneGoals + " - " + teamTwoGoals + ") "+teamTwo.getTeamName());
                if(noOfGoals == 0)
                    System.out.println("\n\t\tNo goals were scored by any team");
                else
                    System.out.println("\n\tLast Goal was scored by "+lastGoalplayer.getPlayerName()+" of "+lastGoalTeam.getTeamName());
                System.out.print("\t  INJURED PLAYERS: "); 
                if(injuredPlayersOne.isEmpty() && injuredPlayersTwo.isEmpty())
                        System.out.println("No players were injured in both teams!");
                    
                else{
                    if(!injuredPlayersOne.isEmpty()){
                        for(int j=0;j<injuredPlayersOne.size();j++)
                            System.out.print(injuredPlayersOne.get(j).getPlayerName()+"( "+teamOne.getTeamName()+" ) ");
                    }
                        
                    if(!injuredPlayersTwo.isEmpty()){
                        for(int j=0;j<injuredPlayersTwo.size();j++)
                            System.out.print(injuredPlayersTwo.get(j).getPlayerName()+"("+teamTwo.getTeamName()+" ) ");
                    }
                            
                }
                System.out.println();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //if the game is drawn play extra time
        if(teamOneGoals == teamTwoGoals) {
            
            Random rand=new Random();
            int randVal=rand.nextInt(10);
            
            refree.displayMessage("\nRefree:\"Match ended in a draw!\n");
            refree.displayMessage("\n**************************EXTRA TIME**************************\n\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i=91;i<=120;i++){
                
                goal = false;
                injured = false;
                missed = false;
                
                //first half of the extra time
                if(i == 91){
                    refree.displayMessage("\t\t\tFIRST HALF(EXTRA TIME)\t\t\n\n");
                    refree.displayMessage("Refree: \"TIME STARTED\"\n\n");
                }
                double val1 = Math.random();
                
                //same functionality as above
                if(val1 > 0.7){
                
                if(Math.random() > 0.7){
                    if(Math.random() > 0.8){ //if  a player hits a goal set the current stage as GOAL
                        currentState = new GoalScored();
                        goal = true;
                        noOfGoals++;
                    }
                    
                    //if not the goal is saved by the goal keeper and set the current stage as goal missed
                    else{
                        currentState = new GoalMissed();
                        missed = true;
                    }
                }
                
                //if the ball is passed without any stage
                else
                    currentState =  new Possession();
                
                //if an player gets injury set the current stage as injured
                if(Math.random() > 0.9 && !goal && !missed){
                    injured = true;
                    currentState = new Injured();
                }
                
                //copy the current stage to temporary created stage
                tempState = currentState;
                
                //select the team who gets the ball and set a random player from the team to find who has the ball
                currentState.setTheTeam(Math.random() > 0.5 ? teamOne: teamTwo);       
                currentState.setThePlayer(currentState.getTheTeam().getPlayerArray()[(int) (randVal)]);
                currentState.setTheTime(i); //set the time
                
                //if the goal was missed by a player display who missed it and his team and the time he missed it
                if(missed == true){
                    System.out.print(currentState+" after "+currentState.getTheTime()+" mins by "+
                    currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+" and Goal was saved by ");
                
                    //store the goal keeper's team using temporary created game stage 
                    if(currentState.getTheTeam() == teamOne)
                        tempState.setTheTeam(teamTwo);
                
                    else
                        tempState.setTheTeam(teamOne);
                
                    //set the goal keepers name of the opponent team
                    tempState.setThePlayer(currentState.getTheTeam().getPlayer(10));
                    
                    //display which goalkeeper from which team saved the goal
                    System.out.print(tempState.getThePlayer().getPlayerName()+" of "+tempState.getTheTeam().getTeamName()+"!\n\n");
                }
                
                /*if a player was injured display which team, which player at which time is injured and 
                 then replaced by a reserved player of the same team*/
                else if(injured == true){
                    doctor.displayMessage(currentState+" , "+currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()
                    +" was injured after "+currentState.getTheTime()+" mins and "+doctor.getPlayerName()+" treated hime but he was replaced by ");
                    if(currentState.getTheTeam() == teamOne){
                        injuredPlayersOne.add(currentState.getTheTeam().getPlayerArray()[(int) (randVal)]);
                        currentState.setThePlayer(currentState.getTheTeam().replacePlayerOne(randVal));
                    }
                
                    else{
                        injuredPlayersTwo.add(currentState.getTheTeam().getPlayerArray()[(int) (randVal)]);
                        currentState.setThePlayer(currentState.getTheTeam().replacePlayerTwo(randVal));
                    }
                    
                    doctor.displayMessage(currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+"!\n\n");
                }
                 
                //if the ball was passed to another player in the same team or opponent team
                else{
                    prevTeam = currentState.getTheTeam();
                    prevPlayer = currentState.getTheTeam().getPlayerArray()[(int) (randVal)];
                    System.out.print(currentState+" after "+currentState.getTheTime()+" mins by "+
                    currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+"!\n\n");
                }
                
                //if a goal was scored, increase the number of goals of the team
                if (currentState instanceof GoalScored) {
                    if (currentState.getTheTeam() == teamOne) {
                        lastGoalTeam = teamOne; //store the team name who scored the last goal
                        teamOneGoals++;
                        teamOne.incGoalsTotal(1);
                    }
                    
                    else {
                        lastGoalTeam = teamTwo; //store the team name who scored the last goal
                        teamTwoGoals++;
                        teamTwo.incGoalsTotal(1);
                    }
                    
                    //increase the goals scored by the player
                    currentState.getThePlayer().incGoalsScored();
                    lastGoalplayer = currentState.getThePlayer(); //store the name of player who scored last goal
                }
                
                prevState = currentState;
                } 
                
                else if(val1 <= 0.1){
                    refree.displayMessage("Refree: \"BALL GOES OUT OF FIELD after "+i+" mins\"\n\n");
                }
                
                else if(prevState instanceof Possession){
                randVal=rand.nextInt(10);
                prevState = new BallReceived();
                currentState = new BallReceived();
                    
                //select the team who gets the ball and set a random player from the team to find who has the ball
                currentState.setTheTeam(Math.random() > 0.5 ? teamOne: teamTwo);       
                currentState.setThePlayer(currentState.getTheTeam().getPlayerArray()[(int) (randVal)]);
                currentState.setTheTime(i); //set the time
                    
                System.out.print(currentState+" after "+currentState.getTheTime()+" mins by "+
                currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+" which passed by "
                +prevPlayer.getPlayerName()+" of "+prevTeam.getTeamName()+"!\n\n");
                }
                
                //half time of the extra time
                if(i == 105){
                    try {
                        refree.displayMessage("Refree: \"TIME STOPPED , 1st Half of Extra Time is Finished!\"\n");
                        refree.displayMessage("\n\t\t\t  TIME : 105 mins\t\t\n");
                        System.out.print("\t\t     "+teamOne.getTeamName()+" ("+ teamOneGoals + " - " + teamTwoGoals + ") "+teamTwo.getTeamName());
                        
                        if(noOfGoals == 0)
                            System.out.println("\n\t\tNo goals were scored by any team");
                        else
                            System.out.println("\n\tLast Goal was scored by "+lastGoalplayer.getPlayerName()+" of "+lastGoalTeam.getTeamName());
                     
                        
                        System.out.println("\t\t\tTIME LEFT: "+(120-i)+" mins");
                        System.out.print("\t  INJURED PLAYERS: ");
                        if(injuredPlayersOne.isEmpty() && injuredPlayersTwo.isEmpty())
                            System.out.println("No players were injured in both teams!");
                    
                        else{
                            if(!injuredPlayersOne.isEmpty()){
                                for(int j=0;j<injuredPlayersOne.size();j++)
                                    System.out.print(injuredPlayersOne.get(j).getPlayerName()+"( "+teamOne.getTeamName()+" ) ");
                            }
                        
                            if(!injuredPlayersTwo.isEmpty()){
                                for(int j=0;j<injuredPlayersTwo.size();j++)
                                    System.out.print(injuredPlayersTwo.get(j).getPlayerName()+"("+teamTwo.getTeamName()+" ) ");
                            }
                            
                        }
                        System.out.println();
                        System.out.println("\n\n************************************HALF TIME(EXTRA TIME)************************************\n\n");
                        TimeUnit.SECONDS.sleep(2);
                    }
                    catch (InterruptedException ex) {
                        Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    refree.displayMessage("\n\t\t\tSECOND HALF(EXTRA TIME)\t\t\n");
                    refree.displayMessage("\nRefree: \"TIME STARTED\"\n\n");
                }
                
                ///end of extra time given
                if(i == 120){
                    refree.displayMessage("\nRefree: \"TIME STOPPED, 2nd Half of Extra Time is Finished!\"\n");
                    refree.displayMessage("\n\t\t\t  TIME : 120 mins\t\t\n");
                    System.out.print("\t\t     "+teamOne.getTeamName()+" ("+ teamOneGoals + " - " + teamTwoGoals + ") "+teamTwo.getTeamName());
                    if(noOfGoals == 0)
                        System.out.println("\n\t\tNo goals were scored by any team");
                    else
                        System.out.println("\n\tLast Goal was scored by "+lastGoalplayer.getPlayerName()+" of "+lastGoalTeam.getTeamName());
                    
                    System.out.print("\t  INJURED PLAYERS: "); 
                    if(injuredPlayersOne.isEmpty() && injuredPlayersTwo.isEmpty())
                        System.out.println("No players were injured in both teams!");
                    
                    else{
                        if(!injuredPlayersOne.isEmpty()){
                            for(int j=0;j<injuredPlayersOne.size();j++)
                                System.out.print(injuredPlayersOne.get(j).getPlayerName()+"( "+teamOne.getTeamName()+" ) ");
                        }
                        
                        if(!injuredPlayersTwo.isEmpty()){
                            for(int j=0;j<injuredPlayersTwo.size();j++)
                                System.out.print(injuredPlayersTwo.get(j).getPlayerName()+"("+teamTwo.getTeamName()+" ) ");
                        }
                            
                    }
                    System.out.println();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } 
        
         
        //if the game is still drawn go for a penalty shootout
        if(teamOneGoals == teamTwoGoals) {
            try {
                int penaltyTeamOne = 0;
                int penaltyTeamTwo = 0;
                int index =0;
                
                refree.displayMessage("\nRefree:\"Match ended in a draw again. Let's go for a penlaty shoot!\"\n");
                refree.displayMessage("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~PENALTY SHOOTOUT~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
                pic.showArt("penalty");
                try {
                    
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                currentState.setTheTeam(Math.random() > 0.5 ? teamOne: teamTwo); //select a side to penalty shoot first
                System.out.println("Penalty Shootout was first taken by "+currentState.getTheTeam().getTeamName()+"\n\n");
                for(int i=1;i<=10;i++){
                    
                    //set the player who tries the penalty shootout
                    currentState.setThePlayer(currentState.getTheTeam().getPlayerArray()[index]);
                    
                    //probability to set whether a player hits a goal
                    if(Math.random() > 0.8) {
                        
                        System.out.print("\"GOAL\" , Penalty shoot was succeeded and Goal was scored by "+
                                currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+"!\n\n");
                        
                        //increase the goals scored in penalty shootout
                        if (currentState.getTheTeam() == teamOne) {
                            penaltyTeamOne++;
                        }
                        
                        else {
                            penaltyTeamTwo++;
                        }
                        
                        currentState.getThePlayer().incGoalsScored();
                        
                        //change the team for next shootout
                        if(currentState.getTheTeam() == teamOne)
                            currentState.setTheTeam(teamTwo);
                        
                        else
                            currentState.setTheTeam(teamOne);
                    }
                    
                    //if the player missed the goal
                    else{
                        //display which player tried the penalty shootout
                        System.out.print("Penalty shoot was not succeeded by "+currentState.getThePlayer().getPlayerName()+" of "
                                +currentState.getTheTeam().getTeamName());
                        
                        //set the opponent team as the goal keeper of the opponent team saved the goal
                        if(currentState.getTheTeam() == teamOne)
                            currentState.setTheTeam(teamTwo);
                        
                        else
                            currentState.setTheTeam(teamOne);
                        
                        //display the message that the goal keeper saved the goal
                        currentState.setThePlayer(currentState.getTheTeam().getPlayer(10));
                        System.out.print(" and Goal was saved by "+
                                currentState.getThePlayer().getPlayerName()+" of "+currentState.getTheTeam().getTeamName()+"!\n\n");
                    }
                    index++;
                }
                
                //display final results of the penalty shootouts and choose which team won or it is still a draw
                System.out.println("\n\n---------------------FINAL RESULTS FOR PENALTY SHOOTOUTS---------------------\n");
                System.out.println("\t\t          "+teamOne.getTeamName()+" ("+ penaltyTeamOne + " - " + penaltyTeamTwo + ") "+teamTwo.getTeamName()+"\n\n");
                if(penaltyTeamOne > penaltyTeamTwo)
                    System.out.println("\t\t\tCONGRATULATIONS!!! "+(teamOne.getTeamName() + " WON!\n\n"));
                
                else if(penaltyTeamOne < penaltyTeamTwo)
                    System.out.println("\t\t\tCONGRATULATIONS!!! "+(teamTwo.getTeamName() + " WON!\n\n"));
                
                else
                    System.out.println("\t\t\t\tMATCH DRAWN\n\n");
            } catch (IOException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } 
        
        //if there aren't any penalty shootouts print which team wins
        else{
            System.out.println("\n\n--------------------------FINAL RESULTS--------------------------\n");
            System.out.println("\t\t     "+teamOne.getTeamName()+" ("+ teamOneGoals + " - " + teamTwoGoals + ") "+teamTwo.getTeamName()+"\n\n");
       
            if(teamOneGoals > teamTwoGoals) 
                System.out.println("\t\t CONGRATULATIONS!!! "+(teamOne.getTeamName() + " WON!\n\n"));
         
            else 
                System.out.println("\t\t CONGRATULATIONS!!! "+(teamTwo.getTeamName() + " WON!\n\n"));
        
        }
        
    }

   
}