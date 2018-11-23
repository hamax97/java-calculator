package co.edu.eafit.dis.st0270.s20172.javacalc.lexer;

import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory;
import co.edu.eafit.dis.st0270.s20172.javacalc.parser.JavaCalcSymbols;
%%

%class JavaCalcLexer
%unicode
%line
%column
%cup
%ctorarg boolean bOutput
%ctorarg String  fileName
%public
%{
   private boolean bOutput;
   private String fileName;
   private SymbolFactory sf = new ComplexSymbolFactory();

   private Symbol symbol(int type) {
       printSymbol(type);
       return new Symbol(type, yyline, yycolumn);
   }

   private Symbol symbol(int type, Object value) {
       printSymbol(type);
       return new Symbol(type, yyline, yycolumn, value);
   }

   private Symbol symbol(int type, Object value, String name) {
       printSymbol(type);
       return sf.newSymbol(name, type, value);
   }

   private void printSymbol(int type) {
      if (bOutput) {
         System.out.println(JavaCalcSymbols.terminalNames[type]);
      }
   }
%}

%init{
   this.bOutput = bOutput;
   this.fileName = fileName;
   if (bOutput) {
      System.out.println("File: " + fileName);
   }
%init}

Identifier     = [:letter:]([:letter:]|[:digit:])*
LineTerminator = \r|\n|\r\n
WhiteSpace     = [ \t\f\v]
Number         = [1-9]([:digit:])*
IdFunction     = "swap" | "min" | "max"

%%
"+"                  { return symbol(JavaCalcSymbols.ADD); }
"-"                  { return symbol(JavaCalcSymbols.SUB); }
"/"                  { return symbol(JavaCalcSymbols.DIVIDE); }
"*"                  { return symbol(JavaCalcSymbols.TIMES); }
"<<"                 { return symbol(JavaCalcSymbols.SHIFTL); }
">>"                 { return symbol(JavaCalcSymbols.SHIFTR); }
"S"                  { return symbol(JavaCalcSymbols.STORE); }
"P"                  { return symbol(JavaCalcSymbols.PLUS); }
"M"                  { return symbol(JavaCalcSymbols.MINUS); }
"R"                  { return symbol(JavaCalcSymbols.RECALL); }
"C"                  { return symbol(JavaCalcSymbols.CLEAR); }
"("                  { return symbol(JavaCalcSymbols.LPAREN); }
")"                  { return symbol(JavaCalcSymbols.RPAREN); }
"="                  { return symbol(JavaCalcSymbols.EQUALS); }
{IdFunction}         { return symbol(JavaCalcSymbols.IDFUNC, yytext(), "IDFUNC"); } 
{Identifier}         { return symbol(JavaCalcSymbols.ID, yytext(), "ID"); }
{Number}             { return symbol(JavaCalcSymbols.NUMBER, new Integer(yytext()), "NUMBER"); }
{LineTerminator}     { return symbol(JavaCalcSymbols.EOL); }
{WhiteSpace}         { }
<<EOF>>              { return symbol(JavaCalcSymbols.EOF);   }
.                    { return symbol(JavaCalcSymbols.error); }