package server;

public enum Endpoint {
    GET_ALL_TASKS,
    GET_TASK,
    POST_TASK,
    DELETE_ALL_TASKS,
    DELETE_TASK_BY_ID,
    GET_SUBTASKS_OF_EPIC,

    WRONG_ENDPOINT;
}
