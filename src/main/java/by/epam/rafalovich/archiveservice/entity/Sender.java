package by.epam.rafalovich.archiveservice.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    private Long senderId;
    private String senderName;
    private SenderInfo info;

    @Id
    @Column(name = "sender_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sender_seq_gen")
    @SequenceGenerator(name = "sender_seq_gen", sequenceName = "sender_sequence" , initialValue = 1, allocationSize = 1)
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    @Column(name = "sender_name")
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @OneToOne(cascade = CascadeType.ALL, targetEntity = SenderInfo.class , mappedBy = "sender")
    public SenderInfo getInfo() {
        return info;
    }

    public void setInfo(SenderInfo info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {

        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {

        return "Sender{" +
                "senderId=" + senderId +
                ", senderName='" + senderName + '\'' +
                ", info=" + info +
                '}';
    }
}