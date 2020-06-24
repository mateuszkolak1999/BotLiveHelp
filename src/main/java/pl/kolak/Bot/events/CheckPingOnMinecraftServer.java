package pl.kolak.Bot.events;

import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;
import pl.kolak.Bot.config.Config;
import pl.kolak.Bot.config.JsonManager;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CheckPingOnMinecraftServer extends ListenerAdapter {
    private JSONObject json;
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        checkPing(event);
    }

    private void checkPing(GenericGuildEvent event){
        json=null;
        try{
            json = JsonManager.readJsonFromUrl("https://mcapi.xdefcon.com/server/62.171.160.117:25577/full/json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object ping = json.get("ping");
        String title = "PING: [%ping% ms]".replace("%ping%",ping.toString());
        event.getGuild().getVoiceChannelById(Config.get("PING_MINECRAFT_SERVER"))
                .getManager()
                .setName(title)
                .queue();
    }
}
