/*==============================================================================

name:       Logger.java

purpose:    Supports logging from a GWT client.

history:    Mon Jul 21, 2016 13:00:00 (LBM) created.

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package com.giavaneers.util.gwt;
                                       // imports ----------------------------//
                                       // (none)                              //
                                       // Logger =============================//
public class Logger
{
                                       // constants ------------------------- //
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       Logger - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of Logger if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
protected Logger()
{
}
/*------------------------------------------------------------------------------

@name       Logger - default constructor
                                                                              */
                                                                             /**
            Default constructor

@return     An instance of Logger if successful.

@history    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static Logger newInstance()
{
   return(new Logger());
}
/*------------------------------------------------------------------------------

@name       createLogMessage - log error level message
                                                                              */
                                                                             /**
            Log error level message.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static String createLogMessage(
   String prefix,
   String msg)
{
   return(prefix + " " + System.currentTimeMillis() +  " : " + msg);
}
/*------------------------------------------------------------------------------

@name       getStackTrace - get current stack trace
                                                                              */
                                                                             /**
            Get current stack trace.

@return     current stack trace.

@history    Mon May 19, 2014 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
public static String getStackTrace(
   Throwable t)
{
   String stackTrace = t.toString() + "\n";
   StackTraceElement[] elements = t.getStackTrace();
   for (int i = 1; i < elements.length; i++)
   {
      stackTrace += elements[i].toString() + "\n";
   }

   Throwable cause = t.getCause();
   if (cause != null)
   {
      stackTrace += "Caused by " + cause.toString() + "\n";
      stackTrace += getStackTrace(cause);
   }
   return(stackTrace);
}
/*------------------------------------------------------------------------------

@name       logError - log error level message
                                                                              */
                                                                             /**
            Log error level message.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void logError(
   String msg)
{
   logNative(createLogMessage("ERROR", msg));
}
/*------------------------------------------------------------------------------

@name       logError - log error level message
                                                                              */
                                                                             /**
            Log error level message.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void logError(
   Throwable t)
{
   logError(t.toString());
}
/*------------------------------------------------------------------------------

@name       logInfo - log info level message
                                                                              */
                                                                             /**
            Log info level message.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void logInfo(
   String msg)
{
   logNative(createLogMessage("INFO", msg));
}
/*------------------------------------------------------------------------------

@name       logNative - logNative
                                                                              */
                                                                             /**
            Log message.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static native void logNative(
   String msg)
/*-{
   $wnd.console.log(msg);
}-*/;
/*------------------------------------------------------------------------------

@name       logWarning - log warning level message
                                                                              */
                                                                             /**
            Log warning level message.

@history    Thu June 06, 2013 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public static void logWarning(
   String msg)
{
   logNative(createLogMessage("WARN", msg));
}
}//====================================// end Logger -------------------------//
