package projectPTM2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.NetworkChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Interpeter {
 private static String[] stringsArray;
 public static Map<String, Expression> commands= new HashMap<>();
 public static Map<String, Double> symTbl= new HashMap<>();
 public static int returnVal=0;
 private static List<String> strings;
 public static List<String> vars= new ArrayList<>();
 
public Interpeter() {
   
	 this.strings= new ArrayList<>();
	 commands.put("var", new CommandExpression(new Var()));
	 commands.put("return", new CommandExpression(new Return()));
	 commands.put("=", new CommandExpression(new Placement()));
	 commands.put("bind", new CommandExpression(new Bind()));
	 commands.put("openDataServer", new CommandExpression(new OpenServerCommand()));
	 commands.put("while", new CommandExpression(new LoopCommand()));
	 commands.put("disconnect", new CommandExpression(new Disconnect()));
	 commands.put("connect", new CommandExpression(new Connect()));
	 commands.put("print", new CommandExpression(new Print()));
	 commands.put("sleep", new CommandExpression(new Sleep()));
	 
	 vars.add("airspeed");
	 vars.add("alt");
	 vars.add("altimeter_pressure");
	 vars.add("internal_pitch");
	 vars.add("roll");
	 vars.add("pitch");
	 vars.add("internal_roll");
	 vars.add("encoder_indicated");
	 vars.add("encoder_pressure");
	 vars.add("gps_indicated-altitude");
	 vars.add("gps_indicated-ground-speed");
	 vars.add("gps_indicated-vertical-speed");
	 vars.add("heading");
	 vars.add("magnetic-compass_indicated-heading-deg");
	 vars.add("slip-skid-ball");
	 vars.add("turn-indicator");
	 vars.add("vertical-speed-indicator");
	 vars.add("aileron");
	 vars.add("elevator");
	 vars.add("rudder");
	 vars.add("flight_flaps");
	 vars.add("throttle");
     vars.add("engine_rpm");
     vars.add("viewer-x-m");
     vars.add("viewer-y-m");
     
}
 
 
 public static String[] lexer(File script) throws IOException {   //Its function to read from the script-reader makes a split returns an array of strings-that each string is a word to be interpreted
  
		BufferedReader br= new BufferedReader(new FileReader(script));
	    List<String> fromScript= new ArrayList<>();
	    String line;
	    while((line=br.readLine()) != null) {
	    	fromScript.add(line);
	    }
	    String[] test= new String[fromScript.size()];
	    fromScript.toArray(test);
	 
	 StringBuilder st= new StringBuilder();
	 
	 for(String s: test) {
		 s=s.replaceAll(" \\+ ", "+");
		 s=s.replaceAll(" \\- ", "-");
		 s=s.replaceAll(" \\* ", "*");
		 s=s.replaceAll(" / ", "/");
		 s=s.replaceAll("\\( ", "(");
		 s=s.replaceAll(" \\) ", ")");
		 st.append(s+" ");
	 }
	 Collections.addAll( strings ,st.toString().replaceAll("=", " = ").split("\\s"));
	 strings.removeIf(s->(s.equals("")));
	 
	 StringBuilder st1= new StringBuilder();
	 
	 for(String s1: strings) {
		 st1.append(s1+" ");
	 }
	 
	 stringsArray = st1.toString().split(" ");
	 return stringsArray;
	 
   }
   
   



public static void parser(String[] stringsA) {  //Passes all the strings in the array created by the mixer, giving a string a key to the corresponding command object
	
	int index = 0;
       while(index < stringsA.length){
		Expression exp = commands.get(stringsA[index]);
		if(exp != null)
		{
			index += exp.calculate(stringsA, index); // accepts as a parameter the position from which to start reading the parameters it needs 
	
		}
		else index++;
	}
 }
}