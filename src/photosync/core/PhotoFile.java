package photosync.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import org.json.JSONObject;

import photosync.com.exceptions.CreationDateNotFoundException;

public class PhotoFile extends MediaFile implements IMediaSynchronizable {

	private static final long serialVersionUID = 7282579663806284515L;

	private static final List<String> attributeCreationDateList =
			Arrays.asList("FileModifyDate", "ModifyDate", "DateTimeOriginal",
					"CreateDate", "SubSecCreateDate", "SubSecDateTimeOriginal", "SubSecModifyDate");

	private static final String EXIFTOOL = "exiftool";
	private static final String EXIFTOOL_PARAM = "-j";

	public PhotoFile(final String pathname) {
		super(pathname);
	}

	@Override
	public void computeCreationDate() throws CreationDateNotFoundException {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process;
			process = runtime.exec(EXIFTOOL + " " + EXIFTOOL_PARAM + " \"" + getAbsolutePath() + "\"");
			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line, output = "";
			while ((line = br.readLine()) != null) {
				output = output.concat(line);
			}
			JSONObject myjson = new JSONObject(output.substring(1, output.length() - 1));
			DateFormat dfm = new SimpleDateFormat("yyyy:MM:dd HH:mm");
			dfm.setTimeZone(TimeZone.getTimeZone("Europe/Zurich"));

			Vector<Date> datesFound = new Vector<Date>();

			Iterator<String> attributeCreationIt = attributeCreationDateList.iterator();
			while (attributeCreationIt.hasNext()) {
				String attributeCreation = attributeCreationIt.next();
				if (myjson.has(attributeCreation)) {
					datesFound.add(dfm.parse(myjson.getString(attributeCreation).substring(0, 17)));
				}
			}

			if (!datesFound.isEmpty()) {
				creationDate = Collections.min(datesFound);
			} else {
				throw new CreationDateNotFoundException();
			}

		} catch (Exception e) {
			throw new CreationDateNotFoundException();
		}
	}
}
