package gcg.dent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import gcg.dent.util.ObjectUtils;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "history", schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class, defaultForType = JsonNode.class)
public class History {
    @Id
    @SequenceGenerator(name = "history_id_seq", sequenceName = "public.history_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Card card;

    @Type(type = "jsonb")
    @Column(name = "props", columnDefinition = "jsonb")
    private JsonNode props;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did")
    private Contract contract;

    @Column(name = "date", nullable = false)
    private Date date;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setProps(JsonNode props) {
        this.props = props;
    }

    public JsonNode getProps() {
        return props;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Contract getContract() {
        return contract;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDate() {
        return ObjectUtils.dateFormat.format(date);
    }

    public Date getDateAsDate() {
        return this.date;
    }
}