package photosync.controllers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import photosync.models.PhotoSyncModels;
import photosync.views.IComponentReachable;

public abstract class PhotoSyncAbstractController extends AbstractAction {

	private static final long serialVersionUID = 4120542902697372031L;

	protected PhotoSyncModels photoSyncModels;
	protected IComponentReachable panel;

	public PhotoSyncAbstractController(final String iText, final PhotoSyncModels iModel, final IComponentReachable iPanel) {
		super(iText);
		photoSyncModels = iModel;
		panel = iPanel;
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

}
