package gcg.dent.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contract", schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class, defaultForType = JsonNode.class)
public class Contract {
    @Id
    @SequenceGenerator(name = "contract_id_seq", sequenceName = "public.contract_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "date", nullable = false)
    private Date date;

    @Type(type = "jsonb")
    @Column(name = "props", columnDefinition = "jsonb")
    private JsonNode props;

    @OneToMany(mappedBy = "contract")
    private List<Act> acts = new ArrayList<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public JsonNode getProps() {
        return props;
    }

    public void setProps(JsonNode props) {
        this.props = props;
    }

    public void setActs(List<Act> acts) {this.acts = acts; }

    public List<Act> getActs() {return acts; }
}