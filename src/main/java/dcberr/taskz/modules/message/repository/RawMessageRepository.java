package dcberr.taskz.modules.message.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dcberr.taskz.modules.message.entity.RawMessage;

public interface RawMessageRepository
        extends JpaRepository<RawMessage, UUID> {
}
