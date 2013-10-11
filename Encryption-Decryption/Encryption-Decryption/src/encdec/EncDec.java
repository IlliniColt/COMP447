package encdec;

import java.util.*;
import java.io.*;

public class EncDec
{
	protected static byte[] xor(byte[] filedata, int key)
	{
		byte keydata[] = new byte[4];
		for (int i=0; i<4; i++){
			keydata[i] = (byte)(key>>(24-(8*i)));	//spread the key over a byte array
			//System.out.println("Key" + i + ": " + keydata[i]);
		}
		for (int i=0; i<filedata.length; i=i+4){
			int size = filedata.length - i;		//size of block to be decrypted (typically 4 bytes)
			if (size >= 4)						//used to prevent errors if data is not multiple of 4 bytes
				size = 4;
			for (int j=0; j<size; j++){
				filedata[j+i] = (byte) (filedata[j+i] ^ keydata[j]);
			}
		}
		return filedata;
	}
	
	protected static int hash(String pw)
	{
		int hash = 7;
		for (int i=0; i < pw.length(); i++) {
		    hash = hash*31+pw.charAt(i);
		} 
		hash += 1717986918;			//creates more effective hashes, particularly with short passcodes
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

}