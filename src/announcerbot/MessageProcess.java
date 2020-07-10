
package announcerbot;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author Sean
 */
public class MessageProcess extends ListenerAdapter{
    
    
    
    public void onMessageReceived(MessageReceivedEvent event) {
    
        if(event.getMessage().getContent().startsWith("<announce>") &&  event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.BAN_MEMBERS)){
            //<announce> (time) 
             FileReader fileReader;
             Boolean success = true;
             
             String channel = null;
            try {
                fileReader = new FileReader("announceChannel");
                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                
                channel = bufferedReader.readLine();
            } catch (FileNotFoundException ex) {
                System.out.println("Error, the set file for announcing the file was not found while reading.");
                event.getChannel().sendMessage("Error, no channel configuration was found. If this is the first time using the command, please use the <setChannel> command to set a channel to post on").queue();
                success = false;
            } catch (IOException ex) {
                System.out.println("Error, there was an IO error while attempting to read the announcerChannel file (disk corruption?)");
                success = false;
            }

            
            
            
            
            
            if(success){
            //event.getMessage().getTextChannel().sendMessage("announce has been called by "+event.getMessage().getAuthor().toString()).queue();
            
            Boolean correctFormat = true;
            String msg = event.getMessage().getContent();
            msg.replace("<announce> ", "");
            
            String timeString = msg.split(" ")[1];
            timeString.replace(" ", "");
            System.out.println("*"+timeString+"*");
            
            try{
                
               int time = Integer.parseInt(timeString);
                
            String announcement = msg.replace(timeString, "");
            announcement = announcement.replace("<announce>", "");
            System.out.println(announcement);
            
            MultiThread mult = new MultiThread(announcement, time, channel, event);
            mult.start();
            event.getTextChannel().sendMessage(":white_check_mark: Message will be sent in "+ time +" hours.").queue();
            
            
            }
            catch(NumberFormatException ex){
                event.getTextChannel().sendMessage("Error, formatting of this command is not correct. Please check your syntax.").queue();
            
            }

            
            }
    }
        else if(event.getMessage().getContent().startsWith("<setChannel>") &&  event.getGuild().getMember(event.getAuthor()).hasPermission(Permission.BAN_MEMBERS)){
            PrintWriter writer;
            Boolean success = true;
            try {
                
                System.out.println("The announcing channel is being changed by "+ event.getAuthor().toString() + " in "+event.getGuild().toString());
                writer = new PrintWriter("announceChannel", "UTF-8");
                writer.println(event.getTextChannel().getName());
                writer.close();
                
                
            } catch (FileNotFoundException ex) {
                System.out.println("Error, the set file for announcing the file was not found while writing.");
                success = false;
            } catch (UnsupportedEncodingException ex) {
                System.out.println("Error, the declared encoding type is not supported for writing to the announcing channel file");
                success = false;
            }
            
            if(success)
            {System.out.println("    Change Successful!");
            event.getTextChannel().sendMessage(":thumbsup: Change Successful").queue();}
            else
                event.getTextChannel().sendMessage("Error, something went wrong in the bot. Check the command prompt on the server for details. :( ").queue();
            
            
            
        }
        
        else if(event.getMessage().getContent().startsWith("<about>")){
            event.getTextChannel().sendMessage("Bot Version 1.0\n\nDeveloped by Spazmaster#8989. Feel free to contact me for bots :) \n\nPurpose: "
                    + "To post announcements in the chat at a designated time. \n\t**Commands:**\n<about>: Show this message.\n<announce> (Integer) \"text\":"
                    + " Posts \"text\" message in (Integer) hours. Using (at)everyone also works with this! \n\t*Example 1:* <announce> 5 hey everyone! --- posts \"hey everyone!\" after 5 hours \n\t*Example 2:* "
                    + "<announce> 0 hello world --- posts \"hello world\" immediately.\n<setChannel>: Sets the channel the bot will post to" ).queue();
        
        
        }
    
    }
    
}
