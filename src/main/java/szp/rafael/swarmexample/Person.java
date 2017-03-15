package szp.rafael.swarmexample;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by rafaelszp on 12/7/16.
 */
@Entity
@Table(name="PERSON")
@XmlRootElement
public class Person {

    @Id
    @Column(name="PERSON_ID")
    private Long id;

    @Column(name="PERSON_NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
