package gcg.dent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "act_service", schema = "public")
public class ActService implements Serializable {
    @Id
    private Long aid;
    @Id
    private Long sid;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aid")
    private Act act;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sid")
    private Service service;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "date", nullable = false)
    private Date date;

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActService that = (ActService) o;
        return aid.equals(that.aid) &&
                sid.equals(that.sid) &&
                amount.equals(that.amount) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aid, sid, amount, date);
    }
}