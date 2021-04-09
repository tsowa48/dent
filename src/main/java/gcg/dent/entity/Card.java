package gcg.dent.entity;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "diagnosis", nullable = false)
    private String diagnostic;

    @Column(name = "complaints", nullable = false)
    private String complaints;

    @Column(name = "anamnesis", nullable = false)
    private String anamnesis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc")
    private Employee doc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Client client;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public void setAnamnesis(String anamnesis) {
        this.anamnesis = anamnesis;
    }

    public String getAnamnesis() {
        return anamnesis;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Employee getDoc() {
        return doc;
    }

    public void setDoc(Employee doc) {
        this.doc = doc;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}