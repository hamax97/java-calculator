package co.edu.eafit.dis.st0270.s20172.javacalc;

import gnu.getopt.Getopt;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import co.edu.eafit.dis.st0270.javacalc.abstree.AbsTree;
import co.edu.eafit.dis.st0270.s20172.javacalc.parser.JavaCalcParser;
import co.edu.eafit.dis.st0270.s20172.javacalc.parser.JavaCalcSymbols;
import co.edu.eafit.dis.st0270.s20172.javacalc.lexer.JavaCalcLexer;
import co.edu.eafit.dis.st0270.s20172.javacalc.visitor.EvalVisitor;
import co.edu.eafit.dis.st0270.s20172.javacalc.visitor.GetVarsVisitor;
import co.edu.eafit.dis.st0270.s20172.javacalc.visitor.TransLispVisitor;

class JavaCalcOptions {
   private boolean bScanner;
   private boolean bParser;
   private boolean bNoExec;
   private boolean bNoFile;
   private boolean bGenLisp;
   private int startFiles;

   private static String options = "[-s] [-p] [-n] [-f] [-g] <files> ...";

   private static void usage(String className) {
      System.err.println("Usage: " + className + " " + options);
      System.exit(1);
   }
   
   static JavaCalcOptions getJavaCalcOptions(String className,
                                             String args[]) {
      Getopt getOpt = new Getopt(className, args, "psnfg");
      int c;
      String str = null;
      boolean bNoFile = false, bNoExec = true, bScanner = false,
         bParser = false, bGenLisp = false;
      
      while ((c = getOpt.getopt()) != -1) {
         switch (c) { 
         case 'f':
            bNoFile = true;
            break;
            
         case 'n':
            bNoExec = false;
            break;
            
         case 'p':
            bParser = true;
            break;
            
         case 's':
            bScanner = true;
            break;

         case 'g':
            bGenLisp = true;
            break;
            
         default:
            usage(className);
            break;
         }
      }

      int startFiles = getOpt.getOptind();

      if (str == null && startFiles == args.length) usage(className);

      return new JavaCalcOptions(bScanner, bParser, bNoExec, bNoFile, bGenLisp, startFiles);
   }
   
   private JavaCalcOptions(boolean bScanner, boolean bParser,
                           boolean bNoExec, boolean bNoFile,
                           boolean bGenLisp,
                           int startFiles) {
      this.bScanner = bScanner;
      this.bParser  = bParser;
      this.bNoExec  = bNoExec;
      this.bNoFile  = bNoFile;
      this.bGenLisp = bGenLisp;
      this.startFiles = startFiles;
   }

   boolean getExec() {
      return this.bNoExec;
   }

   boolean getFile() {
      return this.bNoFile;
   }

   boolean getGenLisp() {
      return this.bGenLisp;
   }

   boolean getParser() {
      return this.bParser;
   }

   boolean getScanner() {
      return this.bScanner;
   }

   int getStartFiles() {
      return this.startFiles;
   }
}

public class JavaCalcMain {

   private static String getThisClassName() {
      return ""+JavaCalcMain.class.getName();
   }

   private static String getFilePathExt(String name, String ext) {

      int pos = name.lastIndexOf(".");
      
      if (pos > 0) {
         name = name.substring(0, pos) + ext;
      }
      
      return name;
   }

   public static void main(String args[]) {
      JavaCalcParser jcp = null;
      Calculator calc = new Calculator();
      JavaCalcOptions jco =  JavaCalcOptions.getJavaCalcOptions(getThisClassName(),
                                                                args);

      for (int i = jco.getStartFiles(); i < args.length; ++i) {

         try {

            jcp = new JavaCalcParser(new JavaCalcLexer(new FileReader(args[i]),
                                                       jco.getScanner(),
                                                       args[i]));
            // new ComplexSymbolFactory());

            Symbol symbol = jcp.parse();
            AbsTree abs = (AbsTree) symbol.value;

            if (jco.getParser()) {
               System.out.println("File: " + args[i] +
                                  " Parser: True");
            }

            if (jco.getExec()) {

               PrintWriter writer = jco.getFile() ?
                  new PrintWriter(new FileWriter(getFilePathExt(args[i], ".out")))
                  :
                  new PrintWriter(System.out);
              
               EvalVisitor eval = new EvalVisitor(calc, writer);
               abs.accept(eval);
            }

            if (jco.getGenLisp()) {
               GetVarsVisitor   gvv = new GetVarsVisitor();
               abs.accept(gvv);
               
               TransLispVisitor tlv = new TransLispVisitor(gvv.getVars());
               abs.accept(tlv);
               
               FileWriter output = new FileWriter(getFilePathExt(args[i], ".lisp"));
               output.write(tlv.getSB().toString());
               output.close();
            }
         }
         catch (Exception e) {
            
            System.err.println("File: " + args[i] +
                               "Parser: False");
         }
      }
   }
}
