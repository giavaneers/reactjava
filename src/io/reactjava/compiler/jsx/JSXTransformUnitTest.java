/*==============================================================================

name:       JSXTransformUnitTest.java

purpose:    Unit test for ReactJava JSX Transform.

history:    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

notes:
                           COPYRIGHT (c) BY GIAVANEERS, INC.
            This source code is licensed under the MIT license found in the
                LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.compiler.jsx;
                                       // imports --------------------------- //
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
                                       // JSXTransformUnitTest ===============//
public class JSXTransformUnitTest
{
                                       // class constants --------------------//
                                       // (none)                              //
                                       // class variables ------------------- //
                                       // (none)                              //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       main - standard main method
                                                                              */
                                                                             /**
            Standard main method. This implementation is null.

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static void main(
   String[] args)
{
   String testNum =
      args.length == 0
         ? "0"
         : args[0].toLowerCase().equals("unittests")
            || args[0].toLowerCase().equals("unittest")
            ? "0" : args[0];

   boolean bOK = unitTest(Integer.parseInt(testNum));

   System.out.println("---------------------------");
   System.out.println("Unit tests -> " + (bOK ? "SUCCESS!" : "ERROR"));
   System.out.println("---------------------------");
}
/*------------------------------------------------------------------------------

@name       unitTest - do unit tests
                                                                              */
                                                                             /**
            Do unit test.

@return     void

@history    Thu May 17, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public static boolean unitTest(
   int testNum)
{
   String[] expectedResults =
   {
                                       // test 0                              //
      "",
                                       // test 1                              //
      "public void render() { String buttonClass = \"button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton\"; String buttonClassLogin = buttonClass + \"ionButtonLogin\"; java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicScrollContent\"),new io.reactjava.client.components.ionic.IonicScrollContent(Properties.with(\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicGrid\"),new io.reactjava.client.components.ionic.IonicGrid(Properties.with(\"fixed\",\"true\",\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicRow\"),new io.reactjava.client.components.ionic.IonicRow(Properties.with(\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicColumn\"),new io.reactjava.client.components.ionic.IonicColumn(Properties.with(\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"splashBg\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"splashInfo\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"splashLogo\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"splashIntro\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" Your collection of films and music streamed to you anywhere in the world \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"padding\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"Button\",Properties.with(\"variant\",\"contained\",\"fullWidth\",true,\"onClick\",props.get(\"signUpHandler\"),\"className\",\"button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" Sign Up \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"Button\",Properties.with(\"variant\",\"contained\",\"fullWidth\",true,\"onClick\",props.get(\"signInHandler\"),\"className\",\"button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton ionButtonLogin\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" Sign In \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 2                              //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"scrollContent\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" {props.getChildren()} \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 3                              //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"h1\",Properties.with(\"className\",\"hello\",\"style\",Properties.with(\"color\",\"blue\",\"fontFamily\",\"helvetica\"),\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Hello&nbsp;World!\");root=root == null ? elem : root;parents.pop();if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 4                              //
      "public void render() { int a = 0; } ",
                                       // test 5                              //
      "public void render() {java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root = null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"A\"),new A(Properties.with(\"id\", getNextId())).props());root = root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"B\"),new B(Properties.with(\"id\", getNextId())).props());root = root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"B\"),new B(Properties.with(\"id\", getNextId())).props());root = root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\", getNextId()),\" \");root = root == null ? elem : root;parents.pop();} ",
                                       // test 6                              //
      "public void render() { String buttonClass = \"button ionButton ionButtonMd\"; if (props.getBoolean(\"login\")) { buttonClass += \" ionButtonLogin\"; } java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"button\",Properties.with(\"className\",buttonClass,\"onClick\",props.get(\"onClick\"),\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"className\",\"buttonInner\",\"id\",getNextId()),getText());root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"buttonEffect\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 7                              //
      "public void render() { String imgURL = \"images/logo.png\"; java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.components.ionic.IonicScrollContent\"),new io.reactjava.client.components.ionic.IonicScrollContent(Properties.with(\"fixed\",\"true\",\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"App\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"header\",Properties.with(\"className\",\"App-header\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"img\",Properties.with(\"src\",imgURL,\"className\",\"App-logo\",\"alt\",\"logo\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"h1\",Properties.with(\"className\",\"App-title\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Welcome to ReactJava\");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"p\",Properties.with(\"className\",\"App-intro\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" To get started,edit \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"code\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),getText());root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" and \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"strong\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"save\");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" to reload. \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 8                              //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"board\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root; for (int i = 0; i < 9; i++) { elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,ReactJava.getNativeFunctionalComponent(\"io.reactjava.client.examples.tictactoe.SubBoardView\"),new io.reactjava.client.examples.tictactoe.SubBoardView(Properties.with(\"board\",props.get(App.kKEY_BOARD),\"subBoardIndex\",i,\"key\",i,\"moveFcn\",props.get(App.kKEY_MOVE_FCN),\"id\",getNextId())).props());root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root; } elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 9                              //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"div0\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div00\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div01\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div010\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div0100\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div0101\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div02\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div020\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div03\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"div030\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\" \");root=root == null ? elem : root;if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} parents.pop();} ",
                                       // test 10                             //
      "public void render() { java.util.Stack<io.reactjava.client.core.react.ElementDsc> parents=new java.util.Stack<>();io.reactjava.client.core.react.ElementDsc root=null;io.reactjava.client.core.react.ElementDsc elem;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(\"className\",\"main-div\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"h1\",Properties.with(\"className\",\"hello\",\"style\",Properties.with(\"color\",\"white\"),\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Let's get setup\");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"h2\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Sign in now so we can access your music\");root=root == null ? elem : root;parents.pop();elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"br\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"input\",Properties.with(\"type\",\"email\",\"className\",\"form-input\",\"placeholder\",\"Email\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"br\",Properties.with(\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"input\",Properties.with(\"type\",\"password\",\"className\",\"form-input\",\"placeholder\",\"Password\",\"id\",getNextId()));root=root == null ? elem : root;elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"div\",Properties.with(props,\"className\",\"signin-button\",\"id\",getNextId()));root=root == null ? elem : root;parents.push(elem);elem =io.reactjava.client.core.react.ElementDsc.create(parents.size() > 0 ? parents.peek() : null,\"span\",Properties.with(\"id\",getNextId()),\"Sign In\");root=root == null ? elem : root;parents.pop();parents.pop();if(root != null){io.reactjava.client.core.react.ReactElement element=io.reactjava.client.core.react.ElementDsc.createElement(root);props.set(\"id\", element.props.get(\"id\"));this.renderElement = element;} } ",
                                       // test 11                             //
      "",
                                       // test 12                             //
      "",
                                       // test 13                             //
      "",
                                       // test 14                             //
      "",
                                       // test 15                             //
      "",
                                       // test 16                             //
      "",
                                       // test 17                             //
      "",
                                       // test 18                             //
      "",
                                       // test 19                             //
      "",
   };

   boolean bRetVal = true;
   try
   {
      Map<String,String> components = null;
      String             javaBlock  = null;
      String             classname  = "Component";
      String             header     = "public void render()\n{\n";
      String             footer     = "\n}\n";
      String             src        = null;
      String             content    = null;
      String             projectDir = null;
      TreeLogger logger     = new PrintWriterTreeLogger();
      boolean            bCapturing = false;

      Map<String,String> providerAndComponentCandidates = new HashMap<>();

      switch(testNum)
      {
         case 0:
         {
                                       // run unit tests                      //
            for (int iTest = 1; bRetVal && iTest < expectedResults.length; iTest++)
            {
               String expectedResult = expectedResults[iTest];
               if (!bCapturing && expectedResult.length() == 0)
               {
                  continue;
               }
               try
               {
                  bRetVal = unitTest(iTest);
               }
               catch(Throwable t)
               {
                  t.printStackTrace();
                  bRetVal = false;
               }
            }
            break;
         }
         case 1:
         {
            components = new HashMap<String,String>()
            {{
               put("IonicScrollContent", "io.reactjava.client.components.ionic.IonicScrollContent");
               put("IonicGrid",          "io.reactjava.client.components.ionic.IonicGrid");
               put("IonicRow",           "io.reactjava.client.components.ionic.IonicRow");
               put("IonicColumn",        "io.reactjava.client.components.ionic.IonicColumn");
            }};

            javaBlock =
               "\n\n"
             + "   String buttonClass = \"button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton\";\n"
             + "   String buttonClassLogin = buttonClass + \"ionButtonLogin\";\n"
             + "\n\n";

            content =
               header
             + javaBlock
             + "\n"
             + "/*--\n"
             + "<IonicScrollContent>\n"
             + "  <IonicGrid fixed='true'>\n"
             + "     <IonicRow>\n"
             + "        <IonicColumn>\n"
             + "           <div className='splashBg' />\n"
             + "           <div className='splashInfo'>\n"
             + "              <div className='splashLogo' />\n"
             + "              <div className='splashIntro'>\n"
             + "                 Your collection of films and music streamed to you\n"
             + "                 anywhere in the world\n"
             + "              </div>\n"
             + "           </div>\n"
             + "           <div className='padding'>\n"
             + "              <@material-ui.core.Button\n"
             + "                 variant='contained' fullWidth\n"
             + "                 onClick=props().get(\"signUpHandler\")\n"
             + "                 className='button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton'\n"
             + "              >\n"
             + "                 Sign Up\n"
             + "              </@material-ui.core.Button>\n"
             + "              <@material-ui.core.Button\n"
             + "                 variant='contained' fullWidth\n"
             + "                 onClick=props().get(\"signInHandler\")\n"
             + "                 className='button ionButtonMd ionButtonBlock ionButtonBlockMd ionButton ionButtonLogin'\n"
             + "              >\n"
             + "                 Sign In\n"
             + "              </@material-ui.core.Button>\n"
             + "           </div>\n"
             + "        </IonicColumn>\n"
             + "     </IonicRow>\n"
             + "  </IonicGrid>\n"
             + "</IonicScrollContent>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 2:
         {
            content =
               header
             + "\n"
             + "/*--\n"
             + "   <div class='scrollContent'>\n"
             + "      {getChildren()}\n"
             + "   </div>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 3:
         {
            content =
               header
             + "\n"
             + "/*--\n"
             + "<h1 class='hello' style='color:blue;font-family:helvetica;'>Hello&nbsp;World!</h1>"
             + "--*/\n"
             + footer;
            break;
         }
         case 4:
         {
            content = header + "int a = 0;\n" + footer;
            break;
         }
         case 5:
         {
            components = new HashMap<String,String>()
            {{
               put("A", "A");
               put("B", "B");
            }};
            content = header + "/*--\n<A><B /></A>--*/" + footer;
            break;
         }
         case 6:
         {
            javaBlock =
               "\n\n\n\n"
             + "String buttonClass = \"button ionButton ionButtonMd\";\n"
             + "if (props().getBoolean(\"login\"))\n"
             + "{\n"
             + "   buttonClass += \" ionButtonLogin\";\n"
             + "}\n"
             + "\n\n\n\n";

            content =
               header
             + javaBlock
             + "/*--\n"
             + "\n\n\n\n"
             + "<button class={buttonClass} onClick=props().get(\"onClick\")>\n"
             + "  <span class='buttonInner'>{getText()}</span>\n"
             + "  <div class='buttonEffect'/>\n"
             + "</button>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 7:
         {
                                       // this case simply parsed markup      //
            if (false)
            {
               src =
                  "<!-- javablock -->\n"
                + "<div class=\"container\">\n"
                + " <div class=\"board\">\n"
                + "<!-- javablock -->\n"
                + "  <div class=\"cell\" style=\"background-color:{this.getColor(i)}\" onClick=\"{this.clickHandler}\" />\n"
                + "<!-- javablock -->\n"
                + " </div> \n"
                + " <ul>\n"
                + "<!-- javablock -->\n"
                + "  <li key=\"{sKey}\">{color}</li>\n"
                + "<!-- javablock -->\n"
                + " </ul> \n"
                + "</div>\n"
                + "<!-- javablock -->\n";
            }
            else
            {
               src =
                  "<React.Fragment>\n"
                + " <h1>Hello</h1>\n"
                + " <p>world!</p>\n"
                + "</React.Fragment>\n";
            }
            break;
         }
         case 8:
         {
                                       // react fragment                      //
            classname = "reactjavawebsite.App";
            content   =
               header
             + "/*--\n"
             + "<React.Fragment>\n"
             + " <h1>Hello</h1>\n"
             + " <p>world!</p>\n"
             + "</React.Fragment>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 9:
         {
                                       // react fragment shorthand syntax     //
            classname = "reactjavawebsite.App";
            content   =
               header
             + "/*--\n"
             + "<>\n"
             + " <h1>Hello</h1>\n"
             + " <p>world!</p>\n"
             + "</>\n"
             //+ "<@material-ui.core.Button\n"
             //+ "    class='button'\n"
             //+ "    variant='contained'\n"
             //+ "    fullWidth={true}\n"
             //+ "    onClick={this.buttonClickHandler}>\n"
             //+ "    Change Colors\n"
             //+ "</@material-ui.core.Button>\n"
             + "--*/\n"
             + footer;
            break;
         }
         case 10:
         {
            if (false)
            {
               classname = "io.reactjava.client.examples.helloworld.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaExamples/src/"
                      + "io/reactjava/client/examples/helloworld/App.java"),
                      null);
            }
            else if (false)
            {
               classname = "io.reactjava.client.examples.simple.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaExamples/src/"
                      + "io/reactjava/client/examples/simple/App.java"),
                      null);
            }
            else if (false)
            {
                                       // an app directly binding the native  //
                                       // react-split-pane component without  //
                                       // the ReactJava SplitPane component   //

               classname  = "io.reactjava.client.examples.splitpane.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaExamples/src/"
                      + "io/reactjava/client/examples/splitpane/App.java"),
                      null);
               projectDir =
                  "/Users/brianm/working/IdeaProjects/ReactJava/"
                + "ReactJavaExamples";
            }
            else if (true)
            {
                                       // an app indirectly binding the native//
                                       // react-split-pane using the ReactJava//
                                       // SplitPane component                 //
               providerAndComponentCandidates.put(
                  "io.reactjava.client.components.splitpane.SplitPane",
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaComponents/src/io/reactjava/client/components/"
                      + "splitpane/SplitPane.java"),
                     null));

               classname  = "io.reactjava.client.examples.splitpane.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaExamples/src/"
                      + "io/reactjava/client/examples/splitpane/App.java"),
                      null);
               projectDir =
                  "/Users/brianm/working/IdeaProjects/ReactJava/"
                + "ReactJavaExamples";
            }
            else if (false)
            {
               classname =
                  "io.reactjava.client.examples.threebythree.step09.board.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaExamples/src/"
                      + "io/reactjava/client/examples/threebythree/step09/board/App.java"),
                      null);
            }
            else if (false)
            {
               classname = "io.reactjava.compiler.codegenerator.tests.allinonefile.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJava/src/io/reactjava/compiler/codegenerator/tests/"
                      + "allinonefile/App.java"),
                     null);
            }
            else if (false)
            {
               classname = "io.reactjava.client.examples.statevariable.twosquaresoneclass.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaExamples/src/"
                      + "io/reactjava/client/examples/statevariable/twosquaresoneclass/App.java"),
                     null);
            }
            else if (false)
            {
               classname = "helloworld.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "GWTCompiler/src/helloworld/App.java"),
                     null);
            }
            else if (false)
            {
               classname = "io.reactjava.client.components.generalpage.ContentBody";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJava/src/io/reactjava/client/components/"
                      + "generalpage/ContentBody.java"),
                     null);
            }
            else if (false)
            {
               providerAndComponentCandidates.put(
                  "io.reactjava.client.components.generalpage.GeneralAppBar",
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaComponents/src/io/reactjava/client/components/"
                      + "generalpage/GeneralAppBar.java"),
                     null));

               providerAndComponentCandidates.put(
                  "io.reactjava.client.components.generalpage.SideDrawer",
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaComponents/src/io/reactjava/client/components/"
                      + "generalpage/SideDrawer.java"),
                     null));

               classname  =
                  "io.reactjava.client.examples.materialui.theme.App";
               src =
                  IJSXTransform.getFileAsString(
                     new File(
                        "/Users/brianm/working/IdeaProjects/ReactJava/"
                      + "ReactJavaExamples/src/"
                      + "io/reactjava/client/examples/materialui/theme/App.java"),
                     null);
               content = src;
            }
            break;
         }
      }

      if (projectDir  != null)
      {
         IConfiguration.projectDirPath[0] = projectDir;
      }

      switch(testNum)
      {
         case 0:
         {
            break;
         }
         case 7:
         case 8:
         case 9:
         {
                                       // these cases simply parse markup     //
            //System.out.println(parseDocument(classname, src, logger).get(0).toString());
            break;
         }
         case 10:
         {
            //                           // these cases go through process()    //
            //providerAndComponentCandidates.put(classname, src);
            //IPreprocessor.parseCandidatesOnInitialInvocation(providerAndComponentCandidates, logger);
            //
            //                           // get components lazily               //
            //IPreprocessor.getParsedComponents(logger);
            //
            //                           // get providers lazily                //
            //IPreprocessor.getParsedProviders(logger);
            //
            //String encoding      = "UTF-8";
            //byte[] modifiedBytes =
            //   new JSXTransform().process(
            //      classname, src.getBytes("UTF-8"), encoding, logger);
            //
            //String generated  = new String(modifiedBytes, encoding);
            //String pretty     = JSXTransform.pretty(generated);
            //String normalized = JSXTransform.normalizeParsed(generated, bCapturing);
            break;
         }
         default:
         {
            //String generated =
            //   new JSXTransform().parse(classname, content, components, logger);
            //
            //String pretty = JSXTransform.pretty(generated);
            //
            //                           // assign second arg 'true' when       //
            //                           // capturing test result and 'false'   //
            //                           // when running test                   //
            //String normalized =
            //   JSXTransform.normalizeParsed(generated, bCapturing);
            //
            //bRetVal =
            //   bCapturing ? true : expectedResults[testNum].equals(normalized);
            //
            //System.out.println("Unit test " + testNum + " -> "
            //   + (bRetVal ? "SUCCESS!" : "ERROR"));
         }
      }

      //System.out.println(generateInjectScript(null).getAbsolutePath());
   }
   catch(Exception e)
   {
      e.printStackTrace();
      bRetVal = false;
   }
   return(bRetVal);
}
}//====================================// end JSXTransform ===================//
