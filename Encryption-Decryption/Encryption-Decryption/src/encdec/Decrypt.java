package encdec;

import java.util.*;
import java.io.*;

public class Decrypt
{
	/**
	 * Performs decryption operation consisting of bit-shifting and
	 * using a key to perform bitwise exclusive or operations over
	 * a set of data. If the proper key is provided, the data will
	 * be decrypted. If an improper key is provided, the data will
	 * become further scrambled.
	 * 
	 * @param filedata	data to be decrypted
	 * @param key		user provided key that will be used to decrypt the data
	 * @return			decrypted data 
	 */
	private static byte[] decrypt(byte[] filedata, int key)
	{
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.rtShift(filedata);
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.rtShift(filedata);
		filedata = EncDec.rtShift(filedata);
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.rtShift(filedata);
		filedata = EncDec.rtShift(filedata);
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.rtShift(filedata);
		filedata = EncDec.xor(filedata, key);
		filedata = EncDec.rtShift(filedata);
		filedata = EncDec.xor(filedata, key);
		
		return filedata;
	}
	
	/**
	 * Writes decrypted data to file
	 * 
	 * @param filedata	decrypted byte array to be written to file
	 * @param filename	name and path of file, which will be manipulated to create new file name
	 */
	private static void writeFile(byte[] filedata, String filename)
	{
		filename = filename.substring(0, filename.length()-4);
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(filename + ".dec");
		} catch (FileNotFoundException e) {
			System.out.println("Unable to write decrypted file");
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
		
		System.out.println("Enter your passcode to decrypt your data");
		String pw = input.nextLine();
		key = EncDec.hash(pw);
		pw = null;						//clear password string from memory
		
		System.out.println("Enter path and name of file to be decrypted");
		String filename = input.nextLine();
		
		filedata = EncDec.getFile(filename);
		
		filedata = decrypt(filedata, key);
		
		writeFile(filedata, filename);
	}
}