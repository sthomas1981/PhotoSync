package photosync.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import photosync.com.exceptions.CreationDateNotFoundException;

import net.sourceforge.filebot.mediainfo.MediaInfo;

public class VideoFile extends MediaFile {

	private static final long serialVersionUID = 4749173801169698128L;

	private static final List<String> attributeCreationDateList =
			Arrays.asList("Mastered_Date", "Encoded_Date", "File_Modified_Date",
					"File_Modified_Date", "Original/Released_Date", "Recorded_Date",
					"Tagged_Date", "Written_Date", "File_Created_Date", "File_Modified_Date");

	private static final List<String> dateFormatList =
			Arrays.asList("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS");

	public VideoFile(final String pathname) {
		super(pathname);
	}

	@Override
	public final void computeCreationDate() throws CreationDateNotFoundException {
		MediaInfo info = new MediaInfo();
		info.open(this);

		Vector<Date> datesFound = new Vector<Date>();

		Iterator<String> attributeCreationIt = attributeCreationDateList.iterator();
		while (attributeCreationIt.hasNext()) {
			String attributeCreation = attributeCreationIt.next();
			Iterator<String> dateFormatIt = dateFormatList.iterator();
			while (dateFormatIt.hasNext()) {
				try {
					String dateFormat = dateFormatIt.next();
					DateFormat dfm = new SimpleDateFormat(dateFormat);
					dfm.setTimeZone(TimeZone.getTimeZone("Europe/Zurich"));
					String date = info.get(MediaInfo.StreamKind.General, 0, attributeCreation, MediaInfo.InfoKind.Text, MediaInfo.InfoKind.Name);
					date = date.replace("UTC ", "");
					datesFound.add(dfm.parse(date));
				} catch (ParseException e) {
					// empty
				}
			}
		}

		if (!datesFound.isEmpty()) {
			creationDate = Collections.min(datesFound);
		} else {
			throw new CreationDateNotFoundException();
		}
	}

}
