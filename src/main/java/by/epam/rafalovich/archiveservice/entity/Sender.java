package by.epam.rafalovich.archiveservice.entity;

import javax.persistence.*;

/**
 * Created by Dzmitry_Rafalovich on 1/19/2017.
 */
@Entity
@Table(name = "senders")
@NamedQueries({@NamedQuery(name = "findByEmail" , query = "from Sender s where s.info.senderEmail = :email"),
        @NamedQuery(name = "findByFax" , query = "from Sender s where s.info.senderFax = :fax"),
        @NamedQuery(name = "findByNumber", query = "from Sender s where s.info.senderPhoneNumber = :number"),
        @NamedQuery(name = "findByName", query = "from Sender s where s.senderName = :name")})
public class Sender {


    @Id
    @Column(name = "sender_id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sender_seq_gen")
    @SequenceGenerator(name = "sender_seq_gen", sequenceName = "sender_sequence" , initialValue = 1, allocationSize = 1)
    private Long senderId;
    @Column(name = "sender_name")
    private String senderName;

    @OneToOne(cascade = CascadeType.ALL, targetEntity = SenderInfo.class , mappedBy = "sender")
    private SenderInfo info;

    public Sender() {
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public SenderInfo getInfo() {
        return info;
    }

    public void setInfo(SenderInfo info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sender)) return false;

        Sender sender = (Sender) o;

        if (senderId != null ? !senderId.equals(sender.senderId) : sender.senderId != null) return false;
        if (senderName != null ? !senderName.equals(sender.senderName) : sender.senderName != null) return false;
        return info != null ? info.equals(sender.info) : sender.info == null;
    }

    @Override
    public int hashCode() {
        int result = senderId != null ? senderId.hashCode() : 0;
        result = 31 * result + (senderName != null ? senderName.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }
}