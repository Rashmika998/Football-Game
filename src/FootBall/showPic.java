package FootBall;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Rashmika
 */
public class showPic {
    
    //display clip art
    public void showArt(String name) throws IOException{
        Scanner sc = new Scanner(getClass().getResourceAsStream(name+".txt"));


        while( sc.hasNextLine()){
            System.out.println(sc.nextLine());
        }
      
 }
    
    
  public void tapEnter()
 { 
        System.out.println("Press Enter key to continue...");
        try{
            System.in.read();
        }  
        catch(Exception e)
        {}  
 }
}
