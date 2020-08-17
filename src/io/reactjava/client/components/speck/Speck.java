/*==============================================================================

name:       Speck.java

purpose:    ReactJava component for Speck molecular viewer

history:    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.components.speck;
                                       // imports --------------------------- //
import com.giavaneers.util.gwt.Logger;
import elemental2.dom.CSSProperties.WidthUnionType;
import elemental2.dom.DomGlobal;
import elemental2.dom.DomGlobal.RequestAnimationFrameCallbackFn;
import elemental2.dom.DomGlobal.SetIntervalCallbackFn;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLOptionElement;
import elemental2.dom.HTMLSelectElement;
import elemental2.dom.MouseEvent;
import elemental2.dom.WheelEvent;
import io.reactjava.client.core.react.Component;
import io.reactjava.client.core.react.INativeEffectHandler;
import io.reactjava.client.core.react.Utilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
                                       // Speck ==============================//
public class Speck extends Component
{
                                       // class constants ------------------- //
                                       // logger                              //
public static final Logger kLOGGER = Logger.newInstance();

public static final Map<String,String> kSAMPLES =
   new HashMap<String,String>()
   {{
      put("Testosterone",                "testosterone.xyz");
      put("Caffeine",                    "caffeine.xyz");
      put("Protein 4E0O",                "4E0O.xyz");
      put("Protein 4QCI",                "4QCI.xyz");
      put("DNA",                         "dna.xyz");
      put("Testosterone",                "testosterone.xyz");
      put("Gold Nanoparticle",           "au.xyz");
      put("Thiolated Gold Nanoparticle", "au_thiol.xyz");
      put("Benzene",                     "benzene.xyz");
      put("Methane",                     "methane.xyz");
      put("COVID-19 Main Protease",      "6lu7.xyz");
   }};

public static final String kSAMPLE_DEFAULT = "6lu7.xyz";

                                       // class variables ------------------- //
                                       // (none)                              //
                                       // public instance variables --------- //
                                       // (none)                              //
                                       // protected instance variables -------//
protected System   system;             // system                              //
protected View     view;               // view                                //
protected Renderer renderer;           // renderer                            //
protected boolean  bNeedReset;         // needs reset                         //
protected Element  renderContainer;    // render container                    //
protected double   lastX;              // last x                              //
protected double   lastY;              // last y                              //
protected boolean  buttonDown;         // true iff button is down             //
                                       // private instance variables -------- //
                                       // (none)                              //
/*------------------------------------------------------------------------------

@name       configureRenderContainer - configure render container
                                                                              */
                                                                             /**
            Configure render container

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void configureRenderContainer()
{
   renderContainer = DomGlobal.document.getElementById("render-container");
   renderContainer.addEventListener("mousedown", renderContainerMouseDownListener);
   renderContainer.addEventListener("mousemove", renderContainerMouseMoveListener);
   renderContainer.addEventListener("mouseup", renderContainerMouseUpListener);

   DomGlobal.setInterval(new SetIntervalCallbackFn()
   {
      @Override
      public void onInvoke(Object... p0)
      {
         if (!buttonDown)
         {
            DomGlobal.document.body.style.cursor = "";
         }
      }
   }, 10);

   renderContainer.addEventListener("dblclick", renderContainerDoubleClickListener);
   renderContainer.addEventListener("wheel", renderContainerWheelListener);
}
/*------------------------------------------------------------------------------

@name       configureSamplesDropDown - configure samples dropDown
                                                                              */
                                                                             /**
            Configure samples dropDown.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void configureSamplesDropDown()
{
   HTMLSelectElement selector =
      (HTMLSelectElement)DomGlobal.document.getElementById("controls-sample");

   for (String name : kSAMPLES.keySet())
   {
      HTMLOptionElement option =
         (HTMLOptionElement)DomGlobal.document.createElement("option");

      option.value     = kSAMPLES.get(name);
      option.innerHTML = name;
      option.selected  = selector.size == 0;
      selector.appendChild(option);
   }
   selector.addEventListener("change", sampleChangeHandler);
}
/*------------------------------------------------------------------------------

@name       fromXYZ - configure samples dropDown
                                                                              */
                                                                             /**
            Configure samples dropDown.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected List<List<Atom>> fromXYZ(
   String data)
{
   List<List<Atom>> trajectory = new ArrayList<>();

   String[] lines   = data.split("\n");
   int      natoms  = Integer.parseInt(lines[0]);
   int      nframes = (int)Math.floor((double)lines.length/(natoms + 2));

   for (int i = 0; i < nframes; i++)
   {
      List<Atom> atoms = new ArrayList<>();

      for(int j = 0; j < natoms; j++)
      {
         String[] tokens =
            lines[i * (natoms +2 ) + j + 2].split(
               Utilities.kREGEX_ONE_OR_MORE_WHITESPACE);

         int k = 0;
         while (tokens[k].equals(""))
         {
            k++;
         }

         atoms.add(
            new Atom(
               tokens[k++],
               Float.parseFloat(tokens[k++]),
               Float.parseFloat(tokens[k++]),
               Float.parseFloat(tokens[k++])));
      }
      trajectory.add(atoms);
   }
   return trajectory;
}
/*------------------------------------------------------------------------------

@name       handleEffect - handleEffect handler
                                                                              */
                                                                             /**
            handleEffect handler as a public instance variable.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
public INativeEffectHandler handleEffect = () ->
{
   if (getMounted())
   {
      launch();
   }
                                       // no cleanup function                 //
   return(INativeEffectHandler.kNO_CLEANUP_FCN);
};
/*------------------------------------------------------------------------------

@name       launch - launch component
                                                                              */
                                                                             /**
            Launch component.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void launch()
{
   system = System.newInstance();
   view   = View.newInstance();

   configureRenderContainer();

   Element imposterCanvas = DomGlobal.document.getElementById("renderer-canvas");

   renderer = new Renderer(imposterCanvas, view.resolution, view.aoRes);

// configureSamplesDropDown();

   loadStructure(props().getString("target"));
   //                                    // make display visible                //
   //HTMLElement speckContainer =
   //   (HTMLElement)DomGlobal.document.getElementById("speck-container");
   //
   //speckContainer.style.setProperty("display", "block");
}
/*------------------------------------------------------------------------------

@name       loadStructure - load structure
                                                                              */
                                                                             /**
            Load structure.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void loadStructure(
   String sampleData)
{
   if (sampleData != null)
   {
      system = System.newInstance();

      for (Atom atom : fromXYZ(sampleData).get(0))
      {
         System.addAtom(system, atom.symbol, atom.x, atom.y, atom.z);
      }

      System.center(system);
      System.calculateBonds(system);
      renderer.setSystem(system, view);
      View.center(view, system);

      bNeedReset = true;
                                       // start animation loop                //
      loop();
   }
};
/*------------------------------------------------------------------------------

@name       loop - animation loop
                                                                              */
                                                                             /**
            Animation loop.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
protected void loop()
{
   ((HTMLElement)DomGlobal.document.getElementById("ao-indicator"))
      .style.width =
         WidthUnionType.of(
            Math.min(100, (renderer.getAOProgress() * 100)) + "%");

   if (bNeedReset)
   {
      renderer.reset();
      bNeedReset = false;
   }
   renderer.render(view);
   DomGlobal.requestAnimationFrame(new RequestAnimationFrameCallbackFn()
   {
      public void onInvoke(double p0)
      {
         loop();
      }
   });
};
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            Render component.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes
      //<div id="controls-menu">
      //   <button id="menu-button-structure">Structure</button>
      //   <button id="menu-button-render">Render</button>
      //   <button id="menu-button-share">Share</button>
      //   <button id="menu-button-help">Help</button>
      //   <button id="menu-button-about">About</button>
      //</div>
      //
      //<div id="controls-container">
      //
      //   <div id="controls-structure">
      //
      //       <h2>Samples</h2>
      //
      //       <select id="controls-sample"></select>
      //
      //       <h2>Custom</h2>
      //
      //       Paste xyz file data:<br>
      //
      //       <textarea id="xyz-data" rows="4" columns="10"></textarea><br><br>
      //
      //       <button id="xyz-button">Load</button><br><br>
      //
      //   </div>
      //
      //   <div id="controls-render">
      //
      //       <table>
      //           <tr>
      //               <td align="right">Preset</td>
      //               <td>
      //                   <select id="view-preset">
      //                       <option value="default">Default</option>
      //                       <option value="stickball">Ball & Stick</option>
      //                       <option value="toon">Toon</option>
      //                       <option value="licorice">Licorice</option>
      //                   </select>
      //               </td>
      //           </tr>
      //           <tr><td colSpan={10}><hr></td></tr>
      //           <tr>
      //               <td align="right">Atom radius</td>
      //               <td><input id="atom-radius" type="range" min="1" max="100" value="50" class="control-width"></td>
      //               <td id="atom-radius-text"></td>
      //               <td class="control-help">a + scrollwheel</td>
      //           </tr>
      //           <tr>
      //               <td align="right">Relative atom radius</td>
      //               <td><input id="relative-atom-radius" type="range" min="0" max="100" value="100" class="control-width"></td>
      //               <td id="relative-atom-radius-text"></td>
      //               <td class="control-help">z + scrollwheel</td>
      //           </tr>
      //           <tr>
      //               <td align="right">Atom shade</td>
      //               <td><input id="atom-shade" type="range" min="0" max="100" value="0" class="control-width"></td>
      //               <td id="atom-shade-text"></td>
      //               <td class="control-help">w + scrollwheel</td>
      //           </tr>
      //           <tr><td colSpan={10}><hr></td></tr>
      //           <tr>
      //               <td align="right">Bonds</td>
      //               <td><input type="checkbox" id="bonds"></td>
      //           </tr>
      //           <tr>
      //               <td align="right">Bond radius</td>
      //               <td><input id="bond-radius" type="range" min="0" max="100" value="50" class="control-width"></td>
      //               <td id="bond-radius-text"></td>
      //               <td class="control-help">b + scrollwheel</td>
      //           </tr>
      //           <tr>
      //               <td align="right">Bond threshold</td>
      //               <td><input type="number" step="0.1" min="0.0" max="2.5" id="bond-threshold" value="1.2" class="control-width"></td>
      //           </tr>
      //           <tr>
      //               <td align="right">Bond shade</td>
      //               <td><input id="bond-shade" type="range" min="0" max="100" value="0" class="control-width"></td>
      //               <td id="bond-shade-text"></td>
      //               <td class="control-help">s + scrollwheel</td>
      //           </tr>
      //           <tr><td colSpan={10}><hr></td></tr>
      //           <tr>
      //               <td align="right">Ambient occlusion</td>
      //               <td><input id="ambient-occlusion" type="range" min="0" max="100" value="50" class="control-width"></td>
      //               <td id="ambient-occlusion-text"></td>
      //               <td class="control-help">o + scrollwheel</td>
      //           </tr>
      //           <tr>
      //               <td align="right">Brightness</td>
      //               <td><input id="brightness" type="range" min="0" max="100" value="50" class="control-width"></td>
      //               <td id="brightness-text"></td>
      //               <td class="control-help">l + scrollwheel</td>
      //           </tr>
      //           <tr>
      //               <td align="right">AO Resolution</td>
      //               <td>
      //                   <select id="ao-resolution">
      //                       <option value="16">16x16</option>
      //                       <option value="32">32x32</option>
      //                       <option value="64">64x64</option>
      //                       <option value="128">128x128</option>
      //                       <option value="256">256x256</option>
      //                       <option value="512">512x512</option>
      //                       <option value="1024">1024x1024</option>
      //                       <option value="2048">2048x2048</option>
      //                   </select>
      //               </td>
      //           </tr>
      //           <tr>
      //               <td align="right">Samples per frame</td>
      //               <td>
      //                   <select id="samples-per-frame">
      //                       <option value="0">0</option>
      //                       <option value="1">1</option>
      //                       <option value="2">2</option>
      //                       <option value="4">4</option>
      //                       <option value="8">8</option>
      //                       <option value="16">16</option>
      //                       <option value="32">32</option>
      //                       <option value="64">64</option>
      //                       <option value="128">128</option>
      //                       <option value="256">256</option>
      //                   </select>
      //               </td>
      //           </tr>
      //           <tr><td colSpan={10}><hr></td></tr>
      //           <tr>
      //               <td align="right">Depth of field strength</td>
      //               <td><input id="dof-strength" type="range" min="0" max="100" value="0" class="control-width"></td>
      //               <td id="dof-strength-text"></td>
      //               <td class="control-help">d + scrollwheel</td>
      //           </tr>
      //           <tr>
      //               <td align="right">Depth of field position</td>
      //               <td><input id="dof-position" type="range" min="0" max="100" value="0" class="control-width"></td>
      //               <td id="dof-position-text"></td>
      //               <td class="control-help">p + scrollwheel</td>
      //           </tr>
      //           <tr><td colSpan={10}><hr></td></tr>
      //           <tr>
      //               <td align="right">Outline strength</td>
      //               <td><input id="outline-strength" type="range" min="0" max="100" value="50" class="control-width"></td>
      //               <td id="outline-strength-text"></td>
      //               <td class="control-help">q + scrollwheel</td>
      //           </tr>
      //           <tr>
      //               <td align="right">Antialiasing passes</td>
      //               <td><input type="number" step="1" min="0" max="32" id="fxaa" value="1" class="control-width"></td>
      //           </tr>
      //           <tr>
      //               <td align="right">Resolution</td>
      //               <td>
      //                   <select id="resolution">
      //                       <option value="256">256x256</option>
      //                       <option value="512">512x512</option>
      //                       <option value="768">768x768</option>
      //                       <option value="1024">1024x1024</option>
      //                       <option value="1536">1536x1536</option>
      //                       <option value="2048">2048x2048</option>
      //                   </select>
      //               </td>
      //           </tr>
      //       </table>
      //
      //       <button id="center-button">Center</button>
      //
      //   </div>
      //   <div id="controls-share">
      //
      //       <button id="share-url-button">Click this</button>then share the url below.<br><br>
      //       <textarea id="share-url" cols="48" rows="8"></textarea><br><br>
      //
      //       <hr><br>
      //
      //       <a href='#' class=button id="download-image-button" download='render.png'>Download</a>as a
      //       <select id="download-image-format-selector">
      //           <option value="png">PNG</option>
      //           <option value="jpg">JPEG</option>
      //           <option value="bmp">BMP</option>
      //           <option value="gif">GIF</option>
      //           <option value="webp">WebP</option>
      //       </select>
      //       <br><br>
      //
      //   </div>
      //
      //   <div id="controls-help">
      //
      //       <p>
      //           Speck has been tested against recent Firefox and Chrome browsers. Performance
      //           appears to be significantly better on Chrome.<br><br>
      //
      //           To translate your system, use the shift key and click and drag on the rendering.
      //           To rotate, click and drag. To zoom, use the scrollwheel.
      //       </p>
      //
      //   </div>
      //
      //   <div id="controls-about">
      //
      //       <h2>Speck</h2>
      //       High quality atomistic system rendering.<br><br>
      //       Author: Rye Terrell | wwwtyro at gmail.com<br><br>
      //       Github: <a href="https://github.com/wwwtyro/speck">https://github.com/wwwtyro/speck</a>
      //       <h2>Copyright, license, etc.</h2>
      //       <p>
      //           Speck and the images it produces are completely public domain
      //           and free to use. Do with it/them what you will. Attribution
      //           is appreciated but not required.
      //       </p>
      //
      //   </div>
      //</div>
      //
      //
      //<div id="error">
      //   <div id="error-text"></div>
      //   <br>
      //   <button id="close-error">Continue</button>
      //</div>

                                                                              */
//------------------------------------------------------------------------------
public final void render()
{
   useEffect(handleEffect);
/*--
   <div id='speck-container'>
      <div id="render-container">
         <div id="ao-indicator"></div>
         <canvas id="renderer-canvas"></canvas>
      </div>
   </div>
--*/
};
/*------------------------------------------------------------------------------

@name       renderCSS - get component css
                                                                              */
                                                                             /**
            Get component css.

            This #speck-container and #render-container setup so #render-canvas
            is the larget square to fit the container width.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public void renderCSS()
{
/*--
#speck-container
{
   margin:              0px;
   box-sizing:          border-box;
   max-width:           100%;
   height:              calc(100vh - 16px);
   resize:              horizontal;
   overflow:            hidden;
   font-family:         'Open Sans', sans-serif;
   font-size:           16px;
   -webkit-user-select: none;
   background:          url('images/noise.png');
   background-color:    rgba(255,255,255,0.5);

}

#render-container
{
   position:       fixed;
   width:          100%;
   padding-bottom: 100%;
   background:     radial-gradient(#fff, #aaa);
}

#renderer-canvas
{
   width:  100%;
   height: 100%;
}

#controls-container
{
   position: fixed;
   overflow-y: auto;
   width: 550px;
}

#controls-menu
{
   position: fixed;
}

#xyz-data
{
   width: 384px;
   height: 128px;
}

#ao-indicator
{
   border-top: 4px solid #3bf;
   top: 0px;
   left: 0px;
}

#error
{
   position: fixed;
   top: 0px;
   left: 0px;
   width: 100%;
   height: 100%;
   background: url('noise.png');
   font-family: 'Open Sans', sans-serif;
   font-size: 16px;
   padding: 64px;
   display: none;
}

.control-help
{
   font-style: italic;
   color: #555;
   text-align: right;
   font-family: 'Ubuntu Mono';
}

.control-width
{
   width: 128px;
}

textarea
{
   outline: none;
}

button
{
   font-family: 'Open Sans', sans-serif;
   font-size: 16px;
   border-radius: 4px;
   background: #bbb;
   border: none;
   margin-right: 8;
   padding: 4 8 4 8;
   outline: none;
}

a.button
{
   font-family: 'Open Sans', sans-serif;
   font-size: 16px;
   border-radius: 4px;
   background: #bbb;
   border: none;
   margin-right: 8;
   padding: 4 8 4 8;
   outline: none;
   text-decoration: none;
   color: #000000;
}

select
{
   font-family: 'Open Sans', sans-serif;
   font-size: 16px;
   outline: none;
}

input
{
   font-family: 'Open Sans', sans-serif;
   font-size: 16px;
   outline: none;
}

table
{
   font-family: 'Open Sans', sans-serif;
   font-size: 16px;
   border-collapse: collapse;
}

td
{
   padding-right: 8px;
   padding-bottom: 4px;
   padding-top: 4px;
}

h2
{
   margin-top: 32px;
   margin-left: 0px;
}

p
{
   width: 512px;
}
--*/
}
/*------------------------------------------------------------------------------

@name       renderContainerDoubleClickListener - doubleClick listener
                                                                              */
                                                                             /**
            renderContainer doubleClick listener.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public EventListener renderContainerDoubleClickListener = (Event e) ->
{
   View.center(view, system);
   bNeedReset = true;
};
/*------------------------------------------------------------------------------

@name       renderContainerMouseDownListener - mouseDown listener
                                                                              */
                                                                             /**
            renderContainer mouseDown listener.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public EventListener renderContainerMouseDownListener = (Event e) ->
{
   MouseEvent mouseEvent = (MouseEvent)e;
   DomGlobal.document.body.style.cursor = "none";
   if (mouseEvent.button == 0)
   {
      buttonDown = true;
   }

   lastX = mouseEvent.clientX;
   lastY = mouseEvent.clientY;
};
/*------------------------------------------------------------------------------

@name       renderContainerMouseMoveListener - mouseMove listener
                                                                              */
                                                                             /**
            renderContainer mouseMove listener.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public EventListener renderContainerMouseMoveListener = (Event e) ->
{
   if (buttonDown)
   {
      MouseEvent mouseEvent = (MouseEvent)e;

      double dx = mouseEvent.clientX - lastX;
      double dy = mouseEvent.clientY - lastY;
      if (dx != 0 || dy != 0)
      {
         lastX = mouseEvent.clientX;
         lastY = mouseEvent.clientY;

         if (mouseEvent.shiftKey)
         {
            View.translate(view, dx, dy);
         }
         else
         {
            View.rotate(view, dx, dy);
         }

         bNeedReset = true;
      }
   }
};
/*------------------------------------------------------------------------------

@name       renderContainerMouseUpListener - renderContainer mouseUp listener
                                                                              */
                                                                             /**
            renderContainer mouseUp listener.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public EventListener renderContainerMouseUpListener = (Event e) ->
{
   MouseEvent mouseEvent = (MouseEvent)e;
   DomGlobal.document.body.style.cursor = "";
   if (mouseEvent.button == 0)
   {
      buttonDown = false;
   }
};
/*------------------------------------------------------------------------------
å
@name       renderContainerWheelListener - renderContainer wheel listener
                                                                              */
                                                                             /**
            renderContainer wheel listener.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public EventListener renderContainerWheelListener = (Event e) ->
{
   view.zoom *= (((WheelEvent)e).deltaY < 0 ? 1/0.9 : 0.9);
   View.resolve(view);
   bNeedReset = true;

   e.preventDefault();
};
/*------------------------------------------------------------------------------

@name       sampleChangeHandler - sample change handler
                                                                              */
                                                                             /**
            Sample change handler.

@history    Thu Jun 25, 2020 10:30:00 (Giavaneers - LBM) created

@notes

                                                                              */
//------------------------------------------------------------------------------
public EventListener sampleChangeHandler = (Event e) ->
{
   //loadTarget();
};
}//====================================// end Speck ==========================//
