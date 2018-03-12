package com.fileencryptor;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public interface Wimm extends Library {
	Wimm INSTANCE = (Wimm) Native.loadLibrary("winmm.dll", Wimm.class);
	int mciSendStringA(String command, StringBuilder  lpszReturnString, int cchReturn,  IntByReference i );

}
