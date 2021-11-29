package hexlet.code.domain;

import io.ebean.Model;
import io.ebean.annotation.NotNull;
import io.ebean.annotation.WhenCreated;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<UrlCheck> urlCheck;

    public Url(String pTransmittedUrl) {
        this.name = pTransmittedUrl;
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

    public final List<UrlCheck> getUrlCheck() {
        return urlCheck;
    }
}
