package dcberr.taskz.modules.message.entity;

import dcberr.taskz.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(nullable = false)
    private Boolean processed;
}
