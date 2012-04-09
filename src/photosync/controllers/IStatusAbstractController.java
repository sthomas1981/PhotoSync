package photosync.controllers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import photosync.models.PhotoSyncModels;
import photosync.views.IStatusReportable;

public abstract class IStatusAbstractController extends AbstractAction {

	private static final long serialVersionUID = -7393672854475430425L;

	protected PhotoSyncModels photoSyncModels;
	protected IStatusReportable coreComponents;

	public IStatusAbstractController(final String text, final PhotoSyncModels iModels, final IStatusReportable iCoreComponent) {
		super(text);
		photoSyncModels = iModels;
		coreComponents = iCoreComponent;
	}

	@Override
	public abstract void actionPerformed(final ActionEvent e);
}
