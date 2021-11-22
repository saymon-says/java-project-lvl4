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
    private final long id;

    private final String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private final Date createdAt;

    public Url(long pId, String pName, Date pCreatedAt) {
        this.id = pId;
        this.name = pName;
        this.createdAt = pCreatedAt;
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
