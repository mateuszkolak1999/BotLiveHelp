package pl.kolak.Bot.DAO;

import pl.kolak.Bot.model.Admin;

public interface AdminDao {
    void add(Admin admin);
    Admin get(Long discord_id);
    void update(Admin admin);
    void delete(Long discord_id);
}
