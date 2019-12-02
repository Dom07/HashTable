import java.io.*;
// Dominic Devasahayam CS 610 0071 prp

class Main_0071{
    public static void main(String[] args){
      Hashing_0071 hashTable = new Hashing_0071(11);
      // hashTable.hashInsert("alex");
      // hashTable.hashInsert("dom");
      // hashTable.hashInsert("mod");
      // hashTable.hashInsert("dmo");
      // hashTable.hashInsert("Donkey");

      // hashTable.printTable();
      
      System.out.println(hashTable.hashSearch("Donkey"));
      // System.out.println(hashTable.hashDelete("dom"));
    }
}

// TO DO:
// 1. Change value of wordArray if no space is available
// 2. Change value of Table if no more slots are available