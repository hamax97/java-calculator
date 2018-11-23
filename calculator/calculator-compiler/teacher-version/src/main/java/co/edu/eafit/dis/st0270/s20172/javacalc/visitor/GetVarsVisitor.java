package co.edu.eafit.dis.st0270.s20172.javacalc.visitor;

import java.util.Set;
import java.util.HashSet;
import co.edu.eafit.dis.st0270.s20172.javacalc.Calculator;
import co.edu.eafit.dis.st0270.javacalc.visitor.Visitor;
import co.edu.eafit.dis.st0270.javacalc.abstree.AbsTree;
import co.edu.eafit.dis.st0270.javacalc.abstree.AssignNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.EpsilonNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.ShiftLeftNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.ShiftRightNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.AddNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.SubNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.TimesNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.DivideNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.StoreNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.PlusNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.ProgNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.MinusNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.NumberNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.IdentifierNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.FunctionNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.RecallNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.ClearNode;
import co.edu.eafit.dis.st0270.javacalc.abstree.StmsNode;

public class GetVarsVisitor implements Visitor {

   private Set<String> vars;
   private Calculator calc;

   public GetVarsVisitor() {

      vars = new HashSet<String>();
   }

   public Set<String> getVars() {

      return vars;
   }

   public void visit(ProgNode node) {

      node.getSub().accept(this);
      this.vars.add("*memory*");
   }

   public void visit(StmsNode node) {
      AbsTree left = node.getLeft();
      Set<String> lvs = new HashSet<String>();

      if (left != null) {
         left.accept(this);
         lvs.addAll(getVars());
      }

      AbsTree right = node.getRight();
      Set<String> rvs = new HashSet<String>();

      if (right != null) {
         right.accept(this);
         rvs.addAll(getVars());
      }

      rvs.addAll(lvs);
      
      this.vars = rvs;
   }

   public void visit(AssignNode node) {
      Set<String> avs = new HashSet<String>();
      avs.add(node.getId());

      avs.addAll(getVars());
      this.vars = avs;
   }

   public void visit(EpsilonNode node) {

      this.vars = new HashSet<String>();
   }

   public void visit(ShiftLeftNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getLeft().accept(this);
      lvs.addAll(getVars());

      node.getRight().accept(this);
      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(ShiftRightNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getLeft().accept(this);
      lvs.addAll(getVars());

      node.getRight().accept(this);
      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(AddNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getLeft().accept(this);
      lvs.addAll(getVars());

      node.getRight().accept(this);
      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(SubNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getLeft().accept(this);
      lvs.addAll(getVars());

      node.getRight().accept(this);
      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(TimesNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getLeft().accept(this);
      lvs.addAll(getVars());

      node.getRight().accept(this);
      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(DivideNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getLeft().accept(this);
      lvs.addAll(getVars());

      node.getRight().accept(this);
      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(StoreNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getSub().accept(this);

      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(PlusNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getSub().accept(this);

      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(MinusNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getSub().accept(this);

      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(NumberNode node) {

      this.vars = new HashSet<String>();
   }

   public void visit(IdentifierNode node) {

      this.vars = new HashSet<String>();
      this.vars.add(node.getValue());
   }

   public void visit(FunctionNode node) {
      Set<String> lvs = new HashSet<String>();

      node.getSub().accept(this);

      lvs.addAll(getVars());

      this.vars = lvs;
   }

   public void visit(RecallNode node) {

      this.vars = new HashSet<String>();
   }

   public void visit(ClearNode node) {

      this.vars = new HashSet<String>();
   }

   private static enum FuncId { SWAP, MAX,
                                MIN, UNDEFINED };

   private static FuncId getFuncId(String name) {
      if (name.equals("swap")) return FuncId.SWAP;
      if (name.equals("max")) return FuncId.MAX;
      if (name.equals("min")) return FuncId.MIN;

      return FuncId.UNDEFINED;
   }
}
