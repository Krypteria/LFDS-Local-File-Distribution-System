package Model.Observers;

public interface TransferenceObservable<TransferenceObserver> {
	void addTransferenceObserver(TransferenceObserver observer);
	void removeTransferenceObserver(TransferenceObserver observer);
}