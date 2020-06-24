package pl.kolak.Bot.commands;

import net.dv8tion.jda.api.JDA;
import pl.kolak.Bot.context.CommandContext;
import pl.kolak.Bot.context.ICommand;


public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping)->ctx.getChannel()
                .sendMessageFormat("Ping: %sms\nPing bramy: %sms",ping,jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "Pokazuje ping jaki jest w połączeniu z danym botem";
    }

}
