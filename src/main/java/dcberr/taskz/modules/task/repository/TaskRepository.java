package dcberr.taskz.modules.task.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {

    List<Task> findByStatus(TaskStatus status);
}
