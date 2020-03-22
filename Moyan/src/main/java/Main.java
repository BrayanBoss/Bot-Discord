import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.RoleImpl;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "Njc1MjgwNjI5NzU5MzQ0NjQx.XlKROA.hVRK6Xaj8eJW02NkPpOEiWY-H88";
        builder.setToken(token);
        builder.addEventListeners(new Main());
        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Message from " + event.getAuthor().getName() + " : " + event.getMessage().getContentDisplay());
        Date aujourdhui = new Date();
        DateFormatSymbols fr = new DateFormatSymbols(Locale.FRENCH);
        //SimpleDateFormat format = new SimpleDateFormat("Nous sommes le' EEEE d MMM yyyy, ' il est' hh:mm:ss.", fr);
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().equals("/ping")) {
            event.getChannel().sendMessageFormat("Pong !").queue();
        }
        if (event.getMessage().getContentRaw().contains("suicid") || event.getMessage().getContentRaw().contains("Suicid")) {
            event.getChannel().sendMessage(" Si vous pensez au suicide, ne le faites pas. Le suicide n'est jamais la solution. Appelez SOS AMITIE au `01 40 09 15 22`. Pour ceux qui ne sont pas en France, vous pouvez trouver votre ligne local ici : <http://www.suicide.org/international-suicide-hotlines.html> ").queue();
        }
        if (event.getMessage().getContentRaw().equals("/time")) {
            //event.getChannel().sendMessage(format.format(aujourdhui)).queue();
        }
        if (event.getMessage().getContentRaw().equals("/help")) {
            event.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("/ping : pour recevoir un message pong\n /time : pour afficher l'heure et la date actuelle\n /origine : pour afficher le nom de mes auteurs et de leurs provenances universitaire\n /meteo : pour afficher la meteo actuelle par rapport à votre localisation\n Voila !").queue();
            });
            event.getChannel().sendMessage("Regardez dans vos PM, toutes nos commandes y sont ! :grin:").queue();
        }
        if (event.getMessage().getContentRaw().equals("Awo") || event.getMessage().getContentRaw().contains("Awo".toLowerCase()) || event.getMessage().getContentRaw().contains("Awo".toUpperCase())) {
            event.getChannel().sendMessage("Awooooooooooo").queue();
        }
        //La provenance du bot
        if (event.getMessage().getContentRaw().equals("/origine".toLowerCase())) {
            event.getChannel().sendMessage("Je viens de l'IUT de Montreuil, j'ai ete realise par deux tres grands informaticiens Yanis ainsi que Mohammed").queue();
        }
        //Loto
        if (event.getMessage().getContentRaw().equals("/loto")) {
            event.getChannel().sendMessage("Tu viens de recuperer " + (int) Math.random() + " euros ! Tu peux revenir dans 24 heures").queue();
        }
        /*if (event.getMessage().getContentRaw().contains("/meteo")) {
            try {
                URL test = new URL("https://weather.com");
                event.getChannel().sendMessage("").queue();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        //Perroquet
        if (event.getMessage().getContentRaw().contains("/say")) {
            event.getChannel().sendMessage(event.getMessage().getContentRaw().substring(4)).queue();
        }


        File m = new File("motsDiscriminatoires");
        m.setReadable(true);
        try {
            List<String> d = Files.readAllLines(Paths.get(m.getAbsolutePath()));
            for (int i = 0; i < d.size(); i++) {
                if (event.getMessage().getContentRaw().contains(d.get(i))) {
                    event.getMessage().delete();
                    event.getChannel().sendMessage("Attention à vos propos, c'est un avertissement").queue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
