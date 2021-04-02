package gcg.dent.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "client", schema = "public")
public class Client {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "birth", nullable = false)
    private Date birth;

    @Column(name = "sex", nullable = false)
    private Boolean isMale;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "fid")
    //private FindOut findOut;


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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Boolean getMale() {
        return isMale;
    }

    public void setMale(Boolean male) {
        isMale = male;
    }
}
