package bgu.spl.mics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    private MessageBusImpl msgbus;

    @BeforeEach
    void setUp() {
        msgbus = new MessageBusImpl();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void subscribeEvent() {
        // We want to subscribe the calling Microservice for the event kind specified in the method parameters..

    }

    @Test
    void subscribeBroadcast() {
    }

    @Test
    void complete() {
    }

    @Test
    void sendBroadcast() {
    }

    @Test
    void sendEvent() {
    }

    @Test
    void register() {
    }

    @Test
    void unregister() {
    }

    @Test
    void awaitMessage() {
    }
}