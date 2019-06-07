package in.co.sachinverma.task.Utils;

import android.os.Environment;
import android.util.Log;

import in.co.sachinverma.task.BuildConfig;

public class Logger {

    public static boolean DEBUG = true;

    public static final void v (String tag, String msg) {
        if (DEBUG) Log.v(getClassNameMethodNameAndLineNumber(), msg);
    }

    public static final void d (String msg) {
        if (DEBUG) Log.d(getClassNameMethodNameAndLineNumber(), msg);
    }

    public static final void i (String msg) {
        if (DEBUG) Log.i(getClassNameMethodNameAndLineNumber(), msg);
    }

    public static final void w (String msg) {
        if (DEBUG) Log.w(getClassNameMethodNameAndLineNumber(), msg);
    }

    public static final void e (String msg) {
        if (DEBUG) Log.e(getClassNameMethodNameAndLineNumber(), msg);
    }

    public static final void e (String msg, Exception e) {
        if (DEBUG) Log.e(getClassNameMethodNameAndLineNumber(), msg, e);
    }

    public static final void wtf (String msg) {
        if (DEBUG) Log.wtf(getClassNameMethodNameAndLineNumber(), msg);
    }

    private static String getClassName() {
        String fileName = Thread.currentThread().getStackTrace()[5].getFileName();
        return fileName.substring(0, fileName.length() - 5);
    }

    private static String getMethodName() {
        return Thread.currentThread().getStackTrace()[5].getMethodName();
    }

    private static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[5].getLineNumber();
    }

    private static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    public static String getClassNameMethodNameAndLineNumber() {
        return "[" + getPackageName() + "::" + getClassName() + "." + getMethodName() + "()-" + getLineNumber() + "]: ";
    }

}
