package com.example.flink.mInterface;

import java.util.Date;

public interface ClockEvent {
    void tick(Date date);
}
