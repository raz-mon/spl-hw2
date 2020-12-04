package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import java.util.concurrent.atomic.AtomicInteger;
import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.ExplotionBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
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
        subscribeBroadcast(ExplotionBroadcast.class, (exp) -> {diary.setLeiaTerminate(System.currentTimeMillis());
            terminate();});
        //Send Events
        for(int i = 0; i < attacks.length; i++){
            AttackEvent e = new AttackEvent(attacks[i]);
            ftr[i] = sendEvent(e);
        }
        int i = 0;
        //wait till all leia's attackEvent's futures are resolve
        while (i < ftr.length - 2){
            completeAttacks(i);
            i++;
        }

        DeactivationEvent deactEve = new DeactivationEvent();
        ftr[ftr.length - 2] = sendEvent(deactEve);                  // sending deactivation event to R2D2 via message bus
        completeDeactivation();                                     //wait till R2D2 finish deactivating ship's shield

        BombDestroyerEvent bombardment = new BombDestroyerEvent();
        ftr[ftr.length - 1] = sendEvent(bombardment);               //sending bombdestroyer event to Lando via message bus
        completeBombDestroyer();                                    //wait till lando will destroy the ship

        sendBroadcast(new ExplotionBroadcast());                    //the bad guys are dead and the ship exploded, send everyone a broadcast
    }

    private synchronized void completeAttacks(int i){
        while (!ftr[i].isDone()){
            try {
                ftr[i].wait();
            }catch (Exception e){
                System.out.println("Problem with leia's future: " + i);
            }
        }
    }

    private synchronized void completeDeactivation(){
        while (!ftr[ftr.length - 2].isDone()){
            try {
                ftr[ftr.length - 2].wait();
            }catch (Exception e){
                System.out.println("Problem with leia's Deactivation's future");
            }
        }
    }

    private synchronized void completeBombDestroyer() {
        while (!ftr[ftr.length - 1].isDone()) {
            try {
                ftr[ftr.length - 1].wait();
            } catch (Exception e) {
                System.out.println("Problem with leia's BombDestroyer's future");
            }
        }
    }
}
