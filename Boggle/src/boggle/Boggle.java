package boggle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Boggle {
//Open File
//Save Words on ArrayList
//Merge Sort
//Binary Search(Currently having problems)

    public static void main(String[] args) throws FileNotFoundException, IOException{
        Scanner in = new Scanner (System.in);
        ArrayList<String> words = new ArrayList<>();
        String user = "";
        boolean found, found2;
        
        System.out.println("Enter the size of the board: ");
        int size=in.nextInt();
        
        char [][] board = new char [size][size];
        
        OpenFile(words); 
        MergeSort(words,0,words.size()-1);
        
        while(user.equals("0") == false){
            Board(board);
            user = EnterWord(user);
            
            if(user.equals("0")==false){
                found = BinarySearch(words, user, false, 0, words.size()-1);
                
                if(found==true){
                    found2=false;
                    for(int c=0; c<board.length; c++){
                        for(int i=0; i<board[0].length; i++){
                            String temp = user.toUpperCase();
                            if(board[c][i]==temp.charAt(0)){
                                found2=BoardSearch(board, c, i, 0, temp);
                                if(found2==true){
                                    break;
                                }
                            }
                            
                        }
                        if(found2==true)break;
                    }
                    /*for(int c=0;c<board.length;c++){
                        for(int i=0;i<board[0].length;i++){
                            if((char)board[c][i]>90){
                                board[c][i]=(char)(board[c][i]-32);
                            }
                        }
                    }*/
                    if(found2==true){
                        System.out.println("The word is on the board");
                    }
                    else System.out.println("The word is not on the board");
                }
                else System.out.println("The word is not in the dictionary");
            }
        }
        
    }
    public static String EnterWord(String user){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a word or 0 to end the program: ");
        user = in.next();
        return user;
    }
    public static void Board(char [][] board){
        Random rnd = new Random();
        for(int c=0; c<board.length; c++){
            for(int i=0; i<board[0].length; i++){
                board[c][i] = (char) ((char)rnd.nextInt(26)+65);
            }
        }
        
        for (char[] board1 : board) {
            for (int i = 0; i<board[0].length; i++) {
                System.out.print(board1[i] + " ");
            }
            System.out.println();
        }
        
    }
    public static void OpenFile(ArrayList words) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader (new File("dictionary.txt")));
        String line;
        while((line = read.readLine()) != null){
            words.add(line);
        }
        read.close();
    }
    
    public static void MergeSort(ArrayList words, int lo, int n){
        int low = lo;
        int high = n;
        if(low>=high){
            return;
        }
        int middle = (low+high)/2;
        MergeSort(words, low, middle);
        MergeSort(words, middle+1, high);
        int end_low = middle;
        int start_high = middle + 1;
        while((low <= end_low) && (start_high <= high)){
            if(words.get(low).toString().compareTo(words.get(start_high).toString())<0){
                low++;
            }
            else{
                String TempWords = words.get(start_high).toString();
                for(int c=start_high-1 ; c>=low; c--){
                    words.set(c+1, words.get(c));
                }
                words.set(low, TempWords);
                low++;
                end_low++;
                start_high++;
            }
        }
    }
    public static boolean BinarySearch(ArrayList<String>words, String user, boolean wordFound, int start, int end){
        double middle;
        int mid, compare;
        middle = (start + end) / 2;
        mid = (int)middle;
        compare = words.get(mid).compareTo(user);
        //System.out.println(start + "/ " + end + "/ " + mid);
        if(compare == 0){
            wordFound = true;
            //System.out.print(words.get(mid));
        }
        else{
            if(end - start <= 1){
                compare = words.get(mid++).compareTo(user);
                if(compare == 0){
                    wordFound = true;
                    //System.out.print(words.get(mid));
                }
            }
            else{
                if(compare<0){
                start = mid;
                }
                else end = mid;
                wordFound = BinarySearch(words, user, wordFound, start, end);
            }
        }
        return wordFound;
    }
    public static boolean BoardSearch(char[][]board, int x, int y, int c, String t){
        if(x<0 || y<0 || x>=board.length || y>=board[0].length)return false;
        if(c==t.length())return true;
        if(board[x][y]!= t.charAt(c))return false;
        c++;
        board[x][y]=(char)(board[x][y]+32);
        if(BoardSearch(board, x+1, y, c, t)==true)return true;//up
        if(BoardSearch(board, x-1, y, c, t)==true)return true;//left
        if(BoardSearch(board, x, y+1, c, t)==true)return true;//down
        if(BoardSearch(board, x, y-1, c, t)==true)return true;//right
        if(BoardSearch(board, x-1, y-1, c, t)==true)return true;//right-up
        if(BoardSearch(board, x+1, y+1, c, t)==true)return true;//left-down
        if(BoardSearch(board, x+1, y-1, c, t)==true)return true;//left-up
        if(BoardSearch(board, x-1, y+1, c, t)==true)return true;//right-down
        c--;
        board[x][y]=(char)(board[x][y]-32);
        return false;
    }
    
    
}
