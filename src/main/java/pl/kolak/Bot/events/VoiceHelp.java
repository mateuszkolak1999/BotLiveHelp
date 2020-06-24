package pl.kolak.Bot.events;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import pl.kolak.Bot.config.Config;
import pl.kolak.Bot.music.PlayerManager;

import javax.annotation.Nonnull;
import java.util.List;

public class VoiceHelp extends ListenerAdapter {
    private PlayerManager playerManager = new PlayerManager();

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        VoiceChannel channelJoined = event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID"));
        List<Member> membersWithRoles = event.getGuild().getMembersWithRoles(event.getJDA().getRoleById("719576891509702678"));
        if(event.getChannelJoined().equals(channelJoined)){
            audioManager.openAudioConnection(channelJoined);
            playerManager.loadAndPlay(event.getJDA().getTextChannelById(710931937111310340L),"https://soundcloud.com/kelo-669115367/mix-by-audio-joinercom/s-y8aLdZrVgOS");

            for (Member m:membersWithRoles) {
                if(m.getOnlineStatus().getKey().toLowerCase().equals(OnlineStatus.ONLINE.getKey().toLowerCase())){
                    List<Member> members = event.getChannelJoined().getMembers();
                    for (Member mem: members){
                        if(!mem.getUser().isBot()){
                            m.getUser().openPrivateChannel().complete()
                                    .sendMessage("Użytkownik " + mem.getUser().getName() + " oczekuje na Twoją pomoc!").queue();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        VoiceChannel channelLeave = event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID"));
        if(event.getChannelLeft().equals(channelLeave)){
            playerManager.getGuildMusicManager(event.getGuild()).scheduler.getQueue().clear();
            playerManager.getGuildMusicManager(event.getGuild()).player.stopTrack();
            audioManager.closeAudioConnection();
        }
    }

    @Override
    public void onGuildVoiceMove(@Nonnull GuildVoiceMoveEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        VoiceChannel channel = event.getGuild().getVoiceChannelById(Config.get("HELP_CHANNEL_ID"));
        List<Member> membersWithRoles = event.getGuild().getMembersWithRoles(event.getJDA().getRoleById("719576891509702678"));
        if(event.getChannelLeft().equals(channel)/*!event.getChannelJoined().equals(channel) && channel.getMembers().size()==1*/){
            playerManager.getGuildMusicManager(event.getGuild()).scheduler.getQueue().clear();
            playerManager.getGuildMusicManager(event.getGuild()).player.stopTrack();
            audioManager.closeAudioConnection();
        }else if(event.getChannelJoined().equals(channel)){
            audioManager.openAudioConnection(channel);
            playerManager.loadAndPlay(event.getJDA().getTextChannelById(710931937111310340L),"https://soundcloud.com/kelo-669115367/mix-by-audio-joinercom/s-y8aLdZrVgOS");

            for (Member m:membersWithRoles) {
                if(m.getOnlineStatus().getKey().toLowerCase().equals(OnlineStatus.ONLINE.getKey().toLowerCase())){
                    List<Member> members = event.getChannelJoined().getMembers();
                    for (Member mem: members){
                        if(!mem.getUser().isBot()){
                            m.getUser().openPrivateChannel().complete()
                                    .sendMessage("Użytkownik " + mem.getUser().getName() + " oczekuje na Twoją pomoc!").queue();
                        }
                    }
                }
            }
        }
    }
}
