package co.edu.eafit.dis.st0270.s20172.javacalc.visitor;

import java.io.PrintWriter;
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

public class EvalVisitor implements Visitor {

   private int value;
   private Calculator calc;
   private PrintWriter writer;

   public EvalVisitor(Calculator calc, PrintWriter writer) {

      this.value = 0;
      this.calc = calc;
      this.writer = writer;
   }

   public int getValue() {

      return value;
   }

   public void visit(ProgNode node) {

      node.getSub().accept(this);
      writer.close();
   }

   public void visit(StmsNode node) {
      AbsTree left = node.getLeft();

      if (left != null) {
         left.accept(this);
      }

      AbsTree right = node.getRight();

      if (right != null) {
         right.accept(this);
         int rValue = getValue();
         writer.println("= " + rValue);
      }
   }

   public void visit(AssignNode node) {
      node.getSub().accept(this);
      int sValue = getValue();
      this.value = calc.setVar(node.getId(), sValue);
   }

   public void visit(EpsilonNode node) {
      this.value = 0;
   }

   public void visit(ShiftLeftNode node) {
      node.getLeft().accept(this);
      int lValue = getValue();
      node.getRight().accept(this);
      int rValue = getValue();
      this.value = lValue << rValue;
   }

   public void visit(ShiftRightNode node) {
      node.getLeft().accept(this);
      int lValue = getValue();
      node.getRight().accept(this);
      int rValue = getValue();
      this.value = lValue >> rValue;
   }

   public void visit(AddNode node) {
      node.getLeft().accept(this);
      int lValue = getValue();
      node.getRight().accept(this);
      int rValue = getValue();
      this.value = lValue + rValue;
   }

   public void visit(SubNode node) {
      node.getLeft().accept(this);
      int lValue = getValue();
      node.getRight().accept(this);
      int rValue = getValue();
      this.value = lValue - rValue;
   }

   public void visit(TimesNode node) {
      node.getLeft().accept(this);
      int lValue = getValue();
      node.getRight().accept(this);
      int rValue = getValue();
      this.value = lValue * rValue;
   }

   public void visit(DivideNode node) {
      node.getLeft().accept(this);
      int lValue = getValue();
      node.getRight().accept(this);
      int rValue = getValue();
      this.value = lValue / rValue;
   }

   public void visit(StoreNode node) {
      node.getSub().accept(this);
      this.value = calc.store(getValue());
   }

   public void visit(PlusNode node) {
      node.getSub().accept(this);
      this.value = calc.plus(getValue());
   }

   public void visit(MinusNode node) {
      node.getSub().accept(this);
      this.value = calc.minus(getValue());
   }

   public void visit(NumberNode node) {
      this.value = node.getValue();
   }

   public void visit(IdentifierNode node) {
      this.value = calc.getVar(node.getValue());
   }

   public void visit(FunctionNode node) {

      node.getSub().accept(this);
      int sValue = getValue();
      int memory = calc.recall();
      switch(getFuncId(node.getId())) {
      case SWAP:
         calc.store(sValue);
         this.value = memory;
         break;

      case MAX:

         this.value = Math.max(memory, sValue);
         break;

      case MIN:

         this.value = Math.min(memory, sValue);
         break;
      }
   }

   public void visit(RecallNode node) {

      this.value = calc.recall();
   }

   public void visit(ClearNode node) {

      this.value = calc.clear();
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
