package pl.kolak.Bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import pl.kolak.Bot.config.Config;
import pl.kolak.Bot.context.CommandContext;
import pl.kolak.Bot.context.ICommand;

import java.awt.*;

public class KickCommand implements ICommand {
    EmbedBuilder embedBuilder;
    @Override
    public void handle(CommandContext ctx) {
        embedBuilder = new EmbedBuilder(new EmbedBuilder().setTitle(null).setDescription(null).setColor(null));
        Role role = ctx.getGuild().getRoleById(Config.get("ADMIN_ROLE"));
        if(!ctx.getArgs().isEmpty()){
            if(ctx.getArgs().toArray().length == 1){
                Member member = ctx.getGuild().getMemberById(ctx.getArgs().get(0)
                        .replace("<@!", "")
                        .replace(">", ""));
                if (ctx.getMember().getRoles().contains(role)) {
                    embedBuilder.setTitle("Wyrzucenie")
                            .setColor(Color.MAGENTA)
                            .setDescription("Wyrzucono użytkownika " + ctx.getArgs().get(0));
                    ctx.getChannel().sendMessage(embedBuilder.build()).queue();
                    ctx.getGuild().kick(member).complete();
                }else{
                    embedBuilder.setTitle("Wyrzucenie")
                            .setColor(Color.RED)
                            .setDescription("Nie masz uprawnien do użycia tej komendy");
                    ctx.getChannel().sendMessage(embedBuilder.build()).queue();
                }

            }else{
                embedBuilder.setTitle("Wyrzucenie")
                        .setColor(Color.RED)
                        .setDescription("Błędne użycie. Użyj " + Config.get("prefix") + getName() + " [UserName]");
                ctx.getChannel().sendMessage(embedBuilder.build()).queue();
            }
        }else{
            embedBuilder.setTitle("Wyrzucenie")
                    .setColor(Color.RED)
                    .setDescription("Błędne użycie. Użyj " + Config.get("prefix") + getName() + " [UserName]");
            ctx.getChannel().sendMessage(embedBuilder.build()).queue();
        }

    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getHelp() {
        return "wyrzuca gracza z serwera disord";
    }
}
