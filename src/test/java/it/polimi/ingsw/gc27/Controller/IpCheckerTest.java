package it.polimi.ingsw.gc27.Controller;

import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.gc27.Controller.IpChecker.checkIp;
import static org.junit.jupiter.api.Assertions.*;

class IpCheckerTest {

    @Test
    void checkIpTest() {
        assertTrue(checkIp("10.168.91.94"));
        assertFalse(checkIp("123"));
        assertFalse(checkIp("500"));
    }
}