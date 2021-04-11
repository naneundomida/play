package main;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BanchMarkMain {

    @Fork(value = 1, warmups = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Measurement(iterations = 1)
    @Warmup(iterations = 1)
    public void init() {
        for (int i = 0; i < 100000; i++) {
            new Integer(i);
        }
    }

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }
}
