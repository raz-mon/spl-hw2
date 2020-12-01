package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
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
	private Future<Boolean>[] ftr;

    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
        this.attacks = attacks;
        this.ftr = new Future[this.attacks.length + 2];           // two additional slots: 1 for DeactivationEvent and one for BombDestroyerEvent
    }

    /**
     * initialization for Leia:
     * register , send eventAttacks and subscribe to a Broadcast in messageBus
     */
    @Override
    protected void initialize() {
        subscribeBroadcast(ExplotionBroadcast.class, (exp) -> {terminate();});

        //Send Events
        for(int i = 0; i < attacks.length; i++){
            AttackEvent e = new AttackEvent(attacks[i]);
            ftr[i] = sendEvent(e);
        }


    }
}
