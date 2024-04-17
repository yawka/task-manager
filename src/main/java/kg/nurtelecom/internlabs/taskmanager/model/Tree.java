    package kg.nurtelecom.internlabs.taskmanager.model;

    public interface Tree<T extends Tree<T>> {
        T getParent();
    }
