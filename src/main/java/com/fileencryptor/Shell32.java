package com.fileencryptor;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.ShellAPI;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public interface Shell32 extends ShellAPI, StdCallLibrary {

	Shell32 INSTANCE = (Shell32) Native.loadLibrary("shell32", Shell32.class);
	WinDef.HINSTANCE ShellExecuteA(int i, String lpOperation, String lpFile, String lpParameters,
			String lpDirectory, int nShowCmd);

}
