package gcg.dent.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contract", schema = "public")
public class Contract {
    @Id
    @SequenceGenerator(name = "contact_id_seq", sequenceName = "public.contact_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_id_seq")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "number", nullable = false)
    private Long number;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "warranty", nullable = false)
    private String warranty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="doc")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    private Client client;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech")
    private */


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

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}