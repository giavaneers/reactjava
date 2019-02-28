/*
 * Copyright 2009 Google Inc.
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
 * The enhancement to this program was created by Giavaneers
 * and is the proprietary product of Giavaneers Inc.
 *
 * COPYRIGHT (c) BY GIAVANEERS, INC.
 * This source code enhancement is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.google.gwt.dev.javac;

import com.google.gwt.dev.jjs.ast.JDeclaredType;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.dev.util.Util;

import java.util.Map;
import org.eclipse.jdt.core.compiler.CategorizedProblem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

/**
 * Builds a {@link com.google.gwt.dev.javac.CompilationUnit}.
 */
public abstract class CompilationUnitBuilder {

  static class GeneratedCompilationUnitBuilder extends com.google.gwt.dev.javac.CompilationUnitBuilder {
    private final com.google.gwt.dev.javac.GeneratedUnit generatedUnit;

    public GeneratedCompilationUnitBuilder(com.google.gwt.dev.javac.GeneratedUnit generatedUnit) {
      this.generatedUnit = generatedUnit;
    }

    @Override
    public com.google.gwt.dev.javac.ContentId getContentId() {
      return new com.google.gwt.dev.javac.ContentId(getTypeName(), generatedUnit.getStrongHash());
    }

    @Override
    public String getLocation() {
      return getLocationFor(generatedUnit);
    }

    @Override
    public String getSourceMapPath() {
      return generatedUnit.getSourceMapPath();
    }

    @Override
    public String getTypeName() {
      return generatedUnit.getTypeName();
    }

    @Override
    protected String doGetSource() {
      return generatedUnit.getSource();
    }

    @Override
    protected com.google.gwt.dev.javac.CompilationUnit makeUnit(
       java.util.List<CompiledClass> compiledClasses,
       java.util.List<com.google.gwt.dev.jjs.ast.JDeclaredType> types, com.google.gwt.dev.javac.Dependencies dependencies,
       java.util.Collection<? extends JsniMethod> jsniMethods, com.google.gwt.dev.javac.MethodArgNamesLookup methodArgs,
       org.eclipse.jdt.core.compiler.CategorizedProblem[] problems) {
      return new com.google.gwt.dev.javac.CompilationUnitBuilder.GeneratedCompilationUnit(generatedUnit, compiledClasses, types, dependencies,
          jsniMethods, methodArgs, problems);
    }
  }

  static class ResourceCompilationUnitBuilder extends com.google.gwt.dev.javac.CompilationUnitBuilder {
    /**
     * Not valid until source has been read.
     */
    private com.google.gwt.dev.javac.ContentId contentId;

    private long lastModifed = -1;

    private final com.google.gwt.dev.resource.Resource resource;

    private final String typeName;

/*LBM-START*/
    private final Map<String,String> components;
    private final com.google.gwt.core.ext.TreeLogger logger;

    //private ResourceCompilationUnitBuilder(com.google.core.dev.resource.Resource resource) {
    private ResourceCompilationUnitBuilder(com.google.gwt.dev.resource.Resource resource, Map<String,String> components, com.google.gwt.core.ext.TreeLogger logger) {
/*LBM-END*/
      this.typeName   = com.google.gwt.dev.javac.Shared.toTypeName(resource.getPath());
      this.resource   = resource;
/*LBM-START*/
      this.components = components;
      this.logger     = logger;
/*LBM-END*/
    }

    @Override
    public com.google.gwt.dev.javac.ContentId getContentId() {
      if (contentId == null) {
        getSource();
      }
      return contentId;
    }

    public long getLastModified() {
      if (lastModifed < 0) {
        return resource.getLastModified();
      } else {
        // Value when the source was actually read.
        return lastModifed;
      }
    }

    @Override
    public String getLocation() {
      return resource.getLocation();
    }

    public com.google.gwt.dev.resource.Resource getResource() {
      return resource;
    }

    @Override
    public String getSourceMapPath() {
      return getSourceMapPathFor(resource);
    }

    @Override
    public String getTypeName() {
      return typeName;
    }

    @Override
    protected String doGetSource() {
      /*
       * Pin the mod date first to be conservative, we'd rather a unit be seen
       * as too stale than too fresh.
       */
      lastModifed = resource.getLastModified();
      java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream(1024);
      try {
        java.io.InputStream in = resource.openContents();
        /**
         * In most cases openContents() will throw an exception, however in the case of a
         * ZipFileResource it might return null causing an NPE in Util.copyNoClose(),
         * see issue 4359.
         */
        if (in == null) {
          throw new RuntimeException("Unexpected error reading resource '" + resource + "'");
        }
        com.google.gwt.dev.util.Util.copy(in, out);
      } catch (java.io.IOException e) {
        throw new RuntimeException("Unexpected error reading resource '" + resource + "'", e);
      }
      byte[] content = out.toByteArray();
/*LBM-START*/
      try
      {
        content = io.reactjava.codegenerator.IPreprocessor.allPreprocessors(
           getTypeName(), content, com.google.gwt.dev.util.Util.DEFAULT_ENCODING, components, logger);
      }
      catch(Exception e)
      {
        throw new RuntimeException("Unexpected error transforming resource '" + resource + "'", e);
      }
/*LBM-END*/
      contentId = new com.google.gwt.dev.javac.ContentId(
         getTypeName(),
         com.google.gwt.dev.util.Util.computeStrongName(content));
      return com.google.gwt.dev.util.Util.toString(content);
    }

    @Override
    protected com.google.gwt.dev.javac.CompilationUnit makeUnit(
       java.util.List<CompiledClass> compiledClasses,
       java.util.List<com.google.gwt.dev.jjs.ast.JDeclaredType> types, com.google.gwt.dev.javac.Dependencies dependencies,
       java.util.Collection<? extends JsniMethod> jsniMethods, com.google.gwt.dev.javac.MethodArgNamesLookup methodArgs,
       org.eclipse.jdt.core.compiler.CategorizedProblem[] problems) {
      return new com.google.gwt.dev.javac.SourceFileCompilationUnit(getResource(), getContentId(), compiledClasses, types,
          dependencies, jsniMethods, methodArgs, problems, getLastModified());
    }
  }

  static final class GeneratedCompilationUnit extends com.google.gwt.dev.javac.CompilationUnitImpl {
    private final com.google.gwt.dev.javac.GeneratedUnit generatedUnit;

    public GeneratedCompilationUnit(
       com.google.gwt.dev.javac.GeneratedUnit generatedUnit,
       java.util.List<CompiledClass> compiledClasses, java.util.List<com.google.gwt.dev.jjs.ast.JDeclaredType> types, com.google.gwt.dev.javac.Dependencies dependencies,
       java.util.Collection<? extends JsniMethod> jsniMethods, com.google.gwt.dev.javac.MethodArgNamesLookup methodArgs,
       org.eclipse.jdt.core.compiler.CategorizedProblem[] problems) {
      super(compiledClasses, types, dependencies, jsniMethods, methodArgs, problems);
      this.generatedUnit = generatedUnit;
    }

    @Override
    public com.google.gwt.dev.javac.CachedCompilationUnit asCachedCompilationUnit() {
      return new com.google.gwt.dev.javac.CachedCompilationUnit(this, astToken);
    }

    @Override
    public long getLastModified() {
      return generatedUnit.creationTime();
    }

    @Override
    public String getResourceLocation() {
      return getLocationFor(generatedUnit);
    }

    @Override
    public String getResourcePath() {
      return com.google.gwt.dev.javac.Shared.toPath(generatedUnit.getTypeName());
    }

    @Override
    public String getTypeName() {
      return generatedUnit.getTypeName();
    }

    @Deprecated
    @Override
    public boolean isGenerated() {
      return true;
    }

    @Deprecated
    @Override
    public boolean isSuperSource() {
      return false;
    }

    @Override
    com.google.gwt.dev.javac.ContentId getContentId() {
      return new com.google.gwt.dev.javac.ContentId(getTypeName(), generatedUnit.getStrongHash());
    }

    String getSource() {
      return generatedUnit.getSource();
    }
  }

  public static com.google.gwt.dev.javac.CompilationUnitBuilder create(com.google.gwt.dev.javac.GeneratedUnit generatedUnit) {
    return new com.google.gwt.dev.javac.CompilationUnitBuilder.GeneratedCompilationUnitBuilder(generatedUnit);
  }

/*LBM-START*/
  public static com.google.gwt.dev.javac.CompilationUnitBuilder create(com.google.gwt.dev.resource.Resource resource, Map<String,String> components, com.google.gwt.core.ext.TreeLogger logger) {
    //return new com.google.core.dev.javac.CompilationUnitBuilder.ResourceCompilationUnitBuilder(resource);
    return new com.google.gwt.dev.javac.CompilationUnitBuilder.ResourceCompilationUnitBuilder(resource, components, logger);
/*LBM-END*/
  }

  /**
   * Given a resource, returns the filename that will appear in the source map.
   */
  public static String getSourceMapPathFor(com.google.gwt.dev.resource.Resource resource) {
    return resource.getPathPrefix() + resource.getPath();
  }

  static String getLocationFor(com.google.gwt.dev.javac.GeneratedUnit generatedUnit) {
    String location = generatedUnit.optionalFileLocation();
    if (location != null) {
      return location;
    }
    return "generated://" + generatedUnit.getStrongHash() + "/"
        + com.google.gwt.dev.javac.Shared.toPath(generatedUnit.getTypeName());
  }

  private java.util.List<CompiledClass> compiledClasses;
  private com.google.gwt.dev.javac.Dependencies dependencies;
  private java.util.Collection<? extends JsniMethod> jsniMethods;
  private com.google.gwt.dev.javac.MethodArgNamesLookup methodArgs;
  private org.eclipse.jdt.core.compiler.CategorizedProblem[] problems;

  /**
   * Caches source until JSNI methods can be collected.
   */
  private transient String source;

  private java.util.List<com.google.gwt.dev.jjs.ast.JDeclaredType> types;

  protected CompilationUnitBuilder() {
  }

  public com.google.gwt.dev.javac.CompilationUnit build() {
    // Free the source now.
    source = null;
    assert compiledClasses != null;
    assert types != null;
    assert dependencies != null;
    assert jsniMethods != null;
    assert methodArgs != null;
    return makeUnit(compiledClasses, types, dependencies, jsniMethods, methodArgs, problems);
  }

  public abstract com.google.gwt.dev.javac.ContentId getContentId();

  /**
   * Returns the location that should appear in JDT error messages.
   */
  public abstract String getLocation();

  public String getSource() {
    if (source == null) {
      source = doGetSource();
    }
    return source;
  }

  /**
   * Returns the location for this resource as it should appear in a sourcemap.
   * For a regular source file, it should be a path relative to one of the classpath entries
   * from the ResourceLoader. For generated files, it should be "gen/" followed by the path where
   * the source file would show up in the generated files directory if the "-gen" compiler option
   * were enabled.
   */
  public abstract String getSourceMapPath();

  /**
   * Returns the type source name.
   */
  public abstract String getTypeName();

  public com.google.gwt.dev.javac.CompilationUnitBuilder setClasses(java.util.List<CompiledClass> compiledClasses) {
    this.compiledClasses = compiledClasses;
    return this;
  }

  public com.google.gwt.dev.javac.CompilationUnitBuilder setDependencies(com.google.gwt.dev.javac.Dependencies dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public com.google.gwt.dev.javac.CompilationUnitBuilder setJsniMethods(java.util.Collection<? extends JsniMethod> jsniMethods) {
    this.jsniMethods = jsniMethods;
    return this;
  }

  public com.google.gwt.dev.javac.CompilationUnitBuilder setMethodArgs(com.google.gwt.dev.javac.MethodArgNamesLookup methodArgs) {
    this.methodArgs = methodArgs;
    return this;
  }

  public com.google.gwt.dev.javac.CompilationUnitBuilder setProblems(org.eclipse.jdt.core.compiler.CategorizedProblem[] problems) {
    this.problems = problems;
    return this;
  }

  public com.google.gwt.dev.javac.CompilationUnitBuilder setTypes(java.util.List<com.google.gwt.dev.jjs.ast.JDeclaredType> types) {
    this.types = types;
    return this;
  }

  @Override
  public final String toString() {
    return getLocation();
  }

  protected abstract String doGetSource();

  protected abstract com.google.gwt.dev.javac.CompilationUnit makeUnit(
     java.util.List<CompiledClass> compiledClasses,
     java.util.List<com.google.gwt.dev.jjs.ast.JDeclaredType> types, com.google.gwt.dev.javac.Dependencies dependencies,
     java.util.Collection<? extends JsniMethod> jsniMethods, com.google.gwt.dev.javac.MethodArgNamesLookup methodArgs,
     org.eclipse.jdt.core.compiler.CategorizedProblem[] errors);
}
