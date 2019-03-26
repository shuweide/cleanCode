package idv.code.concurrent;

public class ClassWithThreadingProblem {
    int nextId;

    public int takeNextId() {
        return nextId++;
    }
}
