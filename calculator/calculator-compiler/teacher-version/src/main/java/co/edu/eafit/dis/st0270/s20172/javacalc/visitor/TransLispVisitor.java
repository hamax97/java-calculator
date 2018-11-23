package co.edu.eafit.dis.st0270.s20172.javacalc.visitor;

import java.util.Set;
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

public class TransLispVisitor implements Visitor {

   private StringBuffer sb;
   private Set<String> vars;

   public TransLispVisitor(Set<String> vars) {

      this.sb = new StringBuffer();
      this.vars = vars;
   }

   public StringBuffer getSB() {

      return sb;
   }

   public void visit(ProgNode node) {
      StringBuffer lsb = new StringBuffer();
      lsb.append("(progn ");

      for (String s: vars) {

         lsb.append("(setf ")
            .append(s)
            .append(" 0)")
            .append('\n');
      }

      node.getSub().accept(this);
      lsb.append(getSB());
      lsb.append(')')
         .append('\n');
      this.sb = lsb;
   }

   public void visit(StmsNode node) {
      AbsTree left = node.getLeft();
      StringBuffer sbLeft = new StringBuffer();

      if (left != null) {

         left.accept(this);
         sbLeft.append(getSB());
      }

      AbsTree right = node.getRight();
      StringBuffer sbRight = new StringBuffer();

      if (right != null) {

         right.accept(this);
         sbRight
            .append("(format t \"= ~D~%\"")
            .append(' ')
            .append(getSB())
            .append(')')
            .append('\n');
      }
      this.sb = sbLeft.append(sbRight);
   }

   public void visit(AssignNode node) {
      StringBuffer lsb = new StringBuffer();
      lsb.append("(setf ").append(node.getId()).append(' ');
      node.getSub().accept(this);
      lsb.append(getSB()).append(')');
      this.sb = lsb;
   }

   public void visit(EpsilonNode node) {
      this.sb = new StringBuffer();
   }

   public void visit(ShiftLeftNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getLeft().accept(this);
      StringBuffer sbLeft = getSB();
      node.getRight().accept(this);
      StringBuffer sbRight = getSB();
      lsb.append("(ash ")
         .append(sbLeft)
         .append(' ')
         .append(sbRight)
         .append(')');
      this.sb = lsb;
   }

   public void visit(ShiftRightNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getLeft().accept(this);
      StringBuffer sbLeft = getSB();
      node.getRight().accept(this);
      StringBuffer sbRight = getSB();
      lsb.append("(ash ")
         .append(sbLeft)
         .append(' ')
         .append("(* ")
         .append(sbRight)
         .append(" -1)")
         .append(')');
      this.sb = lsb;
   }

   public void visit(AddNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getLeft().accept(this);
      StringBuffer sbLeft = getSB();
      node.getRight().accept(this);
      StringBuffer sbRight = getSB();
      lsb.append("(+ ")
         .append(sbLeft)
         .append(' ')
         .append(sbRight)
         .append(')');
      this.sb = lsb;
   }

   public void visit(SubNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getLeft().accept(this);
      StringBuffer sbLeft = getSB();
      node.getRight().accept(this);
      StringBuffer sbRight = getSB();
      lsb.append("(- ")
         .append(sbLeft)
         .append(' ')
         .append(sbRight)
         .append(')');
      this.sb = lsb;
   }

   public void visit(TimesNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getLeft().accept(this);
      StringBuffer sbLeft = getSB();
      node.getRight().accept(this);
      StringBuffer sbRight = getSB();
      lsb.append("(* ")
         .append(sbLeft)
         .append(' ')
         .append(sbRight)
         .append(')');
      this.sb = lsb;
   }

   public void visit(DivideNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getLeft().accept(this);
      StringBuffer sbLeft = getSB();
      node.getRight().accept(this);
      StringBuffer sbRight = getSB();
      lsb.append("(/ ")
         .append(sbLeft)
         .append(' ')
         .append(sbRight)
         .append(')');
      this.sb = lsb;
   }

   public void visit(StoreNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getSub().accept(this);
      lsb.append("(setf *memory* ");
      lsb.append(getSB()).append(')');
      this.sb = lsb;
   }

   public void visit(PlusNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getSub().accept(this);
      lsb.append("(setf *memory* (+ *memory* ");
      lsb.append(getSB()).append("))");
      this.sb = lsb;
   }

   public void visit(MinusNode node) {
      StringBuffer lsb = new StringBuffer();
      node.getSub().accept(this);
      lsb.append("(setf *memory* (- *memory* ");
      lsb.append(getSB()).append("))");
      this.sb = lsb;
   }

   public void visit(NumberNode node) {
      StringBuffer lsb = new StringBuffer();
      lsb.append(node.getValue());
      this.sb = lsb;
   }

   public void visit(IdentifierNode node) {
      StringBuffer lsb = new StringBuffer();
      lsb.append(node.getValue());
      this.sb = lsb;
   }

   public void visit(FunctionNode node) {
      node.getSub().accept(this);
      StringBuffer lsb = new StringBuffer();

      switch(getFuncId(node.getId())) {
      case SWAP:
         lsb.append("0");
         break;

      case MAX:
         lsb.append("(max *memory* ")
            .append(getSB())
            .append(')');
         break;

      case MIN:
         lsb.append("(min *memory* ")
            .append(getSB())
            .append(')');
         break;
      }

      this.sb = lsb;
   }

   public void visit(RecallNode node) {
      StringBuffer lsb = new StringBuffer();
      lsb.append("*memory*");
      this.sb = lsb;
   }

   public void visit(ClearNode node) {
      StringBuffer lsb = new StringBuffer();
      lsb.append("(setf *memory* 0)");
      this.sb = lsb;
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
