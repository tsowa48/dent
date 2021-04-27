package gcg.dent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gcg.dent.util.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "card", schema = "public")
public class Card {
    @Id
    @SequenceGenerator(name = "card_id_seq", sequenceName = "public.card_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "date", nullable = false)
    private Date date;

    @JsonIgnore
    @Column(name = "pid", nullable = false, insertable = false, updatable = false)
    private Long pid;

    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name = "pid", insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY)
    private List<History> history = new ArrayList<>();

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

    public String getDate() {
        return ObjectUtils.dateFormat.format(this.date);
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }
}