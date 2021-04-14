package gcg.dent.entity;

import javax.persistence.*;

@Entity
@Table(name = "service", schema = "public")
public class Service {
    @Id
    @SequenceGenerator(name = "service_id_seq", sequenceName = "public.service_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atid", nullable = false)
    private ActType actType;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public ActType getActType() {
        return actType;
    }

    public void setActType(ActType actType) {
        this.actType = actType;
    }
}
