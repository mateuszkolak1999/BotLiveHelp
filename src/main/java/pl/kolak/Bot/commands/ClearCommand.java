package pl.kolak.Bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import pl.kolak.Bot.context.CommandContext;
import pl.kolak.Bot.context.ICommand;

import java.util.List;

public class ClearCommand implements ICommand {
    EmbedBuilder error = new EmbedBuilder();

    @Override
    public void handle(CommandContext ctx) {
        setDefaultMessage();

        if (!ctx.getArgs().isEmpty()){
            try{
                List<Message> message = ctx.getChannel().getHistory()
                        .retrievePast(Integer.parseInt(ctx.getArgs().get(0))).complete();
                ctx.getChannel().deleteMessages(message).complete();
            } catch (NumberFormatException e){
                error.setTitle("Błędna wartość wiadomości do usunięcia");
                error.setDescription("Musisz wpisać liczbę całkowitą");
                ctx.getChannel().sendMessage(error.build()).queue();
            } catch (IllegalArgumentException e){
                if (Integer.parseInt(ctx.getArgs().get(0)) < 2){
                    error.setTitle("Zbyt mała ilość wiadomości do usunięcia");
                    error.setDescription("Podaj wartość z zakresu od 2 do 100");
                }else if(Integer.parseInt(ctx.getArgs().get(0)) > 100){
                    error.setTitle("Zbyt duża ilość wiadomości do usunięcia");
                    error.setDescription("Podaj wartość z zakresu od 2 do 100");
                }
                ctx.getChannel().sendMessage(error.build()).queue();
            }
        }else{
            ctx.getChannel().sendMessage(error
                    .setTitle("Podaj ilość wiadomości, które chcesz usunąć")
                    .setDescription(null)
                    .build()).queue();
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getHelp() {
        return "Czyści historię wiadomości na danym kanale";
    }

    private void setDefaultMessage(){
        error.setColor(0xff3923);
        error.setTitle(null);
        error.setDescription(null);
    }


}
