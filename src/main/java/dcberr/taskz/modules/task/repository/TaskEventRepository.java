package dcberr.taskz.modules.task.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dcberr.taskz.modules.task.entity.TaskEvent;

public interface TaskEventRepository extends JpaRepository<TaskEvent, UUID>, JpaSpecificationExecutor<TaskEvent> {

    Page<TaskEvent> findByTaskId(UUID taskId, Pageable pageable);
}
