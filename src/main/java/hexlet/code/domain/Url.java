package hexlet.code.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import io.ebean.annotation.WhenCreated;
import org.intellij.lang.annotations.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Url extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private final String name;

    @NotNull
    @WhenCreated
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Url(String pName) {
        this.name = pName;
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
