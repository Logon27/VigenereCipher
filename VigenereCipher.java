import java.util.Scanner;

/*
 * Vigenere Cipher
 * This program encrypts and decrypts messages.
 * A initial character is used to generate the cipher table.
 * A key is used to encrypt the message one character at a time.
 */

public class VigenereCipher {
	
	char[][] cipherTable = new char[26][26];
	
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private String key;
	
	/*
	 * This constructor sets the key variable because the encode / decode methods dont take a key parameter for whatever reason.
	 * This method also generates the cipher table given the initial character, i.e. the code.
	 */
	public VigenereCipher( char code, String key ) {
		setCipher(code);
		this.key = key;
	}
	
	//encodes a given message using the given key and generated cipher table
	String encode( String message ) {
		char[] messageAsChar = new char[message.length()];
		
		int count = 0;
		for(int i = 0; i < message.length(); i++) {
			//this was added to skip spaces for encryption.
			if(message.charAt(i) == ' ') {
				messageAsChar[i] = ' ';
				if(count < key.length()) {
					count++;
				} else {
					count = 1;	
				}
				
			} else {
				if(count < key.length()) {
					/*
					 * grabbing the character from the defined alphabet indexes of the key
					 * character and message character at the current count and iteration.
					 */
					messageAsChar[i] = cipherTable[alphabet.indexOf(key.charAt(count))][alphabet.indexOf(message.charAt(i))];
					count++;	
				} else {
					count = 0;	
					messageAsChar[i] = cipherTable[alphabet.indexOf(key.charAt(count))][alphabet.indexOf(message.charAt(i))];
					count++;
				}	
			}
			
		}
		
		String encryptedMessage = new String(messageAsChar);
		return encryptedMessage;
		 
	}
	
	//Method used to decode a cipher given you already have the key.
	String decode( String cipher ) {
		char[] messageAsChar = new char[cipher.length()];
		
		int count = 0;
		for(int i = 0; i < cipher.length(); i++) {
			//this was added to skip spaces for decryption.
			if(cipher.charAt(i) == ' ') {
				messageAsChar[i] = ' ';
				if(count < key.length()) {
					count++;
				} else {
					count = 1;	
				}
				
			} else {
				if(count < key.length()) {
					//grabbing the row of the cipher table at the index of the alphabet of the key's char at the given count.
					char row[] = cipherTable[alphabet.indexOf(key.charAt(count))];
					for(int j = 0; j < row.length; j++) {
						//looping through the row until a match is found.
						if(row[j] == cipher.charAt(i)) {
							messageAsChar[i] = alphabet.charAt(j);
						}
					}
					count++;	
				} else {
					count = 0;	
					//doing the same thing as the previous if statement, but after the count is reset to 0.
					char row[] = cipherTable[alphabet.indexOf(key.charAt(count))];
					for(int j = 0; j < row.length; j++) {
						if(row[j] == cipher.charAt(i)) {
							messageAsChar[i] = alphabet.charAt(j);
						}
					}
					count++;	
				}	
			}
			
		}
		
		String decryptedMessage = new String(messageAsChar);
		return decryptedMessage;

	}
	
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		
		//Prompting for the initial character
		System.out.print("Enter an initial character: ");
		String stringCode = scan.nextLine();
		char code = stringCode.charAt(0);
		//converting code to uppercase in case of lowercase character.
		code = Character.toUpperCase(code);
		
		//Prompting for the key
		System.out.print("Enter a key: ");
		String key = scan.nextLine();
		//converting key to uppercase in case of lowercase letters.
		key = key.toUpperCase();
		//use regex to remove all spaces
		key.replaceAll("\\s+","");
		
		//Prompting for the message to encode
		System.out.print("Enter a message: ");
		String givenMessage = scan.nextLine();
		//converting the given message to uppercase in case of lowercase letters.
		givenMessage = givenMessage.toUpperCase();
		
		VigenereCipher cipherObject = new VigenereCipher(code, key);
		
		//stores the result of the encoded message
		String cipherResult = cipherObject.encode(givenMessage);
		//stores the result of the decoded cipher
		String cipherDecodeResult = cipherObject.decode(cipherResult);
		System.out.print("Cipher: ");
		System.out.print(cipherResult);
		System.out.println();
		
		//Added a prompt for decoding the cipher if needed or for testing.
		System.out.print("Would you like to decode the cipher for viewing? Y/N : ");
		String nextline = scan.nextLine();
		if(nextline.charAt(0) == 'Y') {
			System.out.print("Decoded Cipher: ");
			System.out.print(cipherDecodeResult);
			System.out.println();
		} else {
			System.out.println("Cipher will remain encoded.");
		}

		
		//Added a prompt for viewing the cipher if needed or for testing.
		System.out.print("Would you like to print out the cipher table for viewing? Y/N : ");
		nextline = scan.nextLine();
		if(nextline.charAt(0) == 'Y') {
			cipherObject.printGrid();
		} else {
			System.out.println("Cipher table will remain hidden.");
		}
	}
	
	//sets the multidimensional array based off the input character.
	void setCipher(char c) {
		int ch = c;
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 26; j++) {
				//increments the character until it reaches Z
				if(ch != 'Z') {
					cipherTable[i][j] = (char) ch;
					ch++;	
				//resets the current character to 'A' if the character 'Z' is reached.
				} else {
					cipherTable[i][j] = (char) ch;
					ch = 'A';
				}
			}
			//increments the character until it reaches Z
			if(ch != 'Z') {
				ch++;
			//resets the current character to 'A' if the character 'Z' is reached.
			} else {
				ch = 'A';
			}
		}
	}
	
	//A printGrid method used to print the whole cipher table for viewing.
	void printGrid() {
	   System.out.println();
	   for(int i = 0; i < 26; i++) {
	      for(int j = 0; j < 26; j++){
	         System.out.printf("%c ", cipherTable[i][j]);
	      }
	      System.out.println();
	   }
	}

}
