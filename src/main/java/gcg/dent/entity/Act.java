package gcg.dent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gcg.dent.util.ObjectUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "act", schema = "public")
public class Act implements Serializable {
    @Id
    @SequenceGenerator(name = "act_id_seq", sequenceName = "public.act_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "act_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "number", nullable = false)
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc")
    private Employee doctor;

    @Column(name = "date", nullable = false)
    private Date date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "did", nullable = false)
    private Contract contract;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "atid", nullable = false)
    private ActType actType;

    @OneToMany(mappedBy = "act", fetch = FetchType.EAGER)
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

    public String getDate() {
        return ObjectUtils.dateFormat.format(date);
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

    public Employee getDoctor() {
        return doctor;
    }

    public void setDoctor(Employee doctor) {
        this.doctor = doctor;
    }

    public List<ActService> getActServices() {
        return actServices;
    }

    public void setActServices(List<ActService> actServices) {
        this.actServices = actServices;
    }
}