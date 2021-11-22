package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Url extends Model {

    @Id
    private long id;

    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Url(String dbName, long id, String name, Date createdAt) {
        super(dbName);
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
