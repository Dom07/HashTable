import java.io.*;
// Dominic Devasahayam CS 610 0071 prp

class Main_0071{
    public static void main(String[] args) throws IOException{
      Lexicon_0071 L = null;
      
      if(args.length > 0){
        String line;
        BufferedReader bf = new BufferedReader(new FileReader(args[0]));
        line = bf.readLine();
        while(line!=null){
          String[] commands = line.split(" ");
          switch(commands[0]){
            case "10":{
              L.HashInsert(commands[1]);
              break;
            }
            case "11":{
              int slot = L.HashDelete(commands[1]);
              if(slot == -1){
                System.out.println(commands[1]+" does not exist");
              }else{
                System.out.println(commands[1]+" deleted from slot "+slot);
              }
              break;
            }
            case "12":{
              int slot = L.HashSearch(commands[1]);
              if(slot == -1){
                System.out.println(commands[1]+" not found");
              }else{
                System.out.println(commands[1]+" found at slot "+slot);
              }
              break;
            }
            case "13":{
              L.HashPrint();
              break;
            }
            case "14":{
              L = Lexicon_0071.HashCreate(L, Integer.valueOf(commands[1]));
              break;
            }
            case "15":{
              L.printWordArray();
            }
          }
          line = bf.readLine();
        }
      }else{
        String input;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
          System.out.println("\n-------------------------------------------------");
          System.out.println("Input a key from the list given below and wait for further instructions:\n"+
          "10 Insert\n"+
          "11 Delete\n"+
          "12 Search\n"+
          "13 Print Hash Table\n"+
          "14 Create\n"+
          "15 LoadFactor\n"+
          "16 Print Word Array\n");
          input = reader.readLine();
          System.out.println("-------------------------------------------------");
          
          switch(input){
            case "10":{
              if(L==null){
                System.out.println("Initialize Lexicon First");
                continue;
              }
              System.out.println("Enter word to insert:");
              L.HashInsert(reader.readLine());
              break;
            }
            case "11":{
              if(L==null){
                System.out.println("Initialize Lexicon First");
                continue;
              }
              System.out.println("Enter word to delete:");
              L.HashDelete(reader.readLine());
              break;
            }
            case "12":{
              if(L==null){
                System.out.println("Initialize Lexicon First");
                continue;
              }
              System.out.println("Enter word to search:");
              int result = L.HashSearch(reader.readLine());
              if(result == -1){
                System.out.println("Item not found");
              }else{
                System.out.println("Item found at slot "+result);
              }
              break;
            }
            case "13":{
              if(L==null){
                System.out.println("Initialize Lexicon First");
                continue;
              }
              L.HashPrint();
              break;}
            case "14":{
              System.out.println("Enter size of table (m):");
              L = Lexicon_0071.HashCreate(L, Integer.valueOf(reader.readLine()));
              break;
            }
            case "15":{
              System.out.println("Load Factor: "+L.ComputeLoadFactor());
              break;
            }
            case "16":{
              L.printWordArray();
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
}