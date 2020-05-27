package test;
import java.io.File;
import java.io.IOException;

import projectPTM2.*;

public class MyInterpreter {

//	public static  int interpret(String[] lines){
public static  int interpret(File f){
		// call your interpreter here
		Interpeter interpeter = new Interpeter();
		String[] lexer;
		try {
			lexer = Interpeter.lexer(f);
			Interpeter.parser(lexer);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Interpeter.returnVal;
	}
}
