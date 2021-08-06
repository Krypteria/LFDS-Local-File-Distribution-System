package Model.Observers;

import java.util.List;

import Model.Host.Host;

public interface HostsObserver {
    void updateAllHosts(List<Host> hostList);
}
