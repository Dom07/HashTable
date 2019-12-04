// Dominic Devasahaym CS 610 0071 prp

public class Lexicon_0071{

    private static Lexicon_0071 lexicon = null;
    private  int m;
    private  int s;
    private  float loadFactor;
    private  int[] hashTable;
    private  char[] wordArray;

    String line;

    public static Lexicon_0071 getInstance(int m){
        if(lexicon == null){
            lexicon = new Lexicon_0071(m);
        }
        return lexicon;
    }
    
    private Lexicon_0071(int tableSize){
        m = tableSize;
        // s = 15*m;
        s = m;
        hashTable = new int[m];
        wordArray = new char[s];
        initialize();
        System.out.println("Lexicon initialized");
    }

    private void initialize(){
            for(int i = 0 ; i < m; i++){
                hashTable[i] = -1;
            }
            
            for(int i = 0; i < s; i++){
                wordArray[i] = '*';
            }
    }

// Functions as per requirements 
    public static Lexicon_0071 HashCreate(Lexicon_0071 L, int m){
        if(L == null){
            L = getInstance(m);
        }
        return L;
    }

    public void HashEmpty(Lexicon_0071 L){
        if(L==null){
            System.out.println("Lexicon is empty");
        }else{
            System.out.println("Lexicon is not empty");
        }
    }

    public void HashFull(){
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

    public float ComputeLoadFactor(){
        int counter = 0;
        for(int i = 0; i < m; i++){
            if(hashTable[i] != -1 && hashTable[i] !=-2){
                counter++;
            }
        }
        loadFactor = counter/(float)m;
        return loadFactor;
    }

    public void HashPrint(){
        String value;
        for(int i = 0; i< m;i++){
            value = "";
            if(hashTable[i] != -1 && hashTable[i] !=-2){
                value = String.valueOf(hashTable[i]);    
            }
            System.out.println(i+" : "+value);
        }
    }
    
    public void HashInsert(String word){
        int sum = 0;
        int hDash = 0;
        int h = 0;
        int startIndex = -1;
        int slot = HashSearch(word);
        if(slot == -1){
            for(int i = 0; i<word.length(); i++){
                int ascii = (int)word.charAt(i);
                sum = sum+ascii;
            }

            hDash = calculateHDash(word);

            for(int i = 0; i < m; i++){
                h = (hDash + i*i) % m;
                if(hashTable[h] == -1){
                    break;
                }else{
                    h=-1;
                }
            }
            
            // check if space is available for inserting in word array
            for(int i = 0; i < s; i++){
                int emptyCount = 0;
                if(wordArray[i] == '*'){
                    for(int j = i+1; j <= word.length()+i; j++){
                        if(j<s && wordArray[j] == '*'){
                            emptyCount++;
                        }
                    }
                    if(emptyCount == word.length()){
                        startIndex = i;
                        break;
                    }
                }
            }

            if(startIndex == -1){
                increaseWordArraySize();
                HashInsert(word);
            }else if(h == -1){
                System.out.println("Value of h:"+h);
                increaseHashTableSize();
                HashInsert(word);
            }else{
                hashTable[h] = startIndex;
                int counter = 0;
                for(int i=startIndex; i<word.length()+startIndex; i++){
                    wordArray[i] = word.charAt(counter);
                    counter++;
                }
                wordArray[startIndex+word.length()] = '\0';
                if(ComputeLoadFactor()>0.75){
                    increaseHashTableSize();
                }
            }
        }else{
            System.out.println(word+" already exists at slot "+slot);
        }
    }

    public int HashSearch(String word){
        int hDash = calculateHDash(word);
        if(hashTable[hDash] == -1){ // terminating if the first value encountered is -1
            return -1;
        }else{
            for(int i = 0 ; i < m; i++){
                int newProb = (hDash + i*i) % m;
                if(hashTable[newProb] == -2){ // if hastable points to -2, it means item was deleted
                    continue;
                }else if(hashTable[newProb] == -1){  // if hashtable points to -1, it means that item never existed
                    return -1;
                }else{
                    String existingWord = "";
                    for(int j = hashTable[newProb]; j<word.length()+hashTable[newProb]; j++){
                        existingWord = existingWord+wordArray[j];
                    }
                    if(existingWord.equals(word)){
                        return newProb;
                    }
                }
            }
        }
        return -1;
    }

    public int HashDelete(String word){
        int hDash = calculateHDash(word);
        if(hashTable[hDash] == -1){
            return -1;
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
                    ComputeLoadFactor();
                    return newProb;
                }
            }
        }
        return -1;
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

    // Print Contents of WordArray
    public void printWordArray(){
        for(int i =0 ; i<s; i++){
            if(wordArray[i] == '\0'){
                System.out.print("\\");
            }else{
                System.out.print(wordArray[i]);
            }
        }
    }

    private void increaseWordArraySize(){
        char[] tempArray = new char[s];
        for(int i = 0; i < s; i++){
            tempArray[i] = wordArray[i];
        }
        s = 2*s;
        wordArray = new char[s];
        for(int i = 0; i<s; i++){
            if(i<s/2){
                wordArray[i] = tempArray[i];
            }else{
                wordArray[i] = '*';
            }
        }   
    }

    private void increaseHashTableSize(){
        int[] tempTable = new int[m];
        char[] tempArray = new char[s];

        for(int i = 0; i<m;i++){
            tempTable[i] = hashTable[i];
        }

        for(int i = 0; i<s;i++){
            tempArray[i] = wordArray[i];
            wordArray[i] = '*';
        }
        System.out.println("Previous Size of m:"+m);
        m = 2*m;
        while(true){
            if(checkPrime(m)){
                break;
            }else{
                m++;
            }
        }

        System.out.println("New Size of m:"+m);

        hashTable = new int[m];

        for(int i = 0; i<m;i++){
            hashTable[i] = -1;
        }

        String generatedWord = "";

        for(int i = 0; i<s; i++){
            if(tempArray[i] != '\0'){
                generatedWord = generatedWord + tempArray[i];
            }else if(tempArray[i] == '*'){
                continue;
            }else{
                System.out.println(generatedWord);
                HashInsert(generatedWord);
                generatedWord = "";
            }
        }
    }

    private boolean checkPrime(int n){
        for(int i = 2; i < n/2 ; i++){
            if(n%i==0){
                return false;
            }
        }
        return true;
    }
}