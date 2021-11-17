package com.skarp.prio;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TechnicianTest {

    Technician technician;

    @Test
    public void testConstructor() {
        Technician technician = new Technician("Antonio B. II", "iPhones");
        assertNotNull(technician);
    }
}
