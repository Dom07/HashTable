// Dominic Devasahaym CS 610 0071 prp

import java.io.*;

public class Hashing_0071{
    private int m;
    private int s;
    private int[] hashTable;
    private char[] wordArray;

    BufferedReader bf;
    String line;

    public Hashing_0071(int m){
        this.m = m;
        s = 15*m;
        this.hashTable = new int[m];
        this.wordArray = new char[s];
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
    
    public void hashInsert(String word){
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
        System.out.println(startIndex+" "+word.length());
        int counter = 0;
        for(int i=startIndex; i<word.length()+startIndex; i++){
            wordArray[i] = word.charAt(counter);
            counter++;
        }
        wordArray[startIndex+word.length()] = '\0';
        writeTableToFile();
        writeWordArrayToFile();
    }

    public String hashSearch(String word){
        int hDash = calculateHDash(word);
        if(hashTable[hDash] == -1){ // terminating if the first value encountered is -1
            return "Not found";
        }else{
            for(int i = 0 ; i < m; i++){
                int newProb = (hDash + i*i) % m;
                System.out.println("Prob Value:"+newProb);
                if(hashTable[newProb] == -2){ // if hastable points to -2, it means item was deleted
                    continue;
                }else if(hashTable[newProb] == -1){  // if hashtable points to -1, it means that item never existed
                    return "Not Found";
                }else{
                    String existingWord = "";
                    for(int j = hashTable[newProb]; j<word.length()+hashTable[newProb]; j++){
                        existingWord = existingWord+wordArray[j];
                    }
                    if(existingWord.equals(word)){
                        return "Item found at slot "+newProb;
                    }
                }
            }
        }
        return "Not found";
    }

    public String hashDelete(String word){
        int hDash = calculateHDash(word);
        if(hashTable[hDash] == -1){
            return "Does not exist";
        }else{
            for(int i = 0; i < 1; i++){
                int newProb = (hDash + i*i) % m;
                String existingWord = "";
                for(int j = hashTable[newProb]; j<word.length()+hashTable[newProb]; j++){
                    existingWord = existingWord+wordArray[j];
                }
                if(existingWord.equals(word)){
                    for(int j = hashTable[newProb]; j<word.length()+hashTable[newProb]+1; j++){
                        wordArray[j]='*';
                    }   
                    hashTable[newProb] = -2;
                    writeTableToFile();
                    writeWordArrayToFile();
                    return word+" Deleted";
                }
            }
        }
        return "Failed";
    }

    // Calculate value of Hdash
    private int calculateHDash(String word){
        int sum = 0;
        for(int i = 0; i<word.length(); i++){
            int ascii = (int)word.charAt(i);
            sum = sum+ascii;
        }
        return sum % m;
    }

    // Print Contents of Table
    public void printTable(){
        for(int i = 0; i<m;i++){
            System.out.println(i+" : "+hashTable[i]);
        }
    }

    // Print Contents of WordArray
    public void printWordArray(){
        for(int i =0 ; i<10; i++){
            System.out.println(i+":"+wordArray[i]);
        }
    }

    // Write new values to Table file
    public void writeTableToFile(){
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
    public void writeWordArrayToFile(){
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