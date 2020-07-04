package pl.kolak.Bot.commands;

import jdk.internal.org.objectweb.asm.commons.SerialVersionUIDAdder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONException;
import org.json.JSONObject;
import pl.kolak.Bot.config.JsonManager;
import pl.kolak.Bot.context.CommandContext;
import pl.kolak.Bot.context.ICommand;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PremiumCommand implements ICommand {
    private final EmbedBuilder messageEmbed = new EmbedBuilder();

    @Override
    public void handle(CommandContext ctx) {
        reset();
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        List<String> history_names = new ArrayList<>();
        JSONObject json = null;
        String name1;
        List<JSONObject> json_list = null;
        StringBuilder stringBuilder = new StringBuilder();

        if (args.size() == 1){
            String name = args.get(0).replace("<@!", "")
                    .replace(">", "");
            try{
                long l = Long.parseLong(name);
                name1 = ctx.getGuild().getMemberById(name).getNickname();
            }catch (Exception e){
                name1=name;
            }

            try {
                json = JsonManager.readJsonFromUrl("https://api.mojang.com/users/profiles/minecraft/" + name1);
            } catch (IOException e) {
                EmbedBuilder message =messageEmbed.setTitle("Gracz o nicku " + name1 + " nie istnieje")
                        .setColor(Color.RED);
                channel.sendMessage(message.build()).queue();
                return;
            }

            Object uuid = json.get("id");

            try {
                json_list = readJsonFromUrlToNames("https://api.mojang.com/user/profiles/" + uuid + "/names");
            } catch (IOException e) {
                e.printStackTrace();
            }

            String description = "-> Nick: " + name1 + "\n"
                                + "-> UUID: " + uuid + "\n"
                                + "-> Historia nicków: \n";

            for(int i=0; i< json_list.size(); i++){
                history_names.add((String)json_list.get(i).get("name"));
            }

            stringBuilder.append(description);
            history_names.forEach(s->stringBuilder.append("✓").append(s).append("\n"));
            EmbedBuilder message = messageEmbed.setTitle("Profil gracza: " + name1)
                    .setColor(Color.MAGENTA)
                    .setImage("https://minotar.net/avatar/" + uuid + "/100")
                    .setDescription(stringBuilder);
            channel.sendMessage(message.build()).queue();

        }

    }

    @Override
    public String getName() {
        return "premium";
    }

    @Override
    public String getHelp() {
        return "Informacje o użytkowniku Minecraft";
    }

    private void reset(){
        messageEmbed.setTitle(null)
                .setDescription(null)
                .setColor(Color.MAGENTA)
                .setAuthor(null)
                .setFooter(null)
                .setImage(null);
    }


    private  List<JSONObject> readJsonFromUrlToNames(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String[] jsonText = readAll(rd).replace("[","").replace("]","").split("},");
            for (int i = 0; i < jsonText.length-1; i++) {
                jsonText[i] += "}";
            }
            List<JSONObject> json = new ArrayList<>();
            for (String s : jsonText) {
                json.add(new JSONObject(s));
            }
            return json;
        } finally {
            is.close();
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
