package bgu.spl.mics.application.passiveObjects;

import java.util.Vector;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 */
public class Ewoks {

    private Vector<Ewok> ewoks;
    private static Ewoks ewok = null;

    public static Ewoks getInstance(int num){
        if (ewok == null){
            return new Ewoks(num);
        }
        return ewok;
    }

    private Ewoks(int num){
        for (int i=0; i<num; i++){
            Ewok e = new Ewok(i);
            ewoks.add(e);
        }
    }

    public Ewok getEwok(int i){
        if(i > ewoks.size())                            //Need to check if ewoks start from zero or one
            throw new IndexOutOfBoundsException();
        return this.ewoks.get(i);
    }
}
