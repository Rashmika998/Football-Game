package FootBall;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rashmika
 */
public class Match {

    /**
     * @param args the command line arguments
     */
    Person refree = new Person("Refree"); //refree to handle the gameplay
    Person doctor = new Person("Doctor"); //doctor when a plyer gets injured
    Person gkOne = new Person("Ter Sergian(GK)"); //goalkeeper of the team one
    Person gkTwo = new Person("Thibaut Courtoris(GK)"); //goal keeper of the team two
 
    public static void main(String[] args) {
        
        showPic pic = new showPic();
        try {
            pic.showArt("start");
        } catch (IOException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pic.tapEnter();
        
        
        //Call methods to create teams, assign them to a game
        Match newMatch = new Match();
        
        try {
            Teams[] theTeams = newMatch.createTeams("Team 01, Team 02", 11); //create two teams
            PlayGame[] theGames = newMatch.createGames(theTeams); //make a game (more than one game can be played)

            TimeUnit.SECONDS.sleep(1);
             
             
            //make only one game to play
            PlayGame currGame = theGames[0];
            currGame.playGame();
            
            //show the best players at the end (points are given to players by how they played)
            newMatch.showBestPlayers(theTeams, theGames[0]);
            
        }
        
        //if the game is not created catch the error
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
    
    
    //function to create teams
    public Teams[] createTeams(String teamNames, int teamSize) throws PlayerDataException {
        PlayerData playerData = new PlayerData(); //create an object to get player names
        StringTokenizer teamNameTokens = new StringTokenizer(teamNames, ","); //get the team names and players in a team
        
        Teams[] theTeams = new Teams[teamNameTokens.countTokens()]; //array to store the teams 
        
        //create the teams by adding the players with the team size (10 players are added)
        for(int i = 0; i < theTeams.length; i++) {
            theTeams[i] = new Teams(teamNameTokens.nextToken(),playerData.getTeam(teamSize,i),playerData);
        }
        
        //last player in both teams is the goal keeper
        theTeams[0].addName(gkOne);
        theTeams[1].addName(gkTwo);
        
        //print a heading whether who's plalying(two teams and then players of the teams)
        System.out.println("\t\t---------------------MATCH DAY---------------------\n\n\t\t\t   "+theTeams[0].getTeamName()+
        "\t VS   \t"+theTeams[1].getTeamName()+"\n\n\t\t====================TEAM PLAYERS====================\n\n");
        
        System.out.print("\t\t\t   "+theTeams[0].getTeamName()+"\t      \t"+theTeams[1].getTeamName()+"\n\n");
        
        //print the players of the teams
        for(int i=0;i<teamSize;i++){
            System.out.print("\t\t\t"+theTeams[0].displayTeamPlayers(i).getPlayerName()+"\t\t");
            System.out.print(theTeams[1].displayTeamPlayers(i).getPlayerName()+"\n");
        }
        
        System.out.println("\t\t====================================================");
       
        //return the created teams
        return theTeams;
    }
        
    //function to create games(in here only one game is played)
    public PlayGame[] createGames(Teams[] theTeams) {
        
        ArrayList<PlayGame> theGames = new ArrayList();
        
        for(Teams homeTeam: theTeams) {
            for(Teams awayTeam: theTeams) {
                if (homeTeam!=awayTeam) {
                    theGames.add(new PlayGame(homeTeam, awayTeam, refree, doctor));
                }
            }
        }
        
        return (PlayGame[]) theGames.toArray(new PlayGame[1]);
    }
    
    //function to show number of goals players scored
    public void showBestPlayers(Teams[] theTeams, PlayGame game) {
        ArrayList <Person> thePlayers = new ArrayList();
        
        for(Teams currTeam: theTeams) {
            thePlayers.addAll(Arrays.asList(currTeam.getPlayerArray()));
        }
        
        //add the injured players
        for(int i=0;i<game.injuredPlayersOne.size();i++)
            thePlayers.add(game.injuredPlayersOne.get(i));
        
        for(int i=0;i<game.injuredPlayersTwo.size();i++)
            thePlayers.add(game.injuredPlayersTwo.get(i));
        
        Collections.sort(thePlayers, (p1, p2) ->
            Double.valueOf(p2.getGoalsScored()).compareTo
            (Double.valueOf(p1.getGoalsScored())));
        System.out.println("\nNumber of Goals Players Scored: ");
        
        thePlayers.forEach((currPlayer) -> {
            System.out.println(currPlayer.getPlayerName() + " : " + 
                    currPlayer.getGoalsScored());
        });   
    }
}
