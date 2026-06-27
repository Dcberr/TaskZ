package dcberr.taskz.modules.task.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TaskAssigneesTest {

    @Test
    void normalizeReturnsEmptyListForNullOrEmptyInput() {
        assertEquals(List.of(), TaskAssignees.normalize(null));
        assertEquals(List.of(), TaskAssignees.normalize(List.of()));
    }

    @Test
    void normalizeTrimsRemovesBlankValuesAndDeduplicatesCaseInsensitively() {
        assertEquals(
                List.of("Chien", "Linh"),
                TaskAssignees.normalize(List.of(
                        " Chien ",
                        "chien",
                        " ",
                        "Linh",
                        "LINH"
                ))
        );
    }
}
