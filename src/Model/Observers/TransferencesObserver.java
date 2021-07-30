package Model.Observers;

public interface TransferencesObserver{
    void addTransference(String mode, String src_addr, String dst_addr, int Filelenght);
    void updateTransference(String mode, String addr, int progress);
    void endTransference(String mode, String addr);
}
