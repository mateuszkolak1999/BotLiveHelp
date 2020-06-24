package pl.kolak.Bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import pl.kolak.Bot.config.CommandManager;
import pl.kolak.Bot.config.Config;
import pl.kolak.Bot.context.CommandContext;
import pl.kolak.Bot.context.ICommand;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;
    private final EmbedBuilder messageEmbed = new EmbedBuilder();

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()){
            StringBuilder builder = new StringBuilder();

            manager.getCommandsList()
                    .stream()
                    .map(ICommand::getName)
                    .forEach(
                            (c)->builder.append(Config.get("prefix"))
                                    .append(c)
                                    .append(" - ")
                                    .append(manager.getCommand(c).getHelp())
                                    .append("\n")
                    );


            messageEmbed.setTitle("Lista komend, których możesz użyć:")
                    .setDescription(builder.toString())
                    .setColor(Color.MAGENTA);
            channel.sendMessage(messageEmbed.build()).queue();

            //ctx.getEvent().getAuthor().openPrivateChannel().complete().sendMessage("AAAA").queue();
            return;
        }

        ICommand command = manager.getCommand(args.get(0));

        if(command == null){
            channel.sendMessage(messageEmbed.setColor(0xff3923)
                    .setTitle("Brak pomocy dla komendy: "  + args.get(0))
                    .setDescription(null)
                    .build()).queue();
            return;
        }

        channel.sendMessage(messageEmbed
                .setTitle("Działanie:")
                .setDescription(command.getHelp())
                .setColor(Color.MAGENTA)
                .build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Pokazuje listę komend. Użycie:"  + Config.get("prefix") + "help [komenda]";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("commands", "cmd", "commandlist", "komendy");
    }
}
