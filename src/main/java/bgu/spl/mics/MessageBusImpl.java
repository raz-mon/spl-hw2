package bgu.spl.mics;
import java.util.Vector;
import java.util.Collections.*;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private Vector<String> names;
	private Vector<Vector<Message>> queues;
	private Vector<Vector<Class<? extends Event>>> interests;

	private static MessageBusImpl msgBus = null;
	// Add a field that is an array of queues? something like that. Look at the collections they gave us. Arraylists and so.
	// They are probably the way to go.
	public static MessageBusImpl getInstance(){
		if (msgBus == null)
			msgBus = new MessageBusImpl();
		return msgBus;
		}

	private MessageBusImpl(){
		this.queues = new Vector<Vector<Message>>(0,1);
		this.interests = new Vector<Vector<Class<? extends Event>>>(0,1);
	}
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		
        return null;
	}

	@Override
	public void register(MicroService m) {
		
	}

	@Override
	public void unregister(MicroService m) {
		
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		
		return null;
	}
}
