package gcg.dent.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee", schema = "public")
public class Employee {
    @Id
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "public.employee_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "type", nullable = false)
    private int type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
