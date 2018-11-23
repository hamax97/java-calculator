require 'ast'
require 'scanner'
require 'token'
require 'calcex'

class Parser
  def initialize(istream)
    @scan = Scanner.new(istream)
  end
   
  def parse()
    return Prog()
  end
  
  #private public protected
  def Prog()
    result = Expr()
    t = @scan.getToken()
    
    unless t.type == :eof then
      print "Expected EOF. Found ", t.type, ".\n"
      raise ParseError.new
    end
    
    return result
  end
  
  def Expr() 
    RestExpr(Term())
  end
   
  def RestExpr(e) 
    t = @scan.getToken()
    
    if t.type == :add then
         return RestExpr(AddNode.new(e,Term()))
    end
    
    if t.type == :sub then
      return RestExpr(SubNode.new(e,Term()))
    end
      
    @scan.putBackToken()
    
    e
  end
  
  def Term()
    RestTerm(Storable())
    # Write your Term() code here. This code is just temporary
    # so you can try the calculator out before finishing it.
    
    # t = @scan.getToken()
    
    # if t.type == :number then
    #   val = t.lex.to_i
    #   return NumNode.new(val)
    # end
    
    # puts "Term not implemented\n"
    
    # raise ParseError.new
  end
   
  def RestTerm(e)
    t = @scan.getToken()

    if t.type == :times then
      return RestTerm(TimesNode.new(e, Storable()))
    end
    if t.type == :divide then
      return RestTerm(DivideNode.new(e, Storable()))
    end
    @scan.putBackToken
    
    e
  end
   
  def Storable()
    fact = Factor()
    t = @scan.getToken

    if t.type == :keyword then
      if t.lex == "S" then
        return StoreNode.new(fact)
      else
        raise ParseError.new
      end
    end

    @scan.putBackToken

    fact
  end
   
  def Factor() 
    t = @scan.getToken
    
    if t.type == :number then
      return NumNode.new(t.lex.to_i)
    end
    if t.type == :keyword then
      if t.lex == "R" then
        return RecallNode.new
      else
        raise ParseError.new
      end
    end
    if t.type == :lparen then
      expr = Expr()
      t = @scan.getToken

      if t.type == :rparen then
        return expr
      else
        raise ParseError.new
      end
    end
    
    raise ParseError.new
  end         
end
