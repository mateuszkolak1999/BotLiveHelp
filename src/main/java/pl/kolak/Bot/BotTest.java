package pl.kolak.Bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import pl.kolak.Bot.config.Config;
import pl.kolak.Bot.config.Listener;
import pl.kolak.Bot.events.*;

import javax.security.auth.login.LoginException;

public class BotTest {

    private BotTest() throws LoginException {
        new JDABuilder(Config.get("TOKEN"))
                .addEventListeners(new Listener())
                .addEventListeners(new CheckNameEvent())
                .addEventListeners(new CheckCurseEvent())
                .addEventListeners(new CheckStatusServerMinecraft())
                .addEventListeners(new VoiceHelp())
                .addEventListeners(new CheckTemporaryChannel())
                .addEventListeners(new MoveUserWithHelpToAdmin())
                .addEventListeners(new MoveFromWaitingRoomToLiveHelp())
                .addEventListeners(new CheckOnlineUserOnServerMinecraft())
                .addEventListeners(new CheckPingOnMinecraftServer())
                .setActivity(Activity.listening("Matiego <3"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new BotTest();
    }
}
