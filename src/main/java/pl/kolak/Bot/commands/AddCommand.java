package pl.kolak.Bot.commands;

import pl.kolak.Bot.DAO.AdminDao;
import pl.kolak.Bot.DAO.AdminDaoImpl;
import pl.kolak.Bot.context.CommandContext;
import pl.kolak.Bot.context.ICommand;
import pl.kolak.Bot.model.Admin;

public class AddCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        String name = ctx.getEvent().getAuthor().getName();
        long id = Long.parseLong(ctx.getEvent().getAuthor().getId());

        Admin admin = new Admin(id, name, 20L);
        AdminDao dao = new AdminDaoImpl();
        System.out.println(admin);
        dao.add(admin);
        ctx.getEvent().getChannel().sendMessage("Dodalem " + admin.getUsername()).queue();
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getHelp() {
        return "be be be";
    }
}
