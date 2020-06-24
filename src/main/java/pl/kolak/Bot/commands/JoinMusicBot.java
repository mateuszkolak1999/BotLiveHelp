package pl.kolak.Bot.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import pl.kolak.Bot.context.CommandContext;
import pl.kolak.Bot.context.ICommand;

public class JoinMusicBot implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        TextChannel channel = ctx.getEvent().getChannel();
        AudioManager audioManager = ctx.getEvent().getGuild().getAudioManager();

        if (audioManager.isConnected()) {
            channel.sendMessage("Połączono z serwerem").queue();
            return;
        }

        GuildVoiceState memberVoiceState = ctx.getEvent().getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("Musisz dołączyć pierwszy do kanału głosowego").queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = ctx.getEvent().getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            channel.sendMessageFormat("Nie mam permisji aby dołączyć do %s", voiceChannel).queue();
            return;
        }

        audioManager.openAudioConnection(voiceChannel);
        channel.sendMessage("Połączono z kanałem").queue();
    }

    @Override
    public String getHelp() {
        return "łączy bota LiveHelp z kanałem";
    }

    @Override
    public String getName() {
        return "join";
    }
}
