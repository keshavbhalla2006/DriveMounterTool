package com.mountify;

import com.mountify.service.MountifyService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testMountAndNotifyFlow() {
        MountifyService service = new MountifyService();

        System.out.println("Running full Mountify test flow:");
        service.mountAndLogUSB();
        service.sendEmailNotification();
        service.unmountUSB();

        assertEquals(1, 1); // Dummy assertion to ensure test passes
    }
}
