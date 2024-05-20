package it.polimi.ingsw.gc27.Controller;

public class IpChecker {
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
