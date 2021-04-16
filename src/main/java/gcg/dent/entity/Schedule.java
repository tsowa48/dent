package gcg.dent.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import gcg.dent.util.ObjectUtils;

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

    @Column(name = "finish", nullable = false)
    private Time finish;

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

    public Time getFinish() {
        return finish;
    }

    public void setFinish(Time finish) {
        this.finish = finish;
    }

    public int getDow() {
        return dow;
    }

    public void setDow(int dow) {
        this.dow = dow;
    }

    @JsonProperty(value = "dayOfWeek", access = JsonProperty.Access.READ_ONLY)
    public String getDayOfWeek() {
        return ObjectUtils.weekDaysFull[getDow()];
    }
}
