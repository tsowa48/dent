package gcg.dent.entity;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "history", schema = "public")
public class History {
    @Id
    @SequenceGenerator(name = "history_id_seq", sequenceName = "public.history_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Card card;

    @Type(type = "jsonb")
    @Column(name = "props", columnDefinition = "jsonb")
    private JsonNode props;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did")
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc")
    private Employee employee;


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

    public void setProps(JsonNode props) {this.props = props; }

    public JsonNode getProps() {return props; }

    public void setContract(Contract contract) {this.contract = contract; }

    public Contract getContract() {return contract; }

    public void setEmployee(Employee employee) {this.employee = employee; }

    public Employee getEmployee() {return employee; }
}
