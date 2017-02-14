package by.epam.rafalovich.archiveservice.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by Dzmitry_Rafalovich on 1/20/2017.
 */
@Entity
@Table(name = "sender_contacts")
@NamedQueries({@NamedQuery(name = "findByNumberInfo" ,
        query = "from SenderInfo s where s.senderPhoneNumber = :number"),
        @NamedQuery(name = "findByFaxInfo" ,
        query = "from SenderInfo s where s.senderFax = :fax"),
        @NamedQuery(name = "findByEmailInfo" ,
        query = "from SenderInfo s where s.senderEmail = :email")})
public class SenderInfo {

    @Id
    @GeneratedValue(generator = "myForeignGenerator")
    @GenericGenerator(name = "myForeignGenerator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "sender"))
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String senderEmail;
    @Column(name = "fax_number")
    private String senderFax;
    @Column(name = "telephone_number")
    private String senderPhoneNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Sender.class)
    @PrimaryKeyJoinColumn
    private Sender sender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderFax() {
        return senderFax;
    }

    public void setSenderFax(String senderFax) {
        this.senderFax = senderFax;
    }

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    @JsonIgnore
    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SenderInfo)) return false;

        SenderInfo that = (SenderInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (senderEmail != null ? !senderEmail.equals(that.senderEmail) : that.senderEmail != null) return false;
        if (senderFax != null ? !senderFax.equals(that.senderFax) : that.senderFax != null) return false;
        return senderPhoneNumber != null ? senderPhoneNumber.equals(that.senderPhoneNumber) : that.senderPhoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (senderEmail != null ? senderEmail.hashCode() : 0);
        result = 31 * result + (senderFax != null ? senderFax.hashCode() : 0);
        result = 31 * result + (senderPhoneNumber != null ? senderPhoneNumber.hashCode() : 0);
        return result;
    }
}
