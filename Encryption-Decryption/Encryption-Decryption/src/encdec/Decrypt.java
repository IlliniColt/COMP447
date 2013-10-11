package encdec;

import java.util.*;
import java.io.*;

public class Decrypt
{
	public static void encrypt(byte[] filedata, int key, String filename)
	{
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.rtShift(filedata, key);
				
		filename = filename.substring(0, filename.length()-4);
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filename + ".dec");
		} catch (FileNotFoundException e) {
			System.out.println("Unable to write decrypted file");
			System.exit(0);
		}
		DataOutputStream dataOut = new DataOutputStream(fileOut);
//		for (int i=0; i < filedata.length; i++){
			try {
				dataOut.write(filedata);
//				dataOut.writeChar((char)filedata[i]);
			} catch (IOException e) {
				System.out.println("IO Exception. Unable to write to file.");
				System.exit(0);
			}
//		}
	}
	
	public static void main(String[]args)
	{
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter a passcode to decrypt your data");
		String pw = input.nextLine();
		int key = EncDec.hash(pw);
		pw = null;						//clear password string from memory
		
		System.out.println("Enter name of file to be decrypted");
		String filename = input.nextLine();
		byte filedata[] = EncDec.getFile(filename);
		
		/*		
		 *		for (int i=0; i < filedata.length; i++){		//confirm that data read in properly
		 *			System.out.print((char)filedata[i]);
		 *
		}*/
		encrypt(filedata, key, filename);
	}
}