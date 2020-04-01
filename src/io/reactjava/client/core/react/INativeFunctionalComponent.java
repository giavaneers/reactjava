/*==============================================================================

name:       INativeFunctionalComponent - native functional component

purpose:    Defines a stateless ReactJava component expressed as a plain function.
            This simplified component API is intended for components that are
            pure functions of their props. These components must not retain
            internal state, do not have backing instances, and do not have the
            component lifecycle methods. They are pure functional transforms of
            their input, with zero boilerplate.

            In ReactJava, all components should be stateless functions because
            in the future ReactJava will be able to make performance
            optimizations specific to these components by avoiding unnecessary
            checks and memory allocations. This is the recommended pattern,
            when possible.

param:      <P>     the type of props this component expects

history:    Mon Aug 28, 2017 10:30:00 (Giavaneers - LBM) created

notes:
                        COPYRIGHT (c) BY GIAVANEERS, INC.
         This source code is licensed under the MIT license found in the
             LICENSE file in the root directory of this source tree.

==============================================================================*/
                                       // package --------------------------- //
package io.reactjava.client.core.react;
                                       // imports --------------------------- //
import jsinterop.annotations.JsFunction;
                                        // INativeRenderableComponent ========//
@JsFunction
public interface INativeFunctionalComponent<P extends Properties>
{
/*------------------------------------------------------------------------------

@name       render - render component
                                                                              */
                                                                             /**
            The render() method is required.

            When called, it should examine props return a single child element.
            This child element can be either a virtual representation of a
            native ReactDOM component (such as ReactJava.ReactDOM.div()) or
            another composite component that you've definedyourself.

            You can also return null to indicate that you don't want anything
            rendered. Behind the scenes, ReactJava renders a <tag> to work with
            our current diffing algorithm.

            The render() function should be pure, meaning that it does not
            modify componen state, it returns the same result each time it's
            invoked, and it does not read from r write to the ReactDOM or
            otherwise interact with the browser (e.g., by using setTimeout).

@return     a single ReactJava element, or null if no rendering is desired.

@param      props      properties for this component

@history    Sat May 13, 2018 10:30:00 (Giavaneers - LBM) created

@notes
                                                                              */
//------------------------------------------------------------------------------
ReactElement render(P props);
}
