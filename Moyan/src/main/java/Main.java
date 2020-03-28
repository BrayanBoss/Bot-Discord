import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "Njc1MjgwNjI5NzU5MzQ0NjQx.Xn-S-g.F0wPrcy9sESH7-Jeo83DvUkh9do";
        builder.setToken(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Beep Beep Boop !"));
        builder.addEventListeners(new Main());
        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Un message de " + event.getAuthor().getName() + " sur " + event.getChannel().getName() + " : " + event.getMessage().getContentDisplay());
        Date aujourdhui = new Date();
        DateFormatSymbols FR = new DateFormatSymbols(Locale.FRENCH);
        SimpleDateFormat format = new SimpleDateFormat("'Nous sommes le' EEEE d MMM yyyy, ' il est' hh:mm:ss.", FR);

        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().equals("/ping")) {
            event.getChannel().sendMessageFormat("Pong !").queue();
        }
        if (event.getMessage().getContentRaw().contains("suicid") || event.getMessage().getContentRaw().contains("Suicid")) {
            event.getChannel().sendMessage(" Si vous pensez au suicide, ne le faites pas. Le suicide n'est jamais la solution. Appelez SOS AMITIE au `01 40 09 15 22`. Pour ceux qui ne sont pas en France, vous pouvez trouver votre ligne local ici : <http://www.suicide.org/international-suicide-hotlines.html> ").queue();
        }
        if (event.getMessage().getContentRaw().equals("/time")) {
            event.getChannel().sendMessage(format.format(aujourdhui)).queue();
        }
        if (event.getMessage().getContentRaw().equals("/help")) {
            event.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("/ping : pour recevoir un message pong\n/time : pour afficher l'heure et la date actuelle\n/origine : pour afficher le nom de mes auteurs et de leurs provenances universitaire\n/say : pour que je fasse le perroquet\n/roll : \n/kick : pour kick un membre si vous en avez la capacité \n/sendMessageAll : pour envoyer un message à tout le monde en mp\n/checkping : pour obtenir le ping de l'utilisateur au mentionné\n/mp : pour envoyer un mp à une personne mentionné\n/ohlebot : pour controler le bot en mode manuel et interragir à travers une console\n/ajouterMotInterdit : afin d'ajouter un mot qui est interdit d'usage\nVoila !").queue();
            });
            event.getChannel().sendMessage("Regardez dans vos PM, toutes mes commandes y sont ! :grin:").queue();
        }
        if (event.getMessage().getContentRaw().equals("Awo") || event.getMessage().getContentRaw().contains("Awo".toLowerCase()) || event.getMessage().getContentRaw().contains("Awo".toUpperCase())) {
            event.getChannel().sendMessage("Awooooooooooo").queue();
        }
        if (event.getMessage().getContentRaw().equals("/roll")) {
            Random rand = ThreadLocalRandom.current();
            int roll = rand.nextInt(101);
            event.getChannel().sendMessage("Roll de l'utilisateur " + event.getAuthor().getAsMention() + " : " + roll).flatMap((v) -> roll == 69, sentMessage -> event.getChannel().sendMessage("Nice.")).queue();
        }
        if (event.getMessage().getContentRaw().startsWith("/kick")) {
            if (event.getMessage().getMentionedUsers().isEmpty()) {
                event.getChannel().sendMessage("Mentionne au moins une personne bg :thumbsup:").queue();
            } else {
                Guild guild = event.getGuild();
                Member bot = guild.getSelfMember();
                if (!bot.hasPermission(Permission.KICK_MEMBERS)) {
                    event.getChannel().sendMessage("J'ai pas la permission de kick quelqu'un bg :pensive:").queue();
                    return;
                }
                List<User> utilisateursMentionnes = event.getMessage().getMentionedUsers();
                for (User user : utilisateursMentionnes) {
                    Member membre = guild.getMember(user);
                    if (!bot.canInteract(membre)) {
                        event.getChannel().sendMessage("Je peux pas le kick, il est plus puissant que moi :pensive:").queue();
                        continue;
                    }
                    event.getChannel().sendMessage("J'ai kick " + membre.getAsMention() + ". \nCiao :thumbsup:").queue();
                    guild.kick(membre).queue();
                }
            }
        }
        if (event.getMessage().getContentRaw().startsWith("/sendMessageAll")) {
            List<Member> membres = event.getGuild().getMembers();
            String message = event.getMessage().getContentRaw().replaceAll("/sendMessageAll ", "");
            for (Member membre : membres) {
                if (membre.getUser().isBot()) {
                    assert true;
                } else {
                    membre.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(event.getAuthor().getName() + " a dit : " + message).queue();
                    });
                }
            }
        }
        if (event.getMessage().getContentRaw().equals("/checkping")) {
            long start = System.currentTimeMillis();
            event.getChannel().sendMessage(":outbox_tray: **Ping...**").queue(message -> message.editMessage(":inbox_tray: **Le ping de l'utilisateur " + event.getAuthor().getAsMention() + " est de " + (System.currentTimeMillis() - start) + "ms**").queue());
        }

        if (event.getMessage().getContentRaw().equals("/y")) {
            Random rand = ThreadLocalRandom.current();
            int roll = rand.nextInt(6) + 1;
            ArrayList<Integer> tab = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                event.getChannel().sendMessage("" + roll).queue();
                roll = rand.nextInt(6) + 1;
                tab.add(i);
            }
        }

        if (event.getMessage().getContentRaw().startsWith("/mp")) {
            List<User> user = event.getMessage().getMentionedUsers();
            System.out.println(user);
            ArrayList<String> pseudos = new ArrayList<>();
            for (User usr : user) {
                pseudos.add(usr.getAsMention());
            }
            String message = event.getMessage().getContentRaw().replaceAll("/mp ", "");
            for (String pseudo : pseudos) {
                pseudo = pseudo.replaceAll("@", "@!");
                message = message.replaceAll(pseudo, "");
            }
            String finalMessage = message;
            System.out.println(finalMessage);
            for (User usr : user) {
                usr.openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(event.getMessage().getAuthor().getName() + " vous dit : " + finalMessage).queue();
                });
            }
        }

        if (event.getMessage().getContentRaw().startsWith("/ohlebot")) {
            Scanner sc = new Scanner(System.in);
            String message = sc.nextLine();
            event.getChannel().sendMessage(message).queue();
        }

        if (event.getMessage().getContentRaw().equals("/origine".toLowerCase())) {
            event.getChannel().sendMessage("Je viens de l'IUT de Montreuil, j'ai été réalisé par deux très grands informaticiens Yanis ainsi que Mohammed").queue();
        }

        if (event.getMessage().getContentRaw().equals("/loto")) {
            event.getChannel().sendMessage("Tu viens de récuperer " + (int) Math.random() + " euros ! Tu peux revenir dans 24 heures").queue();
        }

        if (event.getMessage().getContentRaw().contains("/say")) {
            event.getChannel().sendMessage(event.getMessage().getContentRaw().substring(4)).queue();
        }

        try {
            File m = new File("MotsInterdits");
            List<String> lm = Files.readAllLines(Paths.get(m.getAbsolutePath()));
            if (lm.contains(event.getMessage().getContentRaw())) {
                event.getChannel().sendMessage("Attention à vos propos").queue();
            }
            if (event.getMessage().getContentRaw().contains("/ajouterMotInterdit")) {
                PrintWriter writer = new PrintWriter("MotsInterdits", "UTF-8");
                writer.println(event.getMessage().getContentRaw().substring(20));
                writer.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}

