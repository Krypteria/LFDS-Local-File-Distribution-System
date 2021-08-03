package Model.Observers;

public interface ServerObserver{
    void updateStatus(String newStatus, int newPort);
    void getDefaultDownloadRoute(String route);
    void getCurrentAddress(String address);
    void enableServerControlls(boolean enable);
}
