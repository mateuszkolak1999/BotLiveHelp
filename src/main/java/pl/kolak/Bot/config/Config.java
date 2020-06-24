package pl.kolak.Bot.config;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv DOTENV = Dotenv.configure()
            .directory("./")
       //.ignoreIfMalformed()
       // .ignoreIfMissing()
        .load();

    public static String get(String key){
        return DOTENV.get(key.toUpperCase());
    }
}
