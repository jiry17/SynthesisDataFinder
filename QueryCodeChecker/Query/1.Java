// 
/**
 * ***************************************************************************** Copyright (c) 2000,
 * 2013 IBM Corporation and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: IBM Corporation - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.java.server.jdt.refactoring;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ISharableParticipant;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.junit.Assert;

public class TestDeleteParticipantShared extends DeleteParticipant implements ISharableParticipant {

  static TestDeleteParticipantShared fgInstance;

  List fElements = new ArrayList(3);
  List fHandles = new ArrayList(3);
  List fArguments = new ArrayList(3);

  public boolean initialize(Object element) {
    fgInstance = this;
    fElements.add(element);
    fArguments.add(getArguments());
    if (element instanceof IJavaElement)
      fHandles.add(((IJavaElement) element).getHandleIdentifier());
    else fHandles.add(((IResource) element).getFullPath().toString());
    return true;
  }

  public void addElement(Object element, RefactoringArguments args) {
    fElements.add(element);
    fArguments.add(args);
    if (element instanceof IJavaElement)
      fHandles.add(((IJavaElement) element).getHandleIdentifier());
    else fHandles.add(((IResource) element).getFullPath().toString());
  }

  public String getName() {
    return getClass().getName();
  }

  public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context) {
    return new RefactoringStatus();
  }

  public Change createChange(IProgressMonitor pm) throws CoreException {
    return null;
  }

  public static void testNumberOfElements(int expected) {
    if (expected == 0) {
      Assert.assertTrue(fgInstance == null);
    } else {
      Assert.assertEquals(expected, fgInstance.fElements.size());
      Assert.assertEquals(expected, fgInstance.fArguments.size());
    }
  }

  public static void reset() {
    fgInstance = null;
  }

  public static boolean isLoaded() {
    return fgInstance != null;
  }
}
