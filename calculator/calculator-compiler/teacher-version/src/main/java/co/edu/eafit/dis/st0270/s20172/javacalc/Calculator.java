package co.edu.eafit.dis.st0270.s20172.javacalc;

import java.util.Hashtable;

public class Calculator {
   private int memory;
   private Hashtable<String,Integer> vars;

   public Calculator() {
      vars = new Hashtable<String,Integer>();
      memory = 0;
   }

   public int store(int value) {

      return this.memory = value;
   }

   public int recall() {

      return this.memory;
   }

   public int plus(int value) {

      return this.memory += value;
   }

   public int clear() {

      return this.memory = 0;
   }

   public int minus(int value) {

      return this.memory -= value;
   }

   public int setVar(String id, int value) {

      vars.put(id, value);
      return vars.get(id);
   }

   public int getVar(String id) {
      return vars.get(id);
   }
}
