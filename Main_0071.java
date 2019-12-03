import java.io.*;
// Dominic Devasahayam CS 610 0071 prp

class Main_0071{
    public static void main(String[] args) throws IOException{
      Lexicon_0071 L = null;
      String input;
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

      while(true){
        System.out.println("-------------------------------------------------");
        System.out.println("Input a key from the list given below:\n10 Insert\n11 Delete\n12 Search\n13 Print\n14 Create\n15 HashBatch");
        input = reader.readLine();
        System.out.println("-------------------------------------------------");
        switch(input){
          case "10":{
            System.out.println("Enter word to insert:");
            Lexicon_0071.HashInsert(reader.readLine());
            break;
          }
          case "11":{
            System.out.println("Enter word to delete:");
            Lexicon_0071.HashDelete(reader.readLine());
            break;
          }
          case "12":{
            System.out.println("Enter word to search:");
            Lexicon_0071.HashSearch(reader.readLine());
            break;
          }
          case "13":{
            System.out.println("Printing");
            Lexicon_0071.HashPrint(L);
            break;}
          case "14":{
            System.out.println("Enter size of table (m):");
            Lexicon_0071.HashCreate(L, Integer.valueOf(reader.readLine()));
            break;
          }
          case "15":{
            // Hash Batch
            break;
          }
          default: {
            System.out.println("Invalid input please try again.");
            break;
          }
        }
      }
    }
}

// TO DO:
// 1. Change value of wordArray if no space is available
// 2. Change value of Table if no more slots are available