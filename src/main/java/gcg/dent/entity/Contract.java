package gcg.dent.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contract", schema = "public")
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
}