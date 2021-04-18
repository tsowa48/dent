package gcg.dent.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "act", schema = "public")
public class Act {
    @Id
    @SequenceGenerator(name = "act_id_seq", sequenceName = "public.act_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "act_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "doc", nullable = false)
    private Long doc;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did", nullable = false)
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atid", nullable = false)
    private ActType actType;

    @OneToMany(mappedBy = "act")
    private List<ActService> actServices = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ActType getActType() {
        return actType;
    }

    public void setActType(ActType actType) {
        this.actType = actType;
    }

    public void setDoc(Long doc) {this.doc = doc; }

    public Long getDoc() {return doc; }

    public void setActServices(List<ActService> actServises) {this.actServices = actServises; }

    public List<ActService> getActServices() {return actServices; }
}
