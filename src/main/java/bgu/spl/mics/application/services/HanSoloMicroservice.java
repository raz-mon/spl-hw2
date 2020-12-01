package bgu.spl.mics.application.services;


import bgu.spl.mics.Message;
import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.ExplotionBroadcast;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;

import java.util.Iterator;
import java.util.List;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {

    public HanSoloMicroservice() {
        super("Han");
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(ExplotionBroadcast.class, (exp) -> {terminate(); });
        subscribeEvent(AttackEvent.class,(atk) -> {
            List<Integer> serials = atk.getAttack().getSerials();       //Our Ewoks serials in order we could attack
            Ewoks ewks = Ewoks.getInstance(serials.size());             //Global Ewoks list
            for (Integer serial : serials) {
                Ewok e = ewks.getEwok(serial);
                while (!e.isAvailable()) {
                    try {
                        e.wait();         // Remember to notify when the Ewok is released.
                    } catch (InterruptedException t) {
                        System.out.println("wait interrupted");
                    }     // We need to wait here (this Thread) until the relevant Ewok is released.
                }
                e.acquire();
            }
            try{
                Thread.sleep(atk.getAttack().getDuration());
            }
            catch (InterruptedException e){
                System.out.println("Sleep had fail");
            }

            for (Integer serial : serials) {
                Ewok e = ewks.getEwok(serial);
                e.release();
            }

            complete(atk, true);
        });
    }
}
