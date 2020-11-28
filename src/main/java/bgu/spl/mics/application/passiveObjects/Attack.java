package bgu.spl.mics.application.passiveObjects;

import java.util.List;


/**
 * Passive data-object representing an attack object.
 * You must not alter any of the given public methods of this class.
 * <p>
 * YDo not add any additional members/method to this class (except for getters).
 */
public class Attack {
    final List<Integer> serials;
    final int duration;

    /**
     * Constructor.
     */
    public Attack(List<Integer> serialNumbers, int duration) {
        this.serials = serialNumbers;
        this.duration = duration;
    }

    /**
     * getter for Serial
     * @return List<Integer>
     */
    public List<Integer> getSerials(){
        return this.serials;
    }

    /**
     * getter for duration
     * @return
     */
    public int getDuration(){
        return this.duration;
    }

    public String toString() {
        return "serials: " + serials.toString() + ", " + "duration: " + duration;
    }
}
