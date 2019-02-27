JSXTransform and JSXTransformStandalone.

To use JSXTransform:

1. Add the following to the module gwt.xml file:

   <generate-with class="io.reactjava.codegenerator.ReactCodeGenerator">
     <when-type-assignable class="io.reactjava.client.core.react.IReactCodeGenerator" />
   </generate-with>

2. Remove the following from the module gwt.xml file:

   <generate-with class="io.reactjava.codegenerator.ReactComponentCodeGenerator">
     <when-type-assignable class="io.reactjava.client.core.react.Component" />
   </generate-with>

3. Assign IConfiguration.kSRCCFG_RENDER_INLINE to false.


To use JSXTransformStandalone:

1. Remove the following from the module gwt.xml file:

   <generate-with class="io.reactjava.codegenerator.ReactCodeGenerator">
     <when-type-assignable class="io.reactjava.client.core.react.IReactCodeGenerator" />
   </generate-with>

2. Add the following to the module gwt.xml file:

   <generate-with class="io.reactjava.codegenerator.ReactComponentCodeGenerator">
     <when-type-assignable class="io.reactjava.client.core.react.Component" />
   </generate-with>

3. Assign IConfiguration.kSRCCFG_RENDER_INLINE to true.

