package sg.atom.core.device;

import sg.atom.AtomMain;

/**
 *
 * @author CuongNguyen
 */
public class DeviceInfo {

    protected boolean desktopApp;

    public DeviceInfo() {
        desktopApp = !System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
    }

    public DeviceInfo(AtomMain app) {
        desktopApp = !System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik");
    }

    public boolean isDesktopApp() {
        return desktopApp;
    }
}
