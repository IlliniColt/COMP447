package encdec;

import java.io.*;

public class EncDec
{
	private static final int KEYSIZE = 4;		//Size of key in bytes
	
	/**
	 * Performs a bitwise exclusive or operation over the length of
	 * a file by repeating a key over the file. Note that KEYSIZE 
	 * must octal
	 * 
	 * @param data	byte array containing data to be manipulated
	 * @param key	key that will be repeatedly XOR'ed over the data
	 * @return		XOR'ed data
	 */
	protected static byte[] xor(byte[] data, int key)
	{
		byte keydata[] = new byte[KEYSIZE];
		for (int i=0; i<KEYSIZE; i++){
			keydata[i] = (byte)(key>>(8*(KEYSIZE-(i+1))));	//spread the key over a byte array
		}
		for (int i=0; i<data.length; i=i+KEYSIZE){
			int size = data.length - i;		//size of block to be decrypted (typically KEYSIZE)
			if (size >= KEYSIZE)			//used to prevent overrun errors at end of data set if 
				size = KEYSIZE;				//data set size is not a multiple of KEYSIZE
			
			for (int j=0; j<size; j++){			//perform XOR over block of data from file 
				data[j+i] = (byte) (data[j+i] ^ keydata[j]);
			}
		}
		return data;
	}
	
	/**
	 * Performs a data shift on each byte of a file, shifting each
	 * bit to the left and carrying the left-most bit to the right
	 * 
	 * @param data	byte array containing data to be shifted
	 * @return		bitshifted byte array
	 */
	protected static byte[] lfShift(byte[] data)
	{
		for (int i=0; i<data.length; i++)
			if (data[i] < 0)					//carry the 1
				data[i] = (byte)(((int)data[i]<<1)+1);
			else
				data[i] = (byte)((int)data[i]<<1);
		return data;
	}
	
	/**
	 * Performs a data shift on each byte of a file, shifting each
	 * bit to the right and carrying the right-most bit to the left
	 * 
	 * @param data	byte array containing data to be shifted
	 * @return		bitshifted byte array
	 */
	protected static byte[] rtShift(byte[] data)
	{
		for (int i=0; i<data.length; i++)
			if ((((int)data[i] % 2 == 1) && ((int)data[i] >= 0)) || (((int)data[i] % 2 == 0) && ((int)data[i] < 0)))
				data[i] = (byte)(((int)data[i]>>1)+128);			//carry the 1
			else
				data[i] = (byte)((int)data[i]>>1);
		return data;
	}
	
	/**
	 * A simple hashing algorithm to hash a password
	 * 
	 * @param pw	the password to hash
	 * @return		hashed password as 32 bit int value
	 */
	protected static int hash(String pw)
	{
		int hash = 7;
		for (int i=0; i < pw.length(); i++) {
		    hash = hash*31+pw.charAt(i);
		} 
		hash += 1717986918;			//creates more effective hashes, particularly with short passcodes
		return hash;
	}
	
	/**
	 * Reads file data into a byte array
	 * 
	 * @param filename	name of the file to be read in
	 * @return			a byte array containing the contents of the file
	 */
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
}