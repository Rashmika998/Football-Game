package FootBall;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Rashmika
 */
public class PlayerData {
    public int k=0;
    
    //array lists to store players of two teams
    private ArrayList<Person> playersOne;
    private ArrayList<Person> playersTwo;
    public ArrayList<Person> reserveOne;
    public ArrayList<Person> reserveTwo;
    
    //players of the team one
    String playerListOne = "Lionel Messi(c),"+"Philip Countino,"+"Usman Dembele,"+"Neymar JR,"+"Riqui Puig,"+
            "Frankie de Jong,"+"Sergio Roberto,"+"Sergio Busquets,"+"Jodi Alba,"+"Gerard Pique,"+"Ansu Fati,"+"Thiago Silva,"
            +"Kevin Bruyne,"+"Kylian Mbappe,"+"Harry Kane,"+"Paul Pogba,"+"Sadio Mane";
    
    //players of the team two
    String playerListTwo = "Christiano Ronaldo(c),"+"Sergio Ramos,"+"Thomas Muller,"+"Karim Benzema,"+
            "Luka Modric,"+"Virgil Dijk,"+"Sven Bender,"+"Samuel Umtiti,"+"Gareth Bale,"+"Wayne Rooney,"+"Luiz Suarez,"+"Eden Hazard,"
            +"Toni Kroos,"+"Mohamed Salah,"+"Paulo Dybala,"+"Braut Haland,"+"Dani Alves";
     
    public PlayerData() {
        
        StringTokenizer playerTokensOne = new StringTokenizer(playerListOne, ","); //get team 1 names
        playersOne = new ArrayList();
        
        //add players to the array list
        while(playerTokensOne.hasMoreTokens()) {
            playersOne.add(new Person(playerTokensOne.nextToken()));
        }
        
        StringTokenizer playerTokensTwo = new StringTokenizer(playerListTwo, ","); //get team 2 names
         playersTwo = new ArrayList();
        
         //add players to the array list
        while(playerTokensTwo.hasMoreTokens()) {
            playersTwo.add(new Person(playerTokensTwo.nextToken()));
        }
        
    }
    
    public Person[] getTeam(int numberOfPlayers,int teamNum) throws PlayerDataException {
        reserveOne = new ArrayList();
        reserveTwo = new ArrayList();
        Person[] teamPlayers = new Person[numberOfPlayers]; //create an array of 11 players
   
        //add 10 players from the given names
        for(int i = 0; i < numberOfPlayers-1; i++) {
            
            //add players randomly form the list to team one
            if(teamNum == 0){   
                int playerIndex = (int) (Math.random() * playersOne.size());
                
                if (i==0)
                     playerIndex = 0; //add the skipper
            
                try {
                    teamPlayers[i] = playersOne.get(playerIndex);
                    playersOne.remove(playerIndex);
                }
                catch (IndexOutOfBoundsException e) {              
                    throw new PlayerDataException("Not enough players!");
                }
            }
            
            //add players randomly from the list to team two
            else if(teamNum == 1){
                
                int playerIndex = (int) (Math.random() * playersTwo.size());;
                if (i==0)
                     playerIndex = 0; //add the skipper
                try {
                    teamPlayers[i] = playersTwo.get(playerIndex);
                    playersTwo.remove(playerIndex);
                }
                catch (IndexOutOfBoundsException e) {              
                    throw new PlayerDataException("Not enough players!");
                }
            }
            
        }
        
        for(int i=0;i<playersOne.size();i++){
            reserveOne.add(playersOne.get(i));
        }
        
        for(int i=0;i<playersTwo.size();i++){
            reserveTwo.add(playersTwo.get(i));
        }
        
        return teamPlayers;
    }
}

class PlayerDataException extends Exception{
    public PlayerDataException() {}
    
    public PlayerDataException(String message) {
        super(message);
    }
}