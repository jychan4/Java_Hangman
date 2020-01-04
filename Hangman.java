
//Journ Chan Project Hangman

//import java.util.Scanner;
//import java.util.Random;
//import java.util.ArrayList;
import java.util.*;

public class Hangman
{
	public static void printArray(char[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}

	public static boolean isTheWordGuessed(char[] array)
	{
		for(int i = 0; i < array.length; i++)
		{
			if (array[i] == '_')
			{
				return false;
			}
		}
		return true;
	}
	
	public static boolean isUpper(char c){
	    if((int) c > 64 && (int) c < 91){
	        return true;
	    }else{
	        return false;
	    }
	}
	
	public static boolean isLower(char c){
	    if((int) c > 96 && (int) c < 123){
	        return true;
	    }else{
	        return false;
	    }
	}
	public static boolean isDigit(char c){
	    if((int) c > 47 && (int) c < 58){
	        return true;
	    }else{
	        return false;
	    }
	}
	
	public static boolean isInvalid(char c){
	    /* for everything but upper or lower alpha chars*/
        if(((int) c < 65) || ((int) c > 90 && (int) c < 97) || ((int) c > 122)){
			return true;    
		}else{
		    return false;
		}
	}
	
	public static boolean wasUsed(char c, char[] prevGuess){
	    for(int i=0;i<prevGuess.length;i++){
		    if((int)prevGuess[i] == (int) c){
		        return true;
		    }else{
		        if(isLower(c)){
		             if((int)prevGuess[i] == ((int) c - 32)){
		               return true;
		             }
		         }else if(isUpper(c)){
	    	         if((int)prevGuess[i] == ((int) c + 32)){
		               return true;
		             }
	    	     }else if(isDigit(c)){ // didn't match and no need for conversion so false
	    	         return false;
		        }else{// invalid character used
		            System.out.println("ERROR: wasUsed() detected an invalid");
		            return false;
		        }
		    }
		}
		return false;
	}
	
	public static boolean playingGame(){
	    Scanner sc = new Scanner(System.in);
		int min = 1;
	    int max = 10;
		int randInt = (int) (Math.random() *(((max-1)-(min-1))+1))+(min-1);
		String phraseList[] = {"Savage", "interesting", "google", "dolphin", "alpha", "Beta", "Omega", "PsI", "dElTa", "OmI"};
		StringBuilder randWord = new StringBuilder (phraseList[randInt]);
		boolean gameNotOver = true;
		boolean charMatched = false;
		boolean isWrong = false;
		boolean invalidChar = false;
		int prevGuessCounter = 0;
		
		System.out.println("Welcome to my game of Hangman");
		char[] prevGuess = new char[52]; //to keep track of characters already guessed;
		int remainingGuesses = 8;
		StringBuilder mysteryWord = new StringBuilder(randWord.capacity());
		
		for (int i = 0; i < phraseList[randInt].length(); i++) //stars out mysteryWord for guessing purposes
		{
			if(((int) randWord.charAt(i) > 64 && (int) randWord.charAt(i) < 91) || ((int) randWord.charAt(i) > 96 && (int) randWord.charAt(i) < 123)){
			    mysteryWord.append('*');
			}else{
			    mysteryWord.append(randWord.charAt(i));
			}
		}
		
		while(gameNotOver){
		    System.out.println(mysteryWord);
		    System.out.printf("You have %d tries left.\n", (remainingGuesses));
		    if(remainingGuesses == 0){
		        System.out.println("Game over!");
		        gameNotOver = false;
		        break;
		    }
		    System.out.print("Enter a single character: ");
		    char c = sc.next().charAt(0); //select first character in next input
		    if(isInvalid(c)){
		        System.out.println("The character you entered is invalid.");
		    }else{
		        if(wasUsed(c, prevGuess)){
		            System.out.println("The character you entered has already been used.");
		        }else{
        		    //Check each letter in the answer (randWord) for the input char (c)
        	    	for (int i = 0; i < phraseList[randInt].length(); i++){
        	    	    if((int) c == (int) randWord.charAt(i)){//matches best case secneario 1 check
        	    	        mysteryWord.replace(i, i+1, String.valueOf(randWord.charAt(i)));
            	    	    charMatched = true;
        	    	    }else{//doesn't match worst case scenario
            	    	    if(isLower(c)){
            	    	        if((int)randWord.charAt(i) == ((int) c - 32)){//convert c to upper and if it doesn't match then remainingGuesses-- 
            	    	            mysteryWord.replace(i, i+1, String.valueOf(randWord.charAt(i)));
            	    	            charMatched = true;
            	    	        }else{
            	    	            isWrong = true;
            	    	        }
            	    	    }else if(isUpper(c)){
            	    	        if((int)randWord.charAt(i) == ((int) c + 32)){
    		                        mysteryWord.replace(i, i+1, String.valueOf(randWord.charAt(i)));
            	    	            charMatched = true;
    		                    }else{
            	    	            isWrong = true;
            	    	        }
            	    	    }else if(isDigit(c)){//no conversion necessary so no match in first case -> wrong
                		        isWrong = true;
                		    }else{//invalid char
                		        System.out.println("invalid char");
            		            invalidChar = true;
                		    }
    	    	        }
        	    	}
        		    if(charMatched == false){
        		        if(isWrong){
        		            System.out.println("Wrong character.");
        		        }
        		        if(invalidChar){
        		            System.out.println("The character you entered is invalid.");
        		        }
        		        remainingGuesses--; //decrement remianing guesses by one
        		    }
        		    charMatched = false;//reset flags
        		    isWrong = false;
        		    invalidChar = false;
        		    prevGuess[prevGuessCounter] = c;
        		    prevGuessCounter++;
		        }
		    }
		    if(mysteryWord.toString().compareTo(randWord.toString())==0){
		        System.out.println("You win!");
		        break;
		    }
		}	
		System.out.print("Do you want to play again? (y/n): ");
		char c = sc.next().charAt(0);
		//under the assumption that user will only provide proper input y or n
		if(c == 'y'){
			return true;
		}else{ // c== 'n'
			return false;
		}
	}
	
	public static void main(String [] args)
	{
		while(playingGame());
		
		System.out.println("Game over.");

	}
}

