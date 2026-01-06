package com.amalitech.interfaces;

import com.amalitech.models.Task;

/**
 * Functional interface used to filter tasks based on a condition.
 * <p>
 * This interface supports lambda expressions and method references.
 * </p>
 */
@FunctionalInterface
public interface TaskFilter {

    /**
     * Tests whether a given task matches a condition.
     *
     * @param task the task to test
     * @return true if the task matches the condition, false otherwise
     */
    boolean test(Task task);
}

