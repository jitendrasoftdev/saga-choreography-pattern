package com.jsd.saga.commondtos.event;

import java.util.Date;
import java.util.UUID;

public interface Event {
    public UUID getEventId();
    public Date getEventDate();
}
