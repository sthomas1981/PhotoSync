package photosync.controllers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import photosync.models.PhotoSyncModels;
import photosync.views.ICoreComponentsReachable;

public abstract class CoreAbstractController extends AbstractAction {

	private static final long serialVersionUID = 5684547962307478685L;

	protected PhotoSyncModels photoSyncModels;
	protected ICoreComponentsReachable coreComponents;

	public CoreAbstractController(final String text, final PhotoSyncModels iModels, final ICoreComponentsReachable iCoreComponent) {
		super(text);
		photoSyncModels = iModels;
		coreComponents = iCoreComponent;
	}

	@Override
	public abstract void actionPerformed(final ActionEvent e);

}
