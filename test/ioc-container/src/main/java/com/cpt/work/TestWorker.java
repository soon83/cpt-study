package com.cpt.work;

public class TestWorker {

    private final TestComputer testComputer;

    public TestWorker(TestComputer testComputer) {
        this.testComputer = testComputer;
    }

    public TestComputer getTestComputer() {
        return testComputer;
    }
}
