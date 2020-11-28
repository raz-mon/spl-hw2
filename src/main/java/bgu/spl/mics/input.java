package bgu.spl.mics;


import bgu.spl.mics.application.passiveObjects.Attack;

public class input {
    private Attack[] attacks;
    private long R2D2;
    private long Lando;
    private int Ewoks;

    public input() {
    }


    public String toString(){
        String out = new String();
        for(int i=0; i<attacks.length; i++){
            out = out + attacks[i].toString();
            if (i!=attacks.length-1)
                out = out + "\n";
        }
        out = out + "\n" + "R2D2: " + R2D2 + "\n" + "Lando: " + Lando + "\n" + "Ewols: " + Ewoks;
        return out;
    }

}
