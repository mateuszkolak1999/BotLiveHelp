package pl.kolak.Bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import pl.kolak.Bot.config.Config;
import pl.kolak.Bot.context.CommandContext;
import pl.kolak.Bot.context.ICommand;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MuteCommand implements ICommand {
    EmbedBuilder embedBuilder;

    @Override
    public void handle(CommandContext ctx) {
        embedBuilder = new EmbedBuilder(new EmbedBuilder().setTitle(null).setDescription(null).setColor(null));
        Role role = ctx.getGuild().getRoleById(Config.get("mute_role"));

        if(!ctx.getArgs().isEmpty()) {
            if (ctx.getArgs().toArray().length >= 1 && ctx.getArgs().toArray().length < 3) {
                Member member = ctx.getGuild().getMemberById(ctx.getArgs().get(0)
                        .replace("<@!", "")
                        .replace(">", ""));
                if (!member.getRoles().contains(role)) {
                    embedBuilder.setTitle("Wyciszenie")
                            .setColor(Color.MAGENTA)
                            .setDescription("Nadano wyciszenie na użytkownika " + ctx.getArgs().get(0));
                    if (ctx.getArgs().size() == 2) {
                        embedBuilder.setDescription(null)
                                .setDescription("Nadano wyciszenie na użytkownika " + ctx.getArgs().get(0)
                                        + " na okres " + ctx.getArgs().get(1) + " sekund");

                        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
                        ctx.getGuild().addRoleToMember(member, role).complete();

                        new Timer().schedule(new TimerTask() {
                                                 @Override
                                                 public void run() {
                                                     embedBuilder.setDescription(null)
                                                             .setDescription("Zdjęto czasowe wyciszenie z użytkownika " + ctx.getArgs().get(0));
                                                     ctx.getChannel().sendMessage(embedBuilder.build()).queue();
                                                     ctx.getGuild().removeRoleFromMember(member, role).complete();
                                                 }
                                             },
                                Integer.parseInt(ctx.getArgs().get(1)) * 1000);
                        return;
                    }

                    embedBuilder.setDescription(null)
                            .setDescription("Nadano wyciszenie na użytkownika " + ctx.getArgs().get(0));
                    ctx.getChannel().sendMessage(embedBuilder.build()).queue();
                    ctx.getGuild().addRoleToMember(member, role).complete();
                } else {
                    embedBuilder.setTitle("Wyciszenie")
                            .setColor(Color.MAGENTA)
                            .setDescription("Zdjęto wyciszenie z użytkownika " + ctx.getArgs().get(0));
                    ctx.getChannel().sendMessage(embedBuilder.build()).queue();
                    ctx.getGuild().removeRoleFromMember(member, role).complete();
                }
            } else {
                embedBuilder.setTitle("Błąd:")
                        .setColor(Color.RED)
                        .setDescription("Poprawne użycie: " + Config.get("prefix")
                                + " " + getName() + " [nazwa użytkownika] [czas {opcjonalne}]");
                ctx.getChannel().sendMessage(embedBuilder.build()).queue();
            }
        }else{
            embedBuilder.setTitle("Wyciszenie")
                    .setColor(Color.magenta)
                    .setDescription("Poprawne użycie: " + Config.get("prefix")
                            + " " + getName() + " [nazwa użytkownika] [czas {opcjonalne}]");
            ctx.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public String getHelp() {
        return "Wycisza gracza na określony czas";
    }
}
