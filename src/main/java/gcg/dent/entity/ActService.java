package gcg.dent.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@IdClass(ActServiceId.class)
@Table(name = "act_service", schema = "public")
public class ActService {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid")
    private Service sid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aid")
    private Act aid;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "date", nullable = false)
    private Date date;

    public void setSid(Service sid) {this.sid = sid; }

    public Service getSid() {return sid; }

    public void setAid(Act aid) {this.aid = aid; }

    public Act getAid() {return aid; }

    public void setAmount(Integer amount) {this.amount = amount; }

    public Integer getAmount() {return amount; }

    public void setDate(Date date) {this.date = date; }

    public Date getDate() {return date; }
}
