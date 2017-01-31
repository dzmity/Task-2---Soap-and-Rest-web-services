package by.epam.rafalovich.archiveservice.entity;
import javax.persistence.*;

/**
 *
 * Recipient is used to persist and retrieve objects using hibernate.
 */
@Entity
@Table(name = "recipients")
@NamedQueries({@NamedQuery(name = "findByContact", query = "from Recipient r where r.recipientContact = :contact")})
public class Recipient {
    @Id
    @Column(name = "recipient_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "recipient_seq_gen")
    @SequenceGenerator(name = "recipient_seq_gen", sequenceName = "recipient_sequence" , initialValue = 1, allocationSize = 1)
    private Long recipientId;
    @Column(name = "CHANNEL_ID")
    @Enumerated(EnumType.ORDINAL)
    private CommunicationChannel chanel;
    @Column(name = "recipient_contact")
    private String recipientContact;

    public Recipient() {
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public CommunicationChannel getChanel() {
        return chanel;
    }

    public void setChanel(CommunicationChannel chanel) {
        this.chanel = chanel;
    }

    public String getRecipientContact() {
        return recipientContact;
    }

    public void setRecipientContact(String recipientContact) {
        this.recipientContact = recipientContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipient)) return false;

        Recipient recipient = (Recipient) o;

        if (recipientId != null ? !recipientId.equals(recipient.recipientId) : recipient.recipientId != null)
            return false;
        if (chanel != recipient.chanel) return false;
        return recipientContact != null ? recipientContact.equals(recipient.recipientContact) : recipient.recipientContact == null;
    }

    @Override
    public int hashCode() {
        int result = recipientId != null ? recipientId.hashCode() : 0;
        result = 31 * result + (chanel != null ? chanel.hashCode() : 0);
        result = 31 * result + (recipientContact != null ? recipientContact.hashCode() : 0);
        return result;
    }
}
