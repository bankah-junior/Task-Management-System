package com.amalitech.utils;

import com.amalitech.interfaces.TaskFilter;
import com.amalitech.models.Task;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class providing functional helpers using lambdas and streams.
 */
public final class FunctionalUtils {

    private FunctionalUtils() {
        // Prevent instantiation
    }

    /**
     * Filters tasks using a custom TaskFilter functional interface.
     *
     * @param tasks the list of tasks
     * @param filter the filtering logic
     * @return a list of filtered tasks
     */
    public static List<Task> filterTasks(List<Task> tasks, TaskFilter filter) {
        return tasks.stream()
                .filter(filter::test)
                .collect(Collectors.toList());
    }

}

