package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;
    public Ewok(int serialNumber){        // We added. Available should be true at initialization.
      available = true;
        this.serialNumber = serialNumber;
    }
    /**
     * Acquires an Ewok
     */
    public void acquire() {
		
    }

    /**
     * release an Ewok
     */
    public void release() {
    	
    }








   // ------------------------------ from here on - this is only for our internal testing.

    /**
     * This function is for our use only for testing!!
     * @return
     */
    public boolean isAvailable(){
        return available;
    }
}

