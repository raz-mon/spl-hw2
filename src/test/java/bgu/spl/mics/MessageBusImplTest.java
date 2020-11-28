package bgu.spl.mics;


// tetetetetete
import bgu.spl.mics.application.messages.BroadcastImpl;

import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.services.C3POMicroservice;
import bgu.spl.mics.application.services.LeiaMicroservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bgu.spl.mics.application.messages.ExplotionBroadcast;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.services.HanSoloMicroservice;


class MessageBusImplTest{

    private MessageBusImpl msgbus;

    @BeforeEach
    void setUp() {
        msgbus = MessageBusImpl.getInstance();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void subscribeEvent() {
        MicroService m1 = new HanSoloMicroservice();
        AttackEvent attack = new AttackEvent();
        msgbus.register(m1);
        m1.subscribeEvent(attack.getClass(), (atk) -> {});
        msgbus.sendEvent(attack);
        AttackEvent a1 = (AttackEvent) ((HanSoloMicroservice)m1).awaitMessage();
        assertEquals(attack, a1);
    }

    @Test
    void subscribeBroadcast() {
        MicroService m1 = new HanSoloMicroservice();
        MicroService m2 = new C3POMicroservice();
        // Maybe add another micro-service that isn't subscribed and make sure that he doesn't get the  message. (to avoid endless loop, it can be subscribed to another croadcast and we can make sure it gets that one and not the rather.
        msgbus.register(m1);
        msgbus.register(m2);
        m1.subscribeBroadcast(ExplotionBroadcast.class, (br) -> {});
        m2.subscribeBroadcast(ExplotionBroadcast.class, (br) -> {});
        Broadcast brd = new ExplotionBroadcast();
        msgbus.sendBroadcast(brd);
        ExplotionBroadcast exp1 = (ExplotionBroadcast) ((HanSoloMicroservice)m1).awaitMessage();
        ExplotionBroadcast exp2 = (ExplotionBroadcast) ((C3POMicroservice)m1).awaitMessage();
        assertEquals(exp1, brd);
        assertEquals(exp2, brd);
    }

    @Test
    void complete() {
        Attack[] a = new Attack[0];
        MicroService m = new LeiaMicroservice(a);
        AttackEvent attack = new AttackEvent();
        Future<Boolean> ftr = m.sendEvent(attack);
        msgbus.complete(attack,true);
        assertTrue(ftr.get());

    }

    @Test
    void sendBroadcast() {
        MicroService m1 = new HanSoloMicroservice();
        MicroService m2 = new C3POMicroservice();
        // Maybe add another micro-service that isn't subscribed and make sure that he doesn't get the  message. (to avoid endless loop, it can be subscribed to another croadcast and we can make sure it gets that one and not the rather.
        msgbus.register(m1);
        msgbus.register(m2);
        m1.subscribeBroadcast(ExplotionBroadcast.class, (br)->{});
        m2.subscribeBroadcast(ExplotionBroadcast.class, (br) -> {});
        Broadcast brd = new ExplotionBroadcast();
        msgbus.sendBroadcast(brd);
        ExplotionBroadcast exp1 = (ExplotionBroadcast) ((HanSoloMicroservice)m1).awaitMessage();
        ExplotionBroadcast exp2 = (ExplotionBroadcast) ((C3POMicroservice)m1).awaitMessage();
        assertEquals(exp1, brd);
        assertEquals(exp2, brd);
    }

    @Test
    void sendEvent() {
        MicroService m1 = new HanSoloMicroservice();
        MicroService m2 = new HanSoloMicroservice();
        AttackEvent attack = new AttackEvent();
        msgbus.register(m1);
        msgbus.register(m2);
        m1.subscribeEvent(attack.getClass(), (atk) -> {});
        m2.sendEvent(attack);
        AttackEvent a1 = (AttackEvent) ((HanSoloMicroservice)m1).awaitMessage();
        assertEquals(attack, a1);
    }

    @Test
    void register() {
        MicroService m1 = new HanSoloMicroservice();
        //m1.register();


    }

    @Test       //We were told not to implement this.
    void unregister() {
    }

    @Test
    void awaitMessage() throws InterruptedException {
        MicroService m1 = new HanSoloMicroservice();
        MicroService m2 = new HanSoloMicroservice();
        //Notice there is no test for exception due to unregister microservice
        msgbus.register(m1);
        AttackEvent attack = new AttackEvent();
        m1.subscribeEvent(attack.getClass(), (atk) -> {});
        try {
            AttackEvent message2 = (AttackEvent)msgbus.awaitMessage(m2);
        }
        catch(Exception e) {
            //  Good, m2 did not register
        }
        AttackEvent message1 = (AttackEvent)msgbus.awaitMessage(m1);
        assertEquals( message1 , attack);
    }
}
