#include "token.h"

Token::Token() : //lista de inicializacion
  type(eol), //se inicializan asi para optimizacion
  line(0),  //se inicializa entre llaves cuando se necesita hacer una operacion
  col(0)
{
  //this->type = eof; 
  //(*this).type = eof;
  //this es un apuntador a objeto
}

Token::~Token() {}

Token::Token(TokenType typ, int lineNum, int colNum) : 
  type(typ),
  line(lineNum),
  col(colNum)
{}

TokenType Token::getType() const {
  return type;
}

int Token::getLine() const {
  return line;
}

int Token::getCol() const {
  return col;
}

string Token::getLex() const { return ""; }

LexicalToken::LexicalToken(TokenType typ, string* lex, int lineNum, int colNum) :
   Token(typ,lineNum,colNum),
   lexeme(lex)
{}

LexicalToken::~LexicalToken() {
   try {
      delete lexeme;
   } catch (...) {}
}

string LexicalToken::getLex() const {
  return *lexeme;
}

