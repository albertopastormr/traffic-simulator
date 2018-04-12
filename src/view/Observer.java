package view;

public interface Observer<T> {
    void addObserver(T o);
    void removeObserver(T o);
}
