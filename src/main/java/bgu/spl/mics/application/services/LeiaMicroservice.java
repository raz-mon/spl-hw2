package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.ExplotionBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
    private int queueID;
    private long delayTime;
	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
		this.attacks = attacks;
        queueID = -1;
        delayTime = 0;
    }

    /**
     * initialization for Leia:
     * register , send eventAttacks and subscribe to a Broadcast in messageBus
     */
    @Override
    protected void initialize() {
        MessageBusImpl msgBus = MessageBusImpl.getInstance();
        msgBus.register(this);
        subscribeBroadcast(ExplotionBroadcast.class, (exp) -> {terminate();});
        //Send Events
        for(int i = 0; i < attacks.length; i++){
            AttackEvent e = new AttackEvent(attacks[i]);
            sendEvent(e);
        }

    }
}
