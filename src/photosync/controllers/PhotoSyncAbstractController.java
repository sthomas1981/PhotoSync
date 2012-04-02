package photosync.controllers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import photosync.models.PhotoSyncModel;
import photosync.views.IComponentReachable;

public abstract class PhotoSyncAbstractController extends AbstractAction {

	private static final long serialVersionUID = 4120542902697372031L;

	protected PhotoSyncModel photoSyncModel;
	protected IComponentReachable panel;

	public PhotoSyncAbstractController(final String iText, final PhotoSyncModel iModel, final IComponentReachable iPanel) {
		super(iText);
		photoSyncModel = iModel;
		panel = iPanel;
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

}
