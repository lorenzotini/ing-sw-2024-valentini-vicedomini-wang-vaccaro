package it.polimi.ingsw.gc27.Utils;

/**
 * The IpChecker class provides utility methods for checking IP addresses.
 */
public class IpChecker {
    /**
     * check the incoming ip address
     *
     * @param ip ip address (string)
     * @return boolean indicating the validity
     */
    public static boolean checkIp(String ip) {
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        for (String s : parts) {
            int i = Integer.parseInt(s);
            if ((i < 0) || (i > 255)) {
                return false;
            }
        }
        return true;
    }
}
