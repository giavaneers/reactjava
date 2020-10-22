/*==============================================================================

name:       PostprocessorUnitTest.java

purpose:    ReactJava IPostprocessor Unit Test.

history:    Wed Sep 02, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.codegenerator;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import io.reactjava.compiler.codegenerator.ReactCodePackager.Configuration;
import java.util.HashMap;
import java.util.Map;
                                       // PostprocessorUnitTest ==============//
public class PostprocessorUnitTest
{
                                       // class constants --------------------//
                                       // map of PreprocessorUnitTest number  //
                                       // by PostprocessorUnitTest number     //
public static final Map<String,String> kTEST_CONFIG_BY_TEST_NAME =
   new HashMap<String,String>()
   {{
      for (String key : PreprocessorUnitTest.kTEST_CONFIG_BY_TEST_NAME.keySet())
      {
         put(key, key);
      }
   }};
                                       // class variables ------------------- //
                                       // none                                //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //

/*------------------------------------------------------------------------------

@name       main - standard main method
                                                                              */
                                                                             /**
            Standard main method.

@param      args, where

               args[0]     test number

@history    Wed Aug 19, 2020 18:00:00 (LBM) created.

@notes
                                                                              */
//------------------------------------------------------------------------------
static void main(
   String[] args)
{
   try
   {
      if (args.length != 1)
      {
         throw new IllegalArgumentException("Must specify a test number");
      }
                                       // run the Preprocessor unit test      //
      IPreprocessor.main(new String[]{kTEST_CONFIG_BY_TEST_NAME.get(args[0])});

      if (true)
      {
                                       // run the Postprocessor unit test     //
         TreeLogger logger = IPostprocessor.getFileLogger(null);
         Configuration configuration = new Configuration(logger);
         configuration.setProductionMode(false);

         ReactCodePackager.generateInjectScriptBrowserify(configuration, logger);
      }
      else
      {
         IPostprocessor.allPostprocessors(null, null, null);
      }
   }
   catch(Throwable t)
   {
      t.printStackTrace();
   }
}
}//====================================// end PostprocessorUnitTest ==========//
