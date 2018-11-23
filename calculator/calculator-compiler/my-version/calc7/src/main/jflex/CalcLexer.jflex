package co.edu.eafit.dis.st0270.s20172.calc7;

import java.util.ArrayList;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;
import java_cup.runtime.ComplexSymbolFactory;
import co.edu.eafit.dis.st0270.s20172.calc7.CalcSymbol;

%%

%class CalcLexer
%unicode
%cupsym CalcSymbol
%line
%column
%cup

%{
public ArrayList<String> tokens = new ArrayList<String>();

private ComplexSymbolFactory sf = new ComplexSymbolFactory();

public int getLine() {
   return yyline;
}
public int getColumn() {
   return yycolumn;
}

public void addToken(String tokenName){
   tokens.add(tokenName);
}
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = [ \t\f]

Identifier = [:jletter:][:jletterdigit:]*
Number = 0 | [1-9][0-9]*

%%


"+"		{addToken("ADD");
		return new Symbol(CalcSymbol.ADD);}
"-"		{addToken("SUB");
		return new Symbol(CalcSymbol.SUB);}
"*"		{addToken("TIMES");
		return new Symbol(CalcSymbol.TIMES);}
"/"		{addToken("DIVIDE");
		return new Symbol(CalcSymbol.DIVIDE);}
"("		{addToken("LPAREN");
		return new Symbol(CalcSymbol.LPAREN);}
")"		{addToken("RPAREN");
		return new Symbol(CalcSymbol.RPAREN);}
"<<"		{addToken("SHIFTL");
		return new Symbol(CalcSymbol.LESST);}
">>"		{addToken("SHIFTR");
		return new Symbol(CalcSymbol.GREATERT);}
"S"		{addToken("STORE");
		return new Symbol(CalcSymbol.S);}
"P"		{addToken("PLUS");
		return new Symbol(CalcSymbol.P);}
"M"		{addToken("MINUS");
		return new Symbol(CalcSymbol.M);}
"R"		{addToken("RECALL");
		return new Symbol(CalcSymbol.R);}
"C"		{addToken("CLEAR");
		return new Symbol(CalcSymbol.C);}
"="		{addToken("ASSIGN");
		return new Symbol(CalcSymbol.EQUALS);}

{Number}	{addToken("NUMBER");
		return sf.newSymbol("NUMBER", CalcSymbol.NUMBER, new Integer(yytext()));}
"swap"		{addToken("SWAP");
		return sf.newSymbol("IDENTIFIER", CalcSymbol.IDENTIFIER, yytext());}
"max"		{addToken("MAX");
		return sf.newSymbol("IDENTIFIER", CalcSymbol.IDENTIFIER, yytext());}
"min"		{addToken("MIN");
		return sf.newSymbol("IDENTIFIER", CalcSymbol.IDENTIFIER, yytext());}
{Identifier}	{addToken("IDENTIFIER");
		return sf.newSymbol("IDENTIFIER", CalcSymbol.IDENTIFIER, yytext());}
{WhiteSpace}	{ }
{LineTerminator}    {return new Symbol(CalcSymbol.EOL, yytext());}


<<EOF>>		{return new Symbol(CalcSymbol.EOF);}
.		{throw new Error("Illegal character <" + yytext() + "> at line: " + (yyline + 1) + " column: " + yycolumn);}


