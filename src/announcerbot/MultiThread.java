
package announcerbot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.util.List;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 *
 * @author Sean
 */
public class MultiThread implements Runnable{
    Thread t;
    String threadName = "timingThread";
    String msg;
    int time;
    String channel;
    MessageReceivedEvent event;
    
    MultiThread(String m, int h, String c, MessageReceivedEvent e){
        msg = m;
        time = h;
        channel = c;
        event = e;
        
    }
    
    public void run() {
      System.out.println("Running the timing thread" );
      try {
          
          System.out.println("Announcement will be posted in "+time+" hours.");
          t.sleep(time* 3600000);
          System.out.println("Announcement has been posted.");
          List<TextChannel> all = event.getGuild().getTextChannels();
          
          TextChannel announceChannel = null;
          
          for(int i = 0; i< all.size(); i++){
              if(all.get(i).getName().equals(channel))
                  all.get(i).sendMessage(msg).queue();
          
          }
          
          
          
         }
       catch (InterruptedException e) {
         System.out.println("Error, the thread crashed. :(   ");
      }
      System.out.println("Thread now exiting.");
   }
   
   public void start () {
      System.out.println("Starting timing thread" );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
    
    
    
    
}
