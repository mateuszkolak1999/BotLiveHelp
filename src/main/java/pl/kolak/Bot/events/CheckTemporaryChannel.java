package pl.kolak.Bot.events;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pl.kolak.Bot.config.Config;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class CheckTemporaryChannel extends ListenerAdapter {
    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        //create channel
        String userName = event.getMember().getUser().getName();
        List<String> channels = event.getGuild().getCategoryById(Config.get("TEMPORARY_CATEGORY")).getVoiceChannels().stream().map(GuildChannel::getName).collect(Collectors.toList());
        if(event.getChannelJoined()==event.getGuild().getVoiceChannelById(Config.get("TEMPORARY_CHANNEL_CREATE"))){
            if(!channels.contains(userName))
                new Timer().schedule(new TimerTask() {
                                         @Override
                                         public void run() {
                                             VoiceChannel new_voice_channel = event.getGuild().getCategoryById(Config.get("TEMPORARY_CATEGORY")).createVoiceChannel(userName).complete();
                                             event.getGuild().moveVoiceMember(event.getMember(),new_voice_channel).complete();
                                         }
                                     },
                        5*1000);
            else
                new Timer().schedule(new TimerTask() {
                                         @Override
                                         public void run() {
                                             event.getGuild().kickVoiceMember(event.getMember()).complete();
                                         }
                                     },
                        5*1000);
        }
        /*String userName = event.getMember().getUser().getName();
        if(event.getChannelJoined()==event.getGuild().getVoiceChannelById(Config.get("TEMPORARY_CHANNEL_CREATE"))
                    && !event.getGuild().getCategoryById(Config.get("TEMPORARY_CATEGORY")).getVoiceChannels().contains(userName)){
            VoiceChannel new_voice_channel = event.getGuild().getCategoryById(Config.get("TEMPORARY_CATEGORY")).createVoiceChannel(userName).complete();
            event.getGuild().moveVoiceMember(event.getMember(),new_voice_channel).complete();
        }*/
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        List<VoiceChannel> voiceChannels = event.getGuild().getCategoryById(Config.get("TEMPORARY_CATEGORY")).getVoiceChannels();
        if(voiceChannels!=null){
            if (voiceChannels.contains(event.getChannelLeft())
                    && event.getChannelLeft().getMembers().isEmpty()
                    && event.getChannelLeft()!=event.getGuild().getVoiceChannelById(Config.get("TEMPORARY_CHANNEL_CREATE")))
                event.getChannelLeft().delete().complete();
        }
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        String userName = event.getMember().getUser().getName();
        List<VoiceChannel> voiceChannels = event.getGuild().getCategoryById(Config.get("TEMPORARY_CATEGORY")).getVoiceChannels();
        if(voiceChannels!=null){
            //delete channel
            if(voiceChannels.contains(event.getChannelLeft())
                    && event.getChannelLeft().getMembers().isEmpty()
                    && event.getChannelLeft()!=event.getGuild().getVoiceChannelById(Config.get("TEMPORARY_CHANNEL_CREATE")))
                event.getChannelLeft().delete().complete();

            //create channel
            List<String> channels = event.getGuild().getCategoryById(Config.get("TEMPORARY_CATEGORY")).getVoiceChannels().stream().map(GuildChannel::getName).collect(Collectors.toList());
            if(event.getChannelJoined()==event.getGuild().getVoiceChannelById(Config.get("TEMPORARY_CHANNEL_CREATE"))){
                if(!channels.contains(userName))
                    new Timer().schedule(new TimerTask() {
                                         @Override
                                         public void run() {
                                             VoiceChannel new_voice_channel = event.getGuild().getCategoryById(Config.get("TEMPORARY_CATEGORY")).createVoiceChannel(userName).complete();
                                             event.getGuild().moveVoiceMember(event.getMember(),new_voice_channel).complete();
                                         }
                                     },
                        5*1000);
                else
                    new Timer().schedule(new TimerTask() {
                                             @Override
                                             public void run() {
                                                 event.getGuild().kickVoiceMember(event.getMember()).complete();
                                             }
                                         },
                            5*1000);
            }
        }
    }
}
