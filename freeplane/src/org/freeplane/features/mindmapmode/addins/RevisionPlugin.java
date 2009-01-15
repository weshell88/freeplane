/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2008 Joerg Mueller, Daniel Polansky, Christian Foltin, Dimitry Polivaev
 *
 *  This file is modified by Dimitry Polivaev in 2008.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.freeplane.features.mindmapmode.addins;

import java.awt.Color;

import org.freeplane.core.addins.NodeHookDescriptor;
import org.freeplane.core.addins.PersistentNodeHook;
import org.freeplane.core.extension.IExtension;
import org.freeplane.core.modecontroller.INodeChangeListener;
import org.freeplane.core.modecontroller.ModeController;
import org.freeplane.core.modecontroller.NodeChangeEvent;
import org.freeplane.core.model.NodeModel;
import org.freeplane.core.ui.ActionDescriptor;
import org.freeplane.features.common.nodestyle.NodeStyleController;
import org.freeplane.features.mindmapmode.MModeController;
import org.freeplane.features.mindmapmode.nodestyle.MNodeStyleController;

/**
 * @author foltin
 */
@NodeHookDescriptor(hookName = "accessories/plugins/RevisionPlugin.properties")
@ActionDescriptor(name = "accessories/plugins/RevisionPlugin.properties_name")
public class RevisionPlugin extends PersistentNodeHook implements INodeChangeListener {
	public RevisionPlugin(final ModeController modeController) {
		super(modeController);
	}

	@Override
	protected void add(final NodeModel node, final IExtension extension) {
		super.add(node, extension);
		getModeController().getMapController().addNodeChangeListener(this);
	}

	public void nodeChanged(final NodeChangeEvent event) {
		NodeModel node = event.getNode();
		if(!isActive(node)){
			return;
		}
		if (event.getProperty().equals(NodeModel.NODE_TEXT)
		        && !((MModeController) getModeController()).isUndoAction()) {
			final MNodeStyleController nodeStyleController = (MNodeStyleController) NodeStyleController
			    .getController(getModeController());
			nodeStyleController.setBackgroundColor(event.getNode(), Color.YELLOW);
		}
	}

	@Override
	protected void remove(final NodeModel node, final IExtension extension) {
		getModeController().getMapController().removeNodeChangeListener(this);
		super.remove(node, extension);
	}
}
