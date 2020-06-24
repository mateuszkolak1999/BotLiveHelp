package pl.kolak.Bot.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.kolak.Bot.config.Config;

import javax.annotation.Nonnull;
import java.util.List;


public class MoveUserWithHelpToAdmin extends ListenerAdapter {

    private Member user;

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        moveUser(event);
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        moveUser(event);
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        moveUser(event);
        /*VoiceChannel channelJoined = event.getChannelJoined();
        VoiceChannel admin_channel_help = event.getGuild().getVoiceChannelById(Config.get("ADMIN_CHANNEL_HELP"));
        List<Member> members = event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID")).getMembers();
        for(Member m : members){
            if(!m.getUser().isBot())
                user = m;
        }
        if (channelJoined.getId().equals(Config.get("ADMIN_CHANNEL_HELP"))){
            new Timer().schedule(new TimerTask() {
                                     @Override
                                     public void run() {
                                         event.getGuild().moveVoiceMember(user,channelJoined).complete();
                                     }
                                 },
                    5*1000);
        }else if (admin_channel_help.getMembers().size()==1
                    && event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID")).getMembers().size()==2){
            new Timer().schedule(new TimerTask() {
                                     @Override
                                     public void run() {
                                         event.getGuild().moveVoiceMember(user,admin_channel_help).complete();
                                     }
                                 },
                    13*1000);
        }*/
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        moveUser(event);
    }

    private void moveUser(GenericGuildEvent event){
        VoiceChannel channelLiveHelpBot = event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID"));
        VoiceChannel channelHelpAdmin = event.getGuild().getVoiceChannelById(Config.get("ADMIN_CHANNEL_HELP"));
        if(!channelLiveHelpBot.getMembers().isEmpty()){
            List<Member> members = channelLiveHelpBot.getMembers();
            for(Member m : members){
                if(!m.getUser().isBot())
                    user = m;
            }
            if(!channelHelpAdmin.getMembers().isEmpty()){
                List<Member> membersOnHelp = channelHelpAdmin.getMembers();
                if(membersOnHelp.size()==1){
                    try {
                        Thread.sleep(5*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    event.getGuild().moveVoiceMember(user,channelHelpAdmin).complete();
                }
            }

        }
    }
}
