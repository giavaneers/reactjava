/*==============================================================================

name:       IReactJavaCodeGenerator.java

purpose:    ReactJava code generator interface.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

notes:

                  This program was created by Giavaneers
        and is the confidential and proprietary product of Giavaneers Inc.
      Any unauthorized use, reproduction or transfer is strictly prohibited.

                     COPYRIGHT 2018 BY GIAVANEERS, INC.
      (Subject to limited distribution and restricted disclosure only).
                           All rights reserved.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import java.util.function.Function;

// IReactCodeGenerator ================//
public interface IReactCodeGenerator
{
                                       // class constants --------------------//
String kREACT_JAVA_DIR_NAME = "reactjava";

                                       // class variables ------------------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       boot - application entry point
                                                                              */
                                                                             /**
            aApplication entry point.

@return     void

@param      className      app class name

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Element boot(String className);

/*------------------------------------------------------------------------------

@name       getFactory - get factory for specified classname
                                                                              */
                                                                             /**
            Get factory for specified classname.

@return     factory for specified classname

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
Function getFactory(
   String classname);

/*------------------------------------------------------------------------------

@name       getPlatformProvider - get platform provider to be injected
                                                                              */
                                                                             /**
            Get platform provider to be injected.

@return     platform provider to be injected

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
String getPlatformProvider();

}//====================================// end IReactCodeGenerator ============//
