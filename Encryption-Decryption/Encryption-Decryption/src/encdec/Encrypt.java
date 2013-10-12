package encdec;

import java.util.*;
import java.io.*;

public class Encrypt
{
	/**
	 * Performs encryption operation consisting of bit-shifting and
	 * using a key to perform bitwise exclusive or operations over
	 * a set of data.
	 * 
	 * @param filedata	data to be encrypted
	 * @param key		user provided key that will be used to encrypt the data
	 * @return			encrypted data
	 */
	private static byte[] encrypt(byte[] filedata, int key)
	{
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.lfShift(filedata);
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.lfShift(filedata);
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.lfShift(filedata);
		filedata = EncDec.lfShift(filedata);
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.lfShift(filedata);
		filedata = EncDec.lfShift(filedata);
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.lfShift(filedata);
		filedata = EncDec.xor(filedata, key);
		
		return filedata;
	}
	
	/**
	 * Writes encrypted data to file
	 * 
	 * @param filedata	encrypted byte array to be written to file
	 * @param filename	name and path of file, which will be manipulated to create new file name
	 */
	private static void writeFile(byte[] filedata, String filename)
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
			try {
				dataOut.write(filedata);
			} catch (IOException e) {
				System.out.println("IO Exception. Unable to write to file.");
				System.exit(0);
			}
	}
	
	public static void main(String[]args)
	{
		int key;				//encryption key that will be used for this operation
		byte filedata[];		//array where file data is stored while operations are run over this data
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter a passcode to encrypt your data.\nFor best results, use at least 6 characters");
		String pw = input.nextLine();
		while (pw.length() < 6){		//ensure that user chooses a secure passcode
			System.out.println("Passcode too short. Enter a passcode that is at least 6 characters long");
			pw = input.nextLine();
		}
		key = EncDec.hash(pw);			//hash the password to get the key
		pw = null;						//clear password string from memory
		
		System.out.println("Enter path and name of file to be encrypted");
		String filename = input.nextLine();
		filedata = EncDec.getFile(filename);
		
		filedata = encrypt(filedata, key);
		
		writeFile(filedata, filename);
	}
}