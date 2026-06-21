package dcberr.taskz.modules.message.entity;

import dcberr.taskz.common.entity.BaseEntity;
import dcberr.taskz.common.enums.MessageSource;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "raw_messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawMessage extends BaseEntity {

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageSource source;

    @Column(name = "channel_name")
    private String channelName;

    @Column(name = "conversation_id")
    private String conversationId;

    @Column(name = "external_message_id")
    private String externalMessageId;

    @Column(nullable = false)
    private Boolean processed;
}
