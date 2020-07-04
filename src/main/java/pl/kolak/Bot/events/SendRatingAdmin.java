package pl.kolak.Bot.events;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.kolak.Bot.DAO.AdminDao;
import pl.kolak.Bot.DAO.AdminDaoImpl;
import pl.kolak.Bot.config.Config;
import pl.kolak.Bot.model.Admin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SendRatingAdmin extends ListenerAdapter {

    private List<String> message_list = new ArrayList<>();
    private List<Member> admins;
    private String id_channel;
    AdminDao adminDao = new AdminDaoImpl();
    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        VoiceChannel channelLeft = event.getChannelLeft();
        String message = "Jak oceniasz pomoc administratora: ";
        if(channelLeft.getId().equals(Config.get("ADMIN_CHANNEL_HELP"))){
            if (!event.getMember().getRoles().contains(event.getGuild().getRoleById(Config.get("ADMIN_ROLE")))){
                List<Member> user_list = event.getGuild().getVoiceChannelById(Config.get("ADMIN_CHANNEL_HELP")).getMembers();
                admins = new ArrayList<>();
                for(Member m : user_list){
                    if(m.getRoles().contains(event.getGuild().getRoleById(Config.get("ADMIN_ROLE")))) {
                        admins.add(m);
                    }
                }
                id_channel = event.getGuild().createTextChannel("ocena-admin-" + event.getMember().getUser().getName())
                        .complete().getId();
                for(Member a : admins){
                    event.getGuild().getTextChannelById(id_channel)
                            .sendMessage(message + a.getUser().getName() + "?")
                            .queue(message1 -> {
                                message1.addReaction(event.getGuild().getEmoteById("726153392703144039")).queue();
                                message1.addReaction(event.getGuild().getEmoteById("726153392811933799")).queue();
                            });
                }
            }
        }
    }


    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        if (!event.getMember().getUser().isBot()){
            if (event.getChannel().getId().equals(id_channel)){
                String reaction = event.getReaction().getReactionEmote().getId();
                        Admin admin = null;
                        Member admin_member = admins.get(0);
                        Admin old_admin = adminDao.get(admin_member.getUser().getIdLong());
                        if(old_admin == null){
                            switch (reaction){
                                case "726153392703144039":
                                    admin = new Admin(admin_member.getIdLong(),admin_member.getUser().getName(),-1L);
                                    break;
                                case "726153392811933799":
                                    admin = new Admin(admin_member.getIdLong(),admin_member.getUser().getName(),1L);
                                    break;
                            }
                            adminDao.add(admin);
                        }else{
                            switch (reaction){
                                case "726153392703144039":
                                    admin = new Admin(old_admin.getDiscord_user_id(),old_admin.getUsername(),old_admin.getPoints() - 1);
                                    break;
                                case "726153392811933799":
                                    admin = new Admin(old_admin.getDiscord_user_id(),old_admin.getUsername(),old_admin.getPoints() + 1);
                                    break;
                            }
                            adminDao.update(admin);
                        }
                        event.getChannel().deleteMessageById(event.getMessageId()).complete();
                        event.getGuild().getTextChannelById(event.getChannel().getId()).delete().complete();
            }
        }
    }
}
