package gcg.dent.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Company company;

    @OneToMany(mappedBy = "card", fetch = FetchType.EAGER)
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