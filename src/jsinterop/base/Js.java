/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package jsinterop.base;

import static jsinterop.base.InternalPreconditions.checkType;

import javaemul.internal.annotations.DoNotAutobox;
import javaemul.internal.annotations.HasNoSideEffects;
import javaemul.internal.annotations.UncheckedCast;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;

/**
 * Utilities to provide access to JavaScript language constructs that are not available in pure
 * Java.
 *
 * <p>Note that this class avoids providing replacements for stuff that is already available via a
 * pure Java or Elemental and enforce safe (runtime-checked) coding practices.
 */
public final class Js {
  @JsProperty(namespace = "<window>", name = "undefined")
  public static native Object undefined();

  @JsProperty(namespace = "<window>", name = "arguments")
  public static native JsArrayLike<Object> arguments();

  @JsProperty(namespace = "<window>", name = "debugger")
  public static native void debugger();

  @JsMethod(namespace = "<window>")
  @HasNoSideEffects
  public static native String typeof(Object obj);

  public static <T> JsConstructorFn<T> asConstructorFn(Class<T> clazz) {
    JsConstructorFn<T> fn = InternalJsUtil.toCtor(clazz);
    checkType(fn != null);
    return fn;
  }

  public static Any asAny(@DoNotAutobox Object obj) {
    return uncheckedCast(obj);
  }

  /** Returns {@code JsPropertyMap} view of provided object. */
  public static JsPropertyMap<Object> asPropertyMap(Object obj) {
    return uncheckedCast(obj);
  }

  /** Returns {@code JsArrayLike} view of provided array-like object. */
  public static JsArrayLike<Object> asArrayLike(Object obj) {
    // TODO(goktug): switch to custom $isInstance
    checkType(obj == null || InternalJsUtil.hasLength(obj));
    return uncheckedCast(obj);
  }

  public static Any[] asArray(Object obj) {
    checkType(obj instanceof Any[]);
    return uncheckedCast(obj);
  }

  @SuppressWarnings("ReferenceEquality") // GWT is not good at optimizing equals
  public static String asString(Object obj) {
    checkType(Js.typeof(obj) == "string");
    return uncheckedCast(obj);
  }

  @SuppressWarnings("ReferenceEquality") // GWT is not good at optimizing equals
  public static boolean asBoolean(Object obj) {
    checkType(Js.typeof(obj) == "boolean");
    return InternalJsUtil.asBoolean(obj);
  }

  @SuppressWarnings("ReferenceEquality") // GWT is not good at optimizing equals
  public static double asDouble(Object obj) {
    checkType(Js.typeof(obj) == "number");
    return InternalJsUtil.asDouble(obj);
  }

  public static float asFloat(Object obj) {
    return (float) asDouble(obj);
  }

  public static long asLong(Object obj) {
    checkType(InternalJsUtil.isLong(obj));
    return InternalJsUtil.asLong(obj);
  }

  public static int asInt(Object obj) {
    checkType(InternalJsUtil.isInt(obj));
    return InternalJsUtil.asInt(obj);
  }

  public static short asShort(Object obj) {
    int num = asInt(obj);
    checkType(num == ((short) num));
    return InternalJsUtil.asShort(obj);
  }

  public static char asChar(Object obj) {
    int num = asInt(obj);
    checkType(num == ((char) num));
    return InternalJsUtil.asChar(obj);
  }

  public static byte asByte(Object obj) {
    int num = asInt(obj);
    checkType(num == ((byte) num));
    return InternalJsUtil.asByte(obj);
  }

  /**
   * Performs checked cast to lefthand-side type. This is useful for cases when Java won't allow you
   * otherwise, like casting from a native interface to a final Java type (like String).
   */
  @SuppressWarnings({"TypeParameterUnusedInFormals", "unchecked"})
  public static <T> T cast(Object obj) {
    return (T) obj;
  }

  /**
   * Performs unchecked cast to lefthand-side type. You should always prefer regular casting over
   * this (unless you know what you are doing!).
   */
  @UncheckedCast
  @SuppressWarnings({"TypeParameterUnusedInFormals", "unchecked"})
  public static <T> T uncheckedCast(Object obj) {
    return (T) obj;
  }

  public static boolean isTruthy(Object obj) {
    return !isFalsy(obj);
  }

  //J2CL_ONLY @JsMethod
  @HasNoSideEffects
  public static native boolean isFalsy(Object obj) /*-{
    return !obj;
  }-*/;

  /**
   * Returns {@code true} if two objects are same.
   *
   * <p>This method mostly behaves similar to Java {@code ==} operator except that it doesn't return
   * {@code true} for {@code null==undefined} comparison.
   */
  //J2CL_ONLY @JsMethod
  @HasNoSideEffects
  public static native boolean isTripleEqual(Object o1, Object o2) /*-{
    return o1 === o2;
  }-*/;

  /** Coerces any object to number using {@code +} operation. */
  //J2CL_ONLY @JsMethod
  public static native double coerceToDouble(Object d) /*-{
    return +d;
  }-*/;

  /** Coerces any object to 32 bit signed number using {@code |0} operation. */
  //J2CL_ONLY @JsMethod
  public static int coerceToInt(@DoNotAutobox Object d) {
    return InternalJsUtil.asInt(d) | 0;
  }

  private Js() {} // Hide constructor for utility class.
}
