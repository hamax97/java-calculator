#pragma once

#include "ast.h"
#include "scanner.h"

class Parser {
 public:
   Parser(istream* in);
   ~Parser();

   AST* parse();
   
   AST* Lineas();
   AST* Linea();
   AST* RestExpr(AST* e);
   AST* Term();
   AST* RestTerm(AST* t);
   AST* Storable();
   AST* MemOperation(AST* d);
   AST* Negative();
   AST* Factor();
 private:
   Scanner* scan;
};
