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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="cid" )
    private Company company;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "post", nullable = false)
    private String post;

    @Column(name = "is_scheduled", nullable = false)
    private boolean isScheduled;


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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }
}
