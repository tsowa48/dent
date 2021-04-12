package gcg.dent.entity;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "schedule", schema = "public")
public class Schedule {
    @Id
    @SequenceGenerator(name = "schedule_id_seq", sequenceName = "public.schedule_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "start", nullable = false)
    private Time start;

    @Column(name = "end", nullable = false)
    private Time end;

    @Column(name = "dow", nullable = false)
    private int dow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public int getDow() {
        return dow;
    }

    public void setDow(int dow) {
        this.dow = dow;
    }
}
