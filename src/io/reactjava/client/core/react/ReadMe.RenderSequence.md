The old render sequence depends on JSXTransform and is as follows:

1. The router finds the appropriate component class for the current route.

   Class componentClass = getComponentClassForRoute();

2. The current configuration is added as an element of a new Properties instance.

3. A function to construct an instance of the componentClass is found
   from the global resources map. This function is invoked passing the new
   Properties instance as an argument.

4. The properties of the new component instance are retrieved.

5. A function to generate a renderable component for the componentClass is found
   from the global resources map. This function takes a properties instance as
   an argument.

6. This function is invoked, passing the new component instance properties as an
   argument. The result is the target element to be rendered.

7. The Router finally invokes the ReactDOM.render() method, passing the target
   element to be rendered as an argument.


The new render sequence depends on JSXTransformStandalone and is as follows:

1. The router finds the appropriate component class for the current route.

   Class componentClass = getComponentClassForRoute();

2. The current configuration is added as an element of a new Properties instance.

3. A function to construct an instance of the componentClass is found
   from the global resources map. This function is invoked passing the new
   Properties instance as an argument.

4. The render() method of the instance is invoked, which assigns the target
   element to be rendered as its 'renderElement' instance variable.

5. The Router finally invokes the ReactDOM.render() method, passing the target
   element to be rendered as an argument.

