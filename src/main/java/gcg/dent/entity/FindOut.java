package gcg.dent.entity;

import javax.persistence.*;

@Entity
@Table(name = "find_out", schema = "public")
public class FindOut {
    @Id
    @SequenceGenerator(name = "findOut_id_seq", sequenceName = "public.findOut_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "findOut_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

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

}