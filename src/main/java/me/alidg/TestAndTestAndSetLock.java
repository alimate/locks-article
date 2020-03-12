package me.alidg;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class TestAndTestAndSetLock implements Lock {

    private final AtomicBoolean state = new AtomicBoolean();

    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    public void lock() {
        while (true) {
            while (state.get());
            if (!state.getAndSet(true)) return;
        }
    }

    @Override
    public void unlock() {
        state.set(false);
    }

    @Override
    public void lockInterruptibly() {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
