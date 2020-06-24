package pl.kolak.Bot.events;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;


public class CheckStatusServerMinecraft extends ListenerAdapter {

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        JSONObject json = null;
        try {
            json = readJsonFromUrl("https://mcapi.us/server/status?ip=62.171.160.117&port=25577");
            long size = event.getJDA().getGuildCache().size();
            System.out.println(size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextChannel textChannel = event.getGuild().getTextChannels().get(0);
        if((boolean)json.get("online")){
            textChannel.getManager().setName("online").complete();
        }
        else
            textChannel.getManager().setName("offline").queue();
    }

    @Override
    public void onGuildLeave(@Nonnull GuildLeaveEvent event) {
        JSONObject json = null;
        long size = event.getJDA().getGuildCache().size();
        System.out.println(size);
        try {
            json = readJsonFromUrl("https://mcapi.us/server/status?ip=62.171.160.117&port=25577");
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextChannel textChannel = event.getGuild().getTextChannels().get(0);
        if((boolean)json.get("online")){
            textChannel.getManager().setName("online").complete();
        }
        else
            textChannel.getManager().setName("offline").queue();
    }

    @Override
    public void onUserUpdateOnlineStatus(@Nonnull UserUpdateOnlineStatusEvent event) {
        JSONObject json = null;
        try {
            json = readJsonFromUrl("https://mcapi.us/server/status?ip=62.171.160.117&port=25577");
            } catch (IOException e) {
            e.printStackTrace();
        }
        TextChannel textChannel = event.getGuild().getTextChannels().get(0);
        if((boolean)json.get("online")) {
            textChannel.getManager().setName("online").queue();
        }else
            textChannel.getManager().setName("offline").queue();
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
