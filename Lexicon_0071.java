// Dominic Devasahaym CS 610 0071 prp

import java.io.*;

public class Lexicon_0071{

    private static Lexicon_0071 lexicon = null;
    private static int m;
    private static int s;
    private static int[] hashTable;
    private static char[] wordArray;

    BufferedReader bf;
    String line;

    public static Lexicon_0071 getInstance(int m){
        if(lexicon == null){
            lexicon = new Lexicon_0071(m);
        }
        return lexicon;
    }
    
    private Lexicon_0071(int tableSize){
        m = tableSize;
        s = 15*m;
        hashTable = new int[m];
        wordArray = new char[s];
        File tableText = new File("Table.txt");
        File wordText = new File("WordArray.txt");
        if(tableText.exists() && wordText.exists()){
            try{
                bf = new BufferedReader(new FileReader("Table.txt"));
                line = bf.readLine();
                while(line!=null){
                    String[] x = line.split(":");
                    hashTable[Integer.parseInt(x[0])] = Integer.parseInt(x[1]);
                    line = bf.readLine();
                }
                bf.close();

                bf = new BufferedReader(new FileReader("WordArray.txt"));
                line = bf.readLine();
                while(line!=null){
                    String[] x = line.split(":");
                    wordArray[Integer.parseInt(x[0])] = x[1].charAt(0);
                    line = bf.readLine();
                }
                bf.close();

            }catch(IOException e){
                System.out.println(e);
            }
        }else{
            initialize();
        }
        System.out.println("Lexicon initialized");
    }

    private void initialize(){
        try{
            FileWriter fw = new FileWriter("Table.txt");
            FileWriter fs = new FileWriter("WordArray.txt");
            
            for(int i = 0 ; i < m; i++){
                hashTable[i] = -1;
                fw.write(i+":"+hashTable[i]+"\n");
            }
            fw.close();

            for(int i = 0; i < s; i++){
                wordArray[i] = '*';
                fs.write(i+":"+wordArray[i]+"\n");
            }
            fs.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

// Functions as per requirements 
    public static Lexicon_0071 HashCreate(Lexicon_0071 L, int m){
        if(L == null){
            L = getInstance(m);
        }
        return L;
    }

    public static void HashEmpty(Lexicon_0071 L){
        if(L==null){
            System.out.println("Lexicon is empty");
        }else{
            System.out.println("Lexicon is not empty");
        }
    }

    public static void HashFull(Lexicon_0071 L){
        int count = 0;
        for(int i = 0; i < wordArray.length; i++){
            if(wordArray[i]=='*'){
                count++;
            }
        }
        if(count>3){
            System.out.println("Hash table has "+count+" bytes available");
        }else{
            System.out.println("Hash table is full");
        }
    }

    public static void HashPrint(Lexicon_0071 L){
        String value;
        for(int i = 0; i< m;i++){
            value = "";
            if(hashTable[i] != -1 && hashTable[i] !=-2){
                value = String.valueOf(hashTable[i]);    
            }
            System.out.println(i+" : "+value);
        }
    }
    
    public static void HashInsert(String word){
        int sum = 0;
        int hDash = 0;
        int h = 0;
        int startIndex = -1;

        for(int i = 0; i<word.length(); i++){
            int ascii = (int)word.charAt(i);
            sum = sum+ascii;
        }

        hDash = calculateHDash(word);

        for(int i = 0; i < m; i++){
            h = (hDash + i*i) % m;
            if(hashTable[h] == -1){
                break;
            }
        }

        // check if space is available for inserting in word array
        for(int i = 0; i < s; i++){
            int emptyCount = 0;
            if(wordArray[i] == '*'){
                for(int j = i+1; j <= word.length()+i; j++){
                    if(wordArray[j] == '*'){
                        emptyCount++;
                    }
                }
                if(emptyCount == word.length()){
                    startIndex = i;
                    break;
                }
            }
        }

        hashTable[h] = startIndex;
        int counter = 0;
        for(int i=startIndex; i<word.length()+startIndex; i++){
            wordArray[i] = word.charAt(counter);
            counter++;
        }
        wordArray[startIndex+word.length()] = '\0';
        writeTableToFile();
        writeWordArrayToFile();
    }

    public static void HashSearch(String word){
        int hDash = calculateHDash(word);
        if(hashTable[hDash] == -1){ // terminating if the first value encountered is -1
            System.out.println("Not found");
        }else{
            for(int i = 0 ; i < m; i++){
                int newProb = (hDash + i*i) % m;
                if(hashTable[newProb] == -2){ // if hastable points to -2, it means item was deleted
                    continue;
                }else if(hashTable[newProb] == -1){  // if hashtable points to -1, it means that item never existed
                    System.out.println("Not Found");
                }else{
                    String existingWord = "";
                    for(int j = hashTable[newProb]; j<word.length()+hashTable[newProb]; j++){
                        existingWord = existingWord+wordArray[j];
                    }
                    if(existingWord.equals(word)){
                        System.out.println("Item found at slot "+newProb);
                        break;
                    }
                }
            }
        }
    }

    public static void HashDelete(String word){
        int hDash = calculateHDash(word);
        boolean deleted = false;
        if(hashTable[hDash] == -1){
            System.out.println("Does not exist");
        }else{
            for(int i = 0; i < 1; i++){
                int newProb = (hDash + i*i) % m;
                if(hashTable[newProb] == -2){
                    continue;
                }
                String existingWord = "";
                for(int j = hashTable[newProb]; j<word.length()+hashTable[newProb]; j++){
                    existingWord = existingWord+wordArray[j];
                }
                if(existingWord.equals(word)){
                    for(int j = hashTable[newProb]; j<word.length()+hashTable[newProb]+1; j++){
                        wordArray[j]='*';
                    }   
                    hashTable[newProb] = -2;
                    deleted = true;
                    writeTableToFile();
                    writeWordArrayToFile();
                    System.out.println(word+" Deleted");
                }
            }
        }
        if(!deleted){
            System.out.println("Word "+word+" does not exist");
        }
    }

    // Calculate value of Hdash
    private static int calculateHDash(String word){
        int sum = 0;
        for(int i = 0; i<word.length(); i++){
            int ascii = (int)word.charAt(i);
            sum = sum+ascii;
        }
        return sum % m;
    }

    // Print Contents of Table
    

    // Print Contents of WordArray
    public void printWordArray(){
        for(int i =0 ; i<10; i++){
            System.out.println(i+":"+wordArray[i]);
        }
    }

    // Write new values to Table file
    private static void writeTableToFile(){
        try{
            FileWriter fw = new FileWriter("Table.txt");
            for(int i = 0; i < m; i++){
                fw.write(i+":"+hashTable[i]+"\n");
            }
            fw.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

    // Write new values to WordArray file
    private static void writeWordArrayToFile(){
        try{
            FileWriter fw = new FileWriter("WordArray.txt");
            for(int i = 0; i < s; i++){
                fw.write(i+":"+wordArray[i]+"\n");
            }
            fw.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}