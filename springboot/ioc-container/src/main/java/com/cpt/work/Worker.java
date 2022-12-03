package com.cpt.work;

public class Worker {

    private final Computer computer;

    public Worker(Computer computer) {
        this.computer = computer;
    }

    public Computer getComputer() {
        return computer;
    }
}
