package photosync.com;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;

public class XMLFilter extends FileFilter implements FilenameFilter {

	@Override
	public final boolean accept(final File f) {
		String filename = f.getName();
		return filename.endsWith(".xml");
	}

	@Override
	public final String getDescription() {
		return "*.xml";
	}

	@Override
	public final boolean accept(final File dir, final String name) {
		String filename = dir + File.separator + name;
		return filename.endsWith(".xml");
	}

}
