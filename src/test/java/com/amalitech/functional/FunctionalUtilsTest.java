package com.amalitech.functional;

import com.amalitech.models.Task;
import com.amalitech.utils.FunctionalUtils;
import com.amalitech.utils.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests functional utilities using Streams and lambdas.
 */
class FunctionalUtilsTest {

    @Test
    @DisplayName("Should filter tasks by status")
    void testShouldFilterTasksByStatus() {
        List<Task> tasks = List.of(
                new Task(1, "Task A", TaskStatus.TODO, 3),
                new Task(2, "Task B", TaskStatus.COMPLETED, 2),
                new Task(3, "Task C", TaskStatus.COMPLETED, 4)
        );

        List<Task> completed =
                FunctionalUtils.filterTasks(tasks,
                        t -> t.getStatus() == TaskStatus.COMPLETED);

        assertEquals(2, completed.size());
    }
}
