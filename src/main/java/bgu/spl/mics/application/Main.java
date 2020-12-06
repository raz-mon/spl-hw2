package bgu.spl.mics.application;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Watchable;
import java.util.List;
import java.util.Vector;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import bgu.spl.mics.application.services.*;
import bgu.spl.mics.input;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;

import java.util.ArrayList;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	public static void main(String[] args) {
		Gson gson = new Gson();
		try {
			Reader reader = new FileReader(args[0]);
			input in = gson.fromJson(reader, input.class);
			Diary diary = Diary.getInstance();
			simulate(in);
			outputConfig(diary, args[1]);
		}
		catch(Exception e){
			System.out.println("problem accured");
		}
	}

	public static void simulate(input in){
		LeiaMicroservice leia = new LeiaMicroservice(in.getAttacks());
		HanSoloMicroservice han = new HanSoloMicroservice();
		C3POMicroservice cp3o = new C3POMicroservice();
		R2D2Microservice r2d2 = new R2D2Microservice(in.getR2D2());
		LandoMicroservice lando = new LandoMicroservice(in.getLando());

		Ewoks ewks = Ewoks.getInstance(in.getEwoks());		// Ewoks is single-tone -> only one instance (this one) will be used through-out the program.
		MessageBusImpl msgbus = MessageBusImpl.getInstance();		// Makes sense to initialize msgBus here to.


		Thread t1 = new Thread(leia);
		Thread t2 = new Thread(han);
		Thread t3 = new Thread(cp3o);
		Thread t4 = new Thread(r2d2);
		Thread t5 = new Thread(lando);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

		try{
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
		}catch(Exception e) {System.out.println("problem accured with joining threads");}		// In order to get a right answer at outputConfig.
	}

	public static void outputConfig(Diary diary, String outPath){
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		try{
			FileWriter writer = new FileWriter(outPath);
			g.toJson(diary,writer);
			writer.flush();
			writer.close();
		} catch(Exception e) {System.out.println("problem with generating output file");}
	}

}

