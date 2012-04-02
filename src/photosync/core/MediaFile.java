package photosync.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import photosync.com.exceptions.CreationDateNotFoundException;

public abstract class MediaFile extends File implements IMediaSynchronizable {

	private static Logger logger = Logger.getLogger(MediaFile.class);

	private static final long serialVersionUID = -8348802512853124319L;

	protected String md5SumHash;
	protected Date creationDate;
	protected String extension;

	public MediaFile(final String pathname) {
		super(pathname);
		extension = getName().substring(getName().lastIndexOf('.') + 1, getName().length()).toUpperCase();
	}

	@Override
	public final void computeHash() throws IOException {
		logger.debug("Compute md5sum");
		FileInputStream fis = new FileInputStream(this);
		md5SumHash = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
	}

	@Override
	public abstract void computeCreationDate() throws CreationDateNotFoundException;

	@Override
	public final void writeToFileSystem(final String outputDir, final DateFormat subDirFormat) throws IOException {
		FileUtils.copyFile(this, new File(outputDir + File.separator + subDirFormat.format(creationDate) + File.separator + md5SumHash + "." + extension));
	}

	public final String getHash() {
		return md5SumHash;
	}

	public final Date getCreationDate() {
		return creationDate;
	}

	public final String getExtension() {
		return extension;
	}


}
