package Model.Observers;

import java.util.List;

import Model.Host;

public interface HostsObserver {
    void updateHosts(List<Host> hostList);
}
