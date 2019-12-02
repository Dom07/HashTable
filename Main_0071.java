import java.io.*;
// Dominic Devasahayam CS 610 0071 prp

class Main_0071{
    public static void main(String[] args){
      Hashing_0071 hashTable = new Hashing_0071(11);
      hashTable.hashInsert("alex");
      hashTable.hashInsert("dom");
      hashTable.hashInsert("Donkey");
      hashTable.printTable();
      
      System.out.println(hashTable.hashSearch("alex"));
    }
}

// TO DO:
// 1. Add function to delete contents from the table
// 2. Change value of wordArray if no space is available
// 3. Change value of Table if no more slots are available