package me.alidg;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import static org.openjdk.jmh.annotations.Mode.Throughput;

@Threads(50)
@Fork(value = 2)
@Warmup(iterations = 5)
@State(Scope.Benchmark)
public class MicroBenchmark {

    private final Lock tasLock = new TestAndSetLock();
    private final Lock ttasLock = new TestAndTestAndSetLock();
    private final Lock reentrantLock = new ReentrantLock();
    private final Lock fairLock = new ReentrantLock(true);
    private Consumer<Lock> action = lock -> {
        lock.lock();
        try {
            if (System.currentTimeMillis() == 0) {
                System.out.println("Time Travel?");
            }
        } finally {
            lock.unlock();
        }
    };

    @Benchmark
    @BenchmarkMode(Throughput)
    public void fairLock() {
        action.accept(fairLock);
    }

    @Benchmark
    @BenchmarkMode(Throughput)
    public void reentrantLock() {
        action.accept(reentrantLock);
    }

    @Benchmark
    @BenchmarkMode(Throughput)
    public void tasLock() {
        action.accept(tasLock);
    }

    @Benchmark
    @BenchmarkMode(Throughput)
    public void ttasLock() {
        action.accept(ttasLock);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
