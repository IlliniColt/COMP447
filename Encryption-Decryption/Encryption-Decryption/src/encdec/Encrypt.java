package encdec;

import java.util.*;
import java.io.*;

public class Encrypt
{
	protected static int hash(String pw)
	{
		int hash = 7;
		for (int i=0; i < pw.length(); i++) {
		    hash = hash*31+pw.charAt(i);
		} 
		hash += 1717986918;
		//System.out.println(hash);
		return hash;
	}
	
	protected static byte[] getFile(String filename)
	{
		File file = new File(filename);
		
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Exiting.");
			System.exit(0);
		}
		DataInputStream dataIn = new DataInputStream(fileIn);
		
		byte filedata[] = new byte [(int)(file.length())];
		try {	
			dataIn.read(filedata);
			dataIn.close();
		} catch (IOException e) {
			System.out.println("IO Exception. Unable to read from file.");
			System.exit(0);
		}
		return filedata;

	}
	public static void encrypt(byte[] filedata, String filename)
	{
		filename = filename.substring(0, filename.length()-4);
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filename + ".enc");
		} catch (FileNotFoundException e) {
			System.out.println("Unable to write encrypted file");
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
		
		System.out.println("Enter a passcode to encrypt your data");
		String pw = input.nextLine();
		int hashPass = hash(pw);
		pw = null;						//clear password string from memory
		
		System.out.println("Enter name of file to be encrypted");
		String filename = input.nextLine();
		byte filedata[] = getFile(filename);
		
		/*		
		 *		for (int i=0; i < filedata.length; i++){		//confirm that data read in properly
		 *			System.out.print((char)filedata[i]);
		 *
		}*/
		encrypt(filedata, filename);
	}
}