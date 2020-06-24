package pl.kolak.Bot.events;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckCurseEvent extends ListenerAdapter {
    private static final String FILE = "curse.txt";

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        List<String> curse = downloadCurseWithFile();
        Message message = event.getMessage();

        for (String c:curse) {
            if (message.getContentRaw().contains(c))
                message.delete().queue();
        }
    }

    List<String> downloadCurseWithFile(){
        List<String > temp = new ArrayList<>();
        try(
                BufferedReader read = new BufferedReader(new FileReader(FILE))
                )
        {
            String nextLine = null;
            while ((nextLine = read.readLine())!=null){
                temp.add(nextLine);
            }
            Collections.sort(temp);
        } catch (IOException e) {
            System.err.println("Błąd ładowania pliku");
        }
        return temp;
    }
}
