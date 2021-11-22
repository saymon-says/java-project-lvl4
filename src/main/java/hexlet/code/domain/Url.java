package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.NotNull;
import io.ebean.annotation.WhenCreated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Url extends Model {

    @Id
    private long id;

    private final String name;

    @NotNull
    @WhenCreated
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Url(String name) {
        this.name = name;
    }

    public final long getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final Date getCreatedAt() {
        return createdAt;
    }
}
