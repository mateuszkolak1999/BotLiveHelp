package pl.kolak.Bot.events;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.kolak.Bot.config.Config;

import javax.annotation.Nonnull;
import java.util.Timer;
import java.util.TimerTask;

public class CheckNameEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        String name = event.getUser().getId();
        Role newUserRole = event.getGuild().getRoleById(Config.get("new_user_role"));
        event.getGuild().addRoleToMember(event.getMember(),newUserRole).complete();

        //event.getUser().getJDA().getRoles().contains(newUserRole)
        if (event.getUser().getName().matches(".*[^a-zA-Z0-9].*")){
            event.getUser().openPrivateChannel().complete()
                    .sendMessage("Zmień swoją nazwę użytkownika. Nazwa może zawierać duże litery," +
                            " małe litery i cyfry. Jeżeli w przeciągu 10 minut nie zmienisz swojej" +
                            " nazwy to zostaniesz wyrzucony z serwera!").queue();

            new Timer().schedule(new TimerTask() {
                                     @Override
                                     public void run() {
                                         event.getGuild().kick(name).complete();
                                     }
                                 },
                    60*10*1000);
        }
    }

    @Override
    public void onUserUpdateName(@Nonnull UserUpdateNameEvent event) {
        String userName = event.getNewName();

        if (userName.matches(".*[^a-zA-Z0-9].*")){
            event.getUser().openPrivateChannel().complete()
                    .sendMessage("Zmień swoją nazwę użytkownika. Nazwa może zawierać duże litery," +
                            " małe litery i cyfry. Jeżeli w przeciągu 10 minut nie zmienisz swojej" +
                            " nazwy to zostaniesz wyrzucony z serwera!").queue();

            new Timer().schedule(new TimerTask() {
                                     @Override
                                     public void run() {
                                         event.getJDA().getGuilds().forEach(e-> System.out.println(e.toString()));
                                     }
                                 },
                    60*10*1000);
        }else if(event.getOldName().matches(".*[^a-zA-Z0-9].*") && userName.matches(".*[a-zA-Z0-9].*")){
            event.getUser().openPrivateChannel().complete()
                    .sendMessage("Brawo! Udało Ci się zmienić swoją nazwę! Już nie przeszkadzam i życzę miłego" +
                            " pobytu na naszym serwerze discord! Po więcej informacji użyj komendy " + Config.get("prefix") +
                            "help").queue();
        }


    }
}
