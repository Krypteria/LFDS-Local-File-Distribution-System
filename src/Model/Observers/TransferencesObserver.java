package Model.Observers;

public interface TransferencesObserver{
    void addTransference(String mode, String src_addr, String dst_addr, String fileName);
    void updateTransference(String mode, String addr, int progress);
    void endTransference(String mode, String addr);
    void notifyException(String message);
}
