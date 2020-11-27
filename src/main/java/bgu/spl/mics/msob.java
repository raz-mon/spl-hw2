package bgu.spl.mics;
import java.util.Queue;
import java.util.Set;


public class msob {
    private MicroService ms;
    private Queue<Message> queue;             //T is a Class object
    private Set<Class> broadcastInterests;
    private Set<Class> EventInterests;

    public msob(MicroService ms){
        this.ms = ms;


    }


}


