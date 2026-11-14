package jlox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
	static boolean hadError = false;
	
	public static void main(String[] args) throws IOException {
		if (args.length > 1) {
		      System.out.println("Usage: jlox [script]");
		      System.exit(64); 
	    } else if (args.length == 1) { // run when path file given
	    	runFile(args[0]);
	    } else { // activate REPL
	    	runPrompt();
	    }
	}
	
	// gives a path to a file, read it and execute it
	private static void runFile(String path) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		run(new String(bytes, Charset.defaultCharset()));
		
		if(hadError) System.exit(65);
	}
	
	// allow REPL commands
	private static void runPrompt() throws IOException {
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(input);
		
		while(true) {
			System.out.println("> ");
			String line = reader.readLine();
		    if (line == null) break;
		    run(line);
		    hadError = false;
		}
	}
	
	// core run function
	private static void run(String source) {
		Scanner scanner = new Scanner(source);
		List<Token> tokens = scanner.scanTokens();
		
		for(Token token: tokens) {
			System.out.println(token);
		}
	}
	
	static void error(int line, String message) {
		report(line, "", message);
	}
	
	// COME BACK TO MAKE MORE INTERESTING/USEFUL ERROR MESSAGES IN FUTURE
	// listing error reports
	private static void report(int line, String where, String message) {
		System.err.println("[line " + line + "] Error" + where + ": " + message);
		hadError = true;
	}
	
	
}
