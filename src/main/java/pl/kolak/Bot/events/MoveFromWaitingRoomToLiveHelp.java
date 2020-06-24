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

public class MoveFromWaitingRoomToLiveHelp extends ListenerAdapter {
    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        moveUser(event);
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        moveUser(event);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        moveUser(event);
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        /*if(channelJoined.getId().equals(Config.get("WAITING_ROOM"))){
            if(!channelJoined.getMembers().isEmpty()){
                List<Member> members = channelJoined.getMembers();
                if(event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID")).getMembers().isEmpty())
                    new Timer().schedule(new TimerTask() {
                                         @Override
                                         public void run() {
                                             event.getGuild().moveVoiceMember(members.get(0),event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID"))).complete();
                                         }
                                     },
                        3*1000);
            }
        }*/
        moveUser(event);
    }

    private void moveUser(GenericGuildEvent event){
        VoiceChannel channelJoined = event.getGuild().getVoiceChannelById(Config.get("WAITING_ROOM"));
        if(!channelJoined.getMembers().isEmpty()){
            List<Member> members = channelJoined.getMembers();
            if(event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID")).getMembers().isEmpty())
                event.getGuild().moveVoiceMember(members.get(0),event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID"))).complete();
        }
    }
}
