package Model.Observers;

public interface Observable<T> {
	void addObserver(T observer);
}
