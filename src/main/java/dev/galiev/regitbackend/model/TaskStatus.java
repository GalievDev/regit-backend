package dev.galiev.regitbackend.model;

public enum TaskStatus {
    IN_PROCESS("in_process"),
    COMPLETED("completed"),
    PENDING("pending"),
    CANCELLED("cancelled");

    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
