
package announcerbot;

/*ABOUT:
***********************************************************************

Started on 4/2/2018

Version: 1.0 alpha

*/

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.managers.Presence;

//




public class AnnouncerBot {
    
    public static void main(String[] args){
        JDA discord = null;
        //jda is the shell of the bot. it holds all values and functions the bot is able to do
        
        //set the bot
        
        try {
            
            Consts constants = new Consts();
            //declare a new constants instance. this activates everything in the consts.java file
            
            //JDABuilder jdaB = new JDABuilder(AccountType.BOT).setToken(Consts.getToken()).buildBlocking();
            
            discord = new JDABuilder(AccountType.BOT).setToken(Consts.getToken()).buildBlocking();//make the bot, A BOT
            discord.setAutoReconnect(true);//if the bot disconnects from the internet, reconnect it auto
            discord.getPresence().setGame(Game.of("Type <about>"));
            //load up the bot, then set the playing game
            
            
        } catch (LoginException ex) {
            Logger.getLogger(AnnouncerBot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(AnnouncerBot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AnnouncerBot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RateLimitedException ex) {
            Logger.getLogger(AnnouncerBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        //basic debug and exception handleing in case something goes wrong
        
        discord.addEventListener(new MessageProcess());
        //declare the new listener. this makes it able to read messages
}
    
}