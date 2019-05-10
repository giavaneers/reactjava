/*==============================================================================

name:       IReactJavaCodeGenerator.java

purpose:    ReactJava code generator interface.

history:    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import java.util.Map;
import java.util.function.Function;
                                       // IReactCodeGenerator ================//
public interface IReactCodeGenerator
{
                                       // class constants --------------------//
String kREACT_JAVA_DIR_NAME       = "reactjava";
String kIMPORTED_STYLESHEETS_LIST = "importedStyleSheets.txt";

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
ReactElement boot(String className);

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

@name       getFactoryMap - get factory map
                                                                              */
                                                                             /**
            Get factory map.

@return     factory map

@history    Tue May 15, 2017 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public Map<String,Function> getFactoryMap();

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
