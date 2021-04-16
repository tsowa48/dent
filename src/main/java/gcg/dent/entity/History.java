package gcg.dent.entity;

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
}
