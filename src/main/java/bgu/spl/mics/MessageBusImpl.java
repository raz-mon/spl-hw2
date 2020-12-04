package bgu.spl.mics;
import java.util.Vector;
import java.util.Collections.*;
import java.util.HashMap;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.messages.AttackEvent;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

//	private Vector<Vector<Message>> queues;
//	private Vector<Vector<Class<? extends Message>>> interests;

	private HashMap<String, Vector<Class<? extends Message>>> interestsMap;	  // Option to use concurrent Hashmap.
	private HashMap<String, Vector<Message>> queueMap;
	private Vector<String> names;
	private static String last;		// last will hold the last M-S between Han-Solo and C3PO that was assigned an AttackEvent.
	private HashMap<Event<?>, Future<?>> EventToFuture;	// could be a problematic call, cause Future is Generic.

	private static MessageBusImpl msgBus = null;

	public static MessageBusImpl getInstance(){
		if (msgBus == null)
			msgBus = new MessageBusImpl();
		return msgBus;
		}

	private MessageBusImpl(){
//		this.queues = new Vector<Vector<Message>>(0,1);
//		this.interests = new Vector<Vector<Class<? extends Message>>>(0,1);
		this.names = new Vector<String>(0,1);
		this.interestsMap = new HashMap<>(0);
		this.queueMap = new HashMap<>(0);
		this.EventToFuture = new HashMap<>(0);
		last = null;
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
//		int ind = names.indexOf(m.getName());
//		interests.get(ind).add(type);
		this.interestsMap.get(m.getName()).add(type);
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		//		int ind = names.indexOf(m.getName());
		//		interests.get(ind).add(type);
		this.interestsMap.get(m.getName()).add(type);
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		Future f = this.EventToFuture.get(e);
		f.resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		for (String name : names){
			if (this.interestsMap.get(name).contains(b.getClass()))
				this.queueMap.get(name).add(b);		// Adds broadcast b to all relevant M-S.
		}
		notifyAll();		// Awaken all waiting methods.
	}
	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> future = new Future<T>();
		if (e.getClass().equals(AttackEvent.class)){
			// send by round robin manner
			String turn = roundRobin();
			this.queueMap.get(turn).add(e);		// Adds Message (Event in this case) e to the relevant M-S's queue (vector in our implementation)..
			this.EventToFuture.put(e, future);
		}
		else		// All Events but AttackEvent
		for (String name : names){
			if (this.interestsMap.get(name).contains(e.getClass()))
				this.queueMap.get(name).add(e);		// Adds message (Event in this case) e to the relevant M-S (only one of those if this is not and AttackEvent). [Make sure there is only one].
		}
		// Idea -> Save a vector (or concurrentHashMaps) of futures. When the relevant event is completed, the complete method will resolve the future (and notify sender?).
		notifyAll();		// Awaken all waiting methods.
		return future;
	}

	@Override
	public void register(MicroService m) {
		/*
		if (!names.contains(m.getName())){
			names.add(m.getName());
			queues.add(new Vector<Message>(0,1));
		}
 		*/
		if (!this.names.contains(m.getName())){
			this.queueMap.put(m.getName(), new Vector<Message>());
			this.interestsMap.put(m.getName(), new Vector<Class<? extends Message>>());
			this.names.add(m.getName());
		}
	}

	@Override
	public void unregister(MicroService m) {
		if (this.names.contains(m.getName())){
			this.queueMap.remove(m.getName());
			this.interestsMap.remove(m.getName());
			names.remove(m.getName());		// Check this removes correctly!!
		}
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// if there is a message, return it. If not, wait.
		while (this.queueMap.get(m.getName()).isEmpty())	// Can also be  a while (style Wait & Notify Design).
			wait();			// maybe m.wait()? wait() -> this msgBus will wait?
		return queueMap.get(m.getName()).firstElement();
		// Again, Check Vectors methods for correctness and that it indeed works here well (it should, for it is thread-safe).
	}


	private static String roundRobin(){
		// The idea is to return the correct (the one that was not assigned the last AttackEvent) name (via String) according to the round-Robin manner, between Han-Solo
		// and C3PO. this will be used at sendEvent.
		if (last == null) {
			last = "Han";
			return "Han";        // If no-one was assigned a task yet -> return Han.
		}
		else if (last == "Han"){
			last = "C3PO";
			return "C3PO";		// If the last AttackEvent was handled by Han -> return C3PO.
		}
		else{
			last = "Han";
			return "Han";		// If the last AttackEvent was handled by C3PO -> return Han.
		}
	}
}
