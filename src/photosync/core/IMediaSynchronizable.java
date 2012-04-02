package photosync.core;

import java.io.IOException;
import java.text.DateFormat;

import photosync.com.exceptions.CreationDateNotFoundException;

public interface IMediaSynchronizable {

	void computeHash() throws  IOException;
	void computeCreationDate() throws CreationDateNotFoundException;
	void writeToFileSystem(final String outputDir, final DateFormat subDirFormat) throws IOException;

}
