package by.epam.rafalovich.archiveservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Dzmitry_Rafalovich on 1/19/2017.
 */
@Entity
@Table(name = "transactions")
@NamedQueries({@NamedQuery(name = "findBySender", query = "from CommunicationRecord t where t.sender.senderId = :senderId"),
        @NamedQuery(name = "findByRecipient", query = "from CommunicationRecord t where t.recipient.recipientId = :recipientId"),
        @NamedQuery(name = "findRecordCount", query = "select Count(t) from CommunicationRecord t")})
public class CommunicationRecord {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_gen")
    @SequenceGenerator(name = "transaction_seq_gen", sequenceName = "transaction_sequence" , initialValue = 1, allocationSize = 1)
    private Long recordId;

    @Column(name = "OPERATION_ID" )
    @Enumerated(EnumType.ORDINAL)
    private Operation operationType;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id")
    private Sender sender;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;
    @Column(name = "operation_date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd_HH:mm:ss")
    private LocalDateTime dateTime;

    public CommunicationRecord() {
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Operation getOperationType() {
        return operationType;
    }

    public void setOperationType(Operation operationType) {
        this.operationType = operationType;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
