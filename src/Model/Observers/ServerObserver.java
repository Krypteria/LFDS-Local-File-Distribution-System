package Model.Observers;

public interface ServerObserver{
    void updateStatus(String newStatus, int newPort);
    void updateTaskInfo(String newTask);
    void getDefaultDownloadRoute(String route);
    void getCurrentAddress(String address);
}
