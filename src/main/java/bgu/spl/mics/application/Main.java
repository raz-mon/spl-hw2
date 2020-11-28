package bgu.spl.mics.application;
import java.io.FileReader;
import java.util.Vector;

import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.input;
import com.google.gson.Gson;
import java.io.Reader;

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
			System.out.println(in);
		}
		catch(Exception e){
			System.out.println("problem with Gson2 man..");
		}
		
	}
}
