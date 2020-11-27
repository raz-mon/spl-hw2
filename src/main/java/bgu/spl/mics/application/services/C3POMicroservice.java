package bgu.spl.mics.application.services;

import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {            // Does this class have to implement message interface? It sends messages.. Or maybe only abstract class Micro-srervice has to implement Message.
	
    public C3POMicroservice() {
        super("C3PO");
    }

    public Message awaitMessage(){
        // This is very stupid. Be aware.
        Message out = new AttackEvent();

        return out;
    }

    @Override
    protected void initialize() {

    }
}


