/*==============================================================================

name:       FileLogger.java

purpose:    A TreeLogger that logs to a file with timestamps.

history:    Tue Sep 01, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.util.log.AbstractTreeLogger;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import io.reactjava.compiler.jsx.IConfiguration;
import java.io.File;
import java.text.DecimalFormat;
                                       // FileLogger =========================//
public class FileLogger extends TreeLogger
{
                                       // class constants --------------------//
public static final DecimalFormat kELAPSED_TIME_FORMAT =
   new DecimalFormat("000.000");
                                       // class variables ------------------- //
                                       // none                                //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
protected PrintWriterTreeLogger base;  // base logger                         //
protected long                  start; // start nano                          //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       branch - branch
                                                                              */
                                                                             /**
            Branch.

@return     logger

@param      type        type
@param      msg         message
@param      caught      throwable
@param      helpInfo    helpInfo

@history    Tue Sep 01, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@Override
public TreeLogger branch(
   Type      type,
   String    msg,
   Throwable caught,
   HelpInfo  helpInfo)
{
   return base.branch(type, msg, caught, helpInfo);
}
/*------------------------------------------------------------------------------

@name       isLoggable - test whether is loggable
                                                                              */
                                                                             /**
            Test whether is loggable.

@return     true iff is loggable.

@param      type        type

@history    Tue Sep 01, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@Override
public boolean isLoggable(
   Type type)
{
   return base.isLoggable(type);
}
/*------------------------------------------------------------------------------

@name       log - log message
                                                                              */
                                                                             /**
            log message.

@param      type        type
@param      msg         message
@param      caught      throwable
@param      helpInfo    helpInfo

@history    Tue Sep 01, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
@Override
public void log(
   Type      type,
   String    msg,
   Throwable caught,
   HelpInfo  helpInfo)
{
   String level =
      type == TreeLogger.INFO
         ? "INFO:  "
         : type == TreeLogger.WARN
            ? "WARN:  "
            : type == TreeLogger.ERROR
               ? "ERROR: "
               : type == TreeLogger.DEBUG
                  ? "DEBUG: "
                  : "";

   double elapsed = (double)(System.currentTimeMillis() - start) / 1000;
   String message = level + kELAPSED_TIME_FORMAT.format(elapsed) + " " + msg;
   base.log(type, message, caught, helpInfo);
}
/*------------------------------------------------------------------------------

@name       newInstance - factory method
                                                                              */
                                                                             /**
            Factory method.

@return     file logger

@param      invocationLogger     logger passed with invocation
@param      logfilePath          logfile path

@history    Tue Sep 01, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
static TreeLogger newInstance(
   TreeLogger invocationLogger,
   String     logfilePath)
{
   FileLogger logger = new FileLogger();
   try
   {
      Type maxDetail =
         invocationLogger == null
            ? TreeLogger.ALL
            : invocationLogger instanceof AbstractTreeLogger
               ? ((AbstractTreeLogger)invocationLogger).getMaxDetail()
               :  TreeLogger.ALL;

      File logFile =
         new File(
            IConfiguration.getProjectDirectory(null, invocationLogger),
            logfilePath);

      logFile.delete();

      logger.base = new PrintWriterTreeLogger(logFile);
      logger.base.setMaxDetail(maxDetail);

      logger.start = System.currentTimeMillis();
   }
   catch(Exception e)
   {
      e.printStackTrace();
   }

   return(logger);
}
}//====================================// end FileLogger =====================//
