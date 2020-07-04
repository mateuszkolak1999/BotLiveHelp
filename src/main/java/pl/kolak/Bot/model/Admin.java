package pl.kolak.Bot.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Admin implements Serializable {
    private static final Long versionUID=1L;

    @Id
    @Column(nullable = false)
    private Long discord_user_id;
    @Column(nullable = false,length = 20)
    private String username;
    private Long points;

    public Admin() {
    }

    public Admin(Long discord_user_id, String username, Long points) {
        this.discord_user_id = discord_user_id;
        this.username = username;
        this.points = points;
    }

    public Long getDiscord_user_id() {
        return discord_user_id;
    }

    public void setDiscord_user_id(Long discord_user_id) {
        this.discord_user_id = discord_user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "discord_user_id=" + discord_user_id +
                ", username='" + username + '\'' +
                ", points=" + points +
                '}';
    }
}
