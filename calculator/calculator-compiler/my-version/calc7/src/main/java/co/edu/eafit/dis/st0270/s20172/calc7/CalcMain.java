package co.edu.eafit.dis.st0270.s20172.calc7;

import co.edu.eafit.dis.st0270.s20172.calc7.CalcParser;
import co.edu.eafit.dis.st0270.s20172.calc7.CalcSymbol;
import co.edu.eafit.dis.st0270.s20172.calc7.CalcLexer;
import co.edu.eafit.dis.st0270.s20172.calc7.Evaluator;
import co.edu.eafit.dis.st0270.javacalc.abstree.AbsTree;
import gnu.getopt.Getopt;
import java_cup.runtime.Symbol;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Hello world!
 *
 */
public class CalcMain 
{
    public static void main( String[] args )
    {
	Getopt getOpt = new Getopt("CalcMain", args, "s:p:n:f:");
	int c;
	String str = null;
	boolean optionS = false;
	boolean optionF = false;
	boolean optionP = false;
	boolean optionN = false;
	int argumentsIndex = getOpt.getOptind();

	while((c = getOpt.getopt()) != -1){
	    switch (c){
	    case 's':
		optionS = true;
		getOpt.setOptind(argumentsIndex++);
		//str = getOpt.getOptarg();
		break;
	    case 'p':
		optionP = true;
		getOpt.setOptind(argumentsIndex++);
		break;
	    case 'n':
		optionN = true;
		getOpt.setOptind(argumentsIndex++);
		break;
	    case 'f':
		optionF = true;
		getOpt.setOptind(argumentsIndex++);
		break;
	    }
	}

	argumentsIndex = getOpt.getOptind();
	if(argumentsIndex == args.length){
	    System.err.println("No input files!");
	    System.exit(1);
	}

	
	CalcParser p = null;
	CalcLexer lexer = null;

	if(optionN == true){
	    for(int i = argumentsIndex; i < args.length; i++){   
		try{
		    lexer = new CalcLexer(new FileReader(args[i]));
		    p = new CalcParser(lexer);
		    
		    p.parse();
		    
		    //System.out.println("Valid file: " + args[i]);
		    if(optionS == true){
			System.out.println("File: " + args[i]);
			for(String token: lexer.tokens)
			    System.out.println(token);
		    }
		    if(optionP == true){
			System.out.println("File: " + args[i] + " Parser: True");
		    }
		    if(optionF == true){
			String fileNum = args[i].substring(0, args[i].length() - 4) + ".out";
			
			File tokensFile = new File(fileNum);
			tokensFile.createNewFile();
			tokensFile.setWritable(true, false);
			
			FileWriter fileWriter = new FileWriter(tokensFile);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			for(String token: lexer.tokens)
			    writer.write(token + "\n");
			
			writer.close();
		    }
		    
		}catch (Exception e){
		    //e.printStackTrace();
		    //System.err.println(e);
		    if(optionP == true)
			System.out.println("File: " + args[i] + " Parser: False");
		    else
			System.err.println("Invalid file: " + args[i]);
		}
	    }
	}else{
	    for(int i = argumentsIndex; i < args.length; i++){   
		try{
		    lexer = new CalcLexer(new FileReader(args[i]));
		    p = new CalcParser(lexer);
		    
		    Symbol symbol = p.parse();

		    AbsTree tree = (AbsTree) symbol.value;
		    Evaluator ev = new Evaluator();
		    tree.accept(ev);
		    
		    //System.out.println("Valid file: " + args[i]);
		    System.out.println("Result: ");
		    System.out.println(ev.getSB());
		    if(optionS == true){
			System.out.println("File: " + args[i]);
			for(String token: lexer.tokens)
			    System.out.println(token);
		    }
		    if(optionP == true){
			System.out.println("File: " + args[i] + " Parser: True");
		    }
		    if(optionF == true){
			String fileNum = args[i].substring(0, args[i].length() - 4) + ".out";
			
			File tokensFile = new File(fileNum);
			tokensFile.createNewFile();
			tokensFile.setWritable(true, false);
			
			FileWriter fileWriter = new FileWriter(tokensFile);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			
			for(String token: lexer.tokens)
			    writer.write(token + "\n");
			
			writer.close();
		    }
		    
		}catch (Exception e){
		    //e.printStackTrace();
		    //System.err.println(e);
		    if(optionP == true)
			System.out.println("File: " + args[i] + " Parser: False");
		    else
			System.err.println("Invalid file: " + args[i]);
		}
	    }
	}
    }
}
