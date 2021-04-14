package gcg.dent.entity;

import javax.persistence.*;

@Entity
@Table(name = "act_type", schema = "public")
public class ActType {
    @Id
    @SequenceGenerator(name = "act_type_id_seq", sequenceName = "public.act_type_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "act_type_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
