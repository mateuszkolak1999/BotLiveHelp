package pl.kolak.Bot.events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;
import pl.kolak.Bot.config.Config;
import pl.kolak.Bot.config.JsonManager;

import javax.annotation.Nonnull;
import java.io.IOException;

public class CheckOnlineUserOnServerMinecraft extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        readJSON(event);
    }

    private void readJSON(GenericGuildEvent event){
        JSONObject json = null;
        try {
            json = JsonManager.readJsonFromUrl("https://mcapi.us/server/status?ip=62.171.160.117&port=25577");
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextChannel textChannel = event.getGuild().getTextChannelById(Config.get("ONLINE_USER_ON_SERVER_MINECRAFT"));
        Object players = json.getJSONObject("players").get("now");
        String title = "online-%x%-graczy".replace("%x%",players.toString());
        textChannel.getManager().setName(title).queue();
    }
}
