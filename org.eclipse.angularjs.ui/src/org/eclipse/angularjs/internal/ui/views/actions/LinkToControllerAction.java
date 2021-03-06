/**
 *  Copyright (c) 2013-2014 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.angularjs.internal.ui.views.actions;

import org.eclipse.angularjs.core.link.AngularLinkHelper;
import org.eclipse.angularjs.internal.ui.AngularUIMessages;
import org.eclipse.angularjs.internal.ui.ImageResource;
import org.eclipse.angularjs.internal.ui.views.AngularContentOutlinePage;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

import tern.TernResourcesManager;
import tern.angular.AngularType;
import tern.angular.modules.IAngularElement;
import tern.angular.modules.IModule;

/**
 * This action link the selected controller to the current HTML/JSP file opened
 * in a editor.
 * 
 */
public class LinkToControllerAction extends Action {

	private final AngularContentOutlinePage page;

	public LinkToControllerAction(AngularContentOutlinePage page) {
		this.page = page;
		super.setText(AngularUIMessages.LinkToControllerAction_text);
		super.setToolTipText(AngularUIMessages.LinkToControllerAction_tooltip);
		super.setImageDescriptor(ImageResource.getImageDescriptor(ImageResource.IMG_ELCL_LINK_TO_CTRL));
	}

	@Override
	public void run() {
		IResource resource = page.getCurrentFile();
		IStructuredSelection selection = (IStructuredSelection) page.getViewer().getSelection();
		if (!selection.isEmpty()) {
			Object firstSelection = selection.getFirstElement();
			if (firstSelection instanceof IAngularElement) {
				IAngularElement element = (IAngularElement) firstSelection;
				IModule module = element.getModule();
				if (module != null) {
					try {
						AngularLinkHelper.setController(module.getName(),
								!element.isType(AngularType.module) ? element.getName() : null, resource, null);
						page.updateEnabledLinkActions(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
