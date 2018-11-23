#include "parser.h"
#include "calcex.h"
#include <string>
#include <sstream>

Parser::Parser(istream* in) {
   scan = new Scanner(in);
}

Parser::~Parser() {
   try {
      delete scan;
   } catch (...) {}
}

AST* Parser::parse() {
   return Lineas();
}

AST* Parser::Lineas() {
   AST* result = Linea();
   Token* t = scan->getToken();

   return Lineas();
}

AST* Parser::Linea() {
  Token* t = scan->getToken();
  if(t->getType() == keyword){
    if(t->getLex() == "let"){
      
    }
  }
  if(t->getType() == eof){
    //Code here
  }
  return RestExpr(Term());
}

AST* Parser::RestExpr(AST* e) {
   Token* t = scan->getToken();

   if (t->getType() == add) {
      return RestExpr(new AddNode(e,Term()));
   }

   if (t->getType() == sub){
      return RestExpr(new SubNode(e,Term()));
   }
   scan->putBackToken();

   return e;
}

AST* Parser::Term() {
  return RestTerm(Storable());
}

AST* Parser::RestTerm(AST* e) {
  Token* t = scan->getToken();

  if(t->getType() == times){
    return RestTerm(new TimesNode(e, Storable()));  
  }

  if(t->getType() == divide){
    return RestTerm(new DivideNode(e, Storable()));
  }

  if(t->getType() == mod){
    return RestTerm(new ModNode(e, Storable()));
  }

  scan->putBackToken();

  return e;
}

AST* Parser::Storable() {
  return MemOperation(Negative());
}

AST* Parser::MemOperation(AST* e){
  Token* t = scan->getToken();
  if(t->getType() == keyword){
    if(t->getLex() == "S"){
      return new StoreNode(e);
    }
    if(t->getLex() == "R"){
      return new RecallNode();
    }
    if(t->getLex() == "M"){
      return new MemoryNode();
    }
  }
  scan->putBackToken();
  return e;
}

AST* Parser::Negative(){
  AST* res = Factor();
  Token* t = scan->getToken();
  if(t->getType == negative){
    if(t->getLex() == "-"){
      //Code here
    }
  }
  scan->putBackToken();
  return res;
}

AST* Parser::Factor() {
  Token* t = scan->getToken();
  if(t->getType() == number){
    istringstream in(t->getLex());
    int val;
    in >> val;
    return new NumNode(val);
    // return new NumNode(stoi(t->getLex())); en c++ 11
  }
  if(t->getType() == keyword){
    if(t->getLex() == "R"){
      return new RecallNode();
    }
  }
  if(t->getType() == keyword){
    if(t->getLex() == "C"){
      return new ClearNode();
    }
  }
  if(t->getType() == indentifier){
    
  }
  if(t->getType() == lparen){
    //No retorno Expr() de una vez, porque la gramatica pide un token mas, rparen
    AST* res = Expr();
    t = scan->getToken();
    if(t->getType() == rparen){
      return res;
    }
    cerr << "Eror, expected: )" << endl;
    throw ParseError;
  }
  cerr << "Error, expected number | R | (" << endl;
  throw ParseError;
}
   
