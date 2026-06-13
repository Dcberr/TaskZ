package dcberr.taskz.modules.task.dto;

import dcberr.taskz.common.enums.Priority;

public record CreateTaskRequest(

        String title,

        String description,

        String requester,

        Priority priority

) {
}
