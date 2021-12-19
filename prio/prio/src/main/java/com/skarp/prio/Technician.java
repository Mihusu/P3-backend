package com.skarp.prio;
// Todo: remove this class as it is unused
public class Technician {
    private final String name;
    private String specialization;

    public Technician(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    public String getName() {
        return this.name;
    }
}
