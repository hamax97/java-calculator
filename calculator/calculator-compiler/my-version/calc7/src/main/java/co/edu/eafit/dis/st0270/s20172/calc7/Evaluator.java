package co.edu.eafit.dis.st0270.s20172.calc7;

import co.edu.eafit.dis.st0270.javacalc.abstree.AbsTree;
import co.edu.eafit.dis.st0270.javacalc.abstree.AddNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.AssignNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.ClearNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.EpsilonNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.DivideNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.TimesNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.MinusNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.PlusNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.NumberNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.IdentifierNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.FunctionNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.ProgNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.RecallNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.ShiftLeftNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.ShiftRightNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.StoreNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.SubNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.StmsNode;
import co.edu.eafit.dis.st0270.javacalc.visitor.Visitor;
import java.util.HashMap;

public class Evaluator implements Visitor{
    private StringBuffer sb = null;
    private int memory = 0;
    private HashMap<String, Integer> map = new HashMap<String, Integer>();

    public StringBuffer getSB(){
	return sb;
    }

    public void visit(EpsilonNode epNode){
	if(epNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer eol = new StringBuffer();
	    this.sb = eol.append("\n");
	}
    }
    
    public void visit(AddNode addNode){
	if(addNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer left = new StringBuffer();
	    StringBuffer right = new StringBuffer();

	    if(addNode.getLeft() != null){
		addNode.getLeft().accept(this); //.accept calls the visit with this same class and
		left.append(getSB());
	    }
	    if(addNode.getRight() != null){
		addNode.getRight().accept(this);
		right.append(getSB());
	    }

	    int result = Integer.parseInt(left.toString()) + Integer.parseInt(right.toString());
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(SubNode subNode){
	if(subNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer left = new StringBuffer();
	    StringBuffer right = new StringBuffer();

	    if(subNode.getLeft() != null){
		subNode.getLeft().accept(this);
		left.append(getSB());
	    }
	    if(subNode.getRight() != null){
		subNode.getRight().accept(this);
		right.append(getSB());
	    }

	    int result = Integer.parseInt(left.toString()) - Integer.parseInt(right.toString());
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(TimesNode timesNode){
	if(timesNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer left = new StringBuffer();
	    StringBuffer right = new StringBuffer();

	    if(timesNode.getLeft() != null){
		timesNode.getLeft().accept(this);
		left.append(getSB());
	    }
	    if(timesNode.getRight() != null){
		timesNode.getRight().accept(this);
		right.append(getSB());
	    }

	    int result = Integer.parseInt(left.toString()) * Integer.parseInt(right.toString());
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(DivideNode divideNode){
	if(divideNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer left = new StringBuffer();
	    StringBuffer right = new StringBuffer();

	    if(divideNode.getLeft() != null){
		divideNode.getLeft().accept(this);
		left.append(getSB());
	    }
	    if(divideNode.getRight() != null){
		divideNode.getRight().accept(this);
		right.append(getSB());
	    }

	    int result = Integer.parseInt(left.toString()) / Integer.parseInt(right.toString());
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(ShiftLeftNode slNode){
	if(slNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer left = new StringBuffer();
	    StringBuffer right = new StringBuffer();

	    if(slNode.getLeft() != null){
		slNode.getLeft().accept(this);
		left.append(getSB());
	    }
	    if(slNode.getRight() != null){
		slNode.getRight().accept(this);
		right.append(getSB());
	    }

	    int result = Integer.parseInt(left.toString()) << Integer.parseInt(right.toString());
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(ShiftRightNode srNode){
	if(srNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer left = new StringBuffer();
	    StringBuffer right = new StringBuffer();

	    if(srNode.getLeft() != null){
		srNode.getLeft().accept(this);
		left.append(getSB());
	    }
	    if(srNode.getRight() != null){
		srNode.getRight().accept(this);
		right.append(getSB());
	    }

	    int result = Integer.parseInt(left.toString()) >> Integer.parseInt(right.toString());
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(ProgNode pgNode){
	if(pgNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();

	    if(pgNode.getSub() != null){
		pgNode.getSub().accept(this);
		res.append(getSB());
	    }

	    this.sb = res;
	}
    }

    public void visit(StmsNode stmNode){
	if(stmNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();

	    if(stmNode.getLeft() != null){
		stmNode.getLeft().accept(this);
		res.append(getSB());
	    }
	    if(stmNode.getRight() != null){
		stmNode.getRight().accept(this);
		res.append(getSB());
	    }
	    
	    this.sb = res;
	}
    }

    public void visit(AssignNode eqNode){
	if(eqNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer expr = new StringBuffer();

	    if(eqNode.getSub() != null){
		eqNode.getSub().accept(this);
		expr.append(getSB());
	    }

	    String identifier = eqNode.getId();
	    int result = Integer.parseInt(expr.toString());
	    map.put(identifier, result);
	    //this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(StoreNode stNode){
	if(stNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer expr = new StringBuffer();

	    if(stNode.getSub() != null){
		stNode.getSub().accept(this);
		expr.append(getSB());
	    }
	    int result = Integer.parseInt(expr.toString());
	    memory = result;
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(RecallNode rNode){
	if(rNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    
	    int result = memory;
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(ClearNode cNode){
	if(cNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    
	    memory = 0;
	    this.sb = res.append(String.valueOf(memory));
	}
    }

    public void visit(PlusNode plusNode){
	if(plusNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer num = new StringBuffer();

	    if(plusNode.getSub() != null){
		plusNode.getSub().accept(this);
		num.append(getSB());
	    }
	    int result = Integer.parseInt(num.toString());
	    memory += result;
	    this.sb = res.append(String.valueOf(memory));
	}
    }
    
    public void visit(MinusNode minusNode){
	if(minusNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer num = new StringBuffer();

	    if(minusNode.getSub() != null){
		minusNode.getSub().accept(this);
		num.append(getSB());
	    }
	    int result = Integer.parseInt(num.toString());
	    memory -= result;
	    this.sb = res.append(String.valueOf(memory));
	}
    }

    public void visit(NumberNode numNode){
	if(numNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    
	    int result = numNode.getValue();
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(IdentifierNode idNode){
	if(idNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    
	    String identifier = idNode.getValue();
	    Integer result = map.get(identifier);
	    
	    this.sb = res.append(String.valueOf(result));
	}
    }

    public void visit(FunctionNode fNode){
	if(fNode == null){
	    sb = new StringBuffer();
	}else{
	    StringBuffer res = new StringBuffer();
	    StringBuffer expr = new StringBuffer();

	    if(fNode.getSub() != null){
		fNode.getSub().accept(this);
		expr.append(getSB());
	    }
	    
	    String identifier = fNode.getId();
	    int result = Integer.parseInt(expr.toString());
	    
	    if(identifier.equals("swap")){
		int aux = memory;
		memory = result;
		result = aux;
		this.sb = res.append(String.valueOf(result));
		
	    }else if(identifier.equals("min")){
		if(memory > result)
		    this.sb = res.append(String.valueOf(result));
		else if(result > memory)
		    this.sb = res.append(String.valueOf(memory));
		else
		    this.sb = res.append(String.valueOf(memory));
		
	    }else if(identifier.equals("max")){
		if(memory > result)
		    this.sb = res.append(String.valueOf(memory));
		else if(result > memory)
		    this.sb = res.append(String.valueOf(result));
		else
		    this.sb = res.append(String.valueOf(memory));
	    }
	}
    }
}
