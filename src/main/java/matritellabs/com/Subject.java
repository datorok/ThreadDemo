package matritellabs.com;

public interface Subject {
    public void registerObserver(Observer o);
    public void notifyAllObservers();
}
