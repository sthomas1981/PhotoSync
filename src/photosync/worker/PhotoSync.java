package photosync.worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;

public class PhotoSync {

	// Logger initialization
	private static Logger logger = Logger.getLogger(PhotoSync.class);
	private static final String INIT_LOG_FILENAME = "log4j-init-file";
	private static final String EXECUTION_FOLDER = System.getProperty("user.dir");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			
			FileInputStream fis = new FileInputStream(new File("pub/test.jpg"));
			String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
			System.out.println(md5);
			
			
			
			File file = new File("pub/test.jpg");
			Hashtable<String, String> exifInfomationTable;
			try {
				exifInfomationTable = getMetadata(file);
			    for(String key : exifInfomationTable.keySet())
			    {
			      System.out.println(key + " : " + exifInfomationTable.get(key));
			    }
			} catch (ImageReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Hashtable<String, String> getMetadata(File file) throws ImageReadException, IOException {
		
		  IImageMetadata metadata = Sanselan.getMetadata(file);
		  Hashtable<String, String> exifInfomationTable = new Hashtable<String, String>();
		  if (metadata instanceof JpegImageMetadata) {
		    JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
		    
		    TiffField field;
		    
		    field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_MODEL);
		    exifInfomationTable.put(field.getTagName(), field.getValueDescription());
		    
		    field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_CREATE_DATE);
		    exifInfomationTable.put(field.getTagName(), field.getValueDescription());
		    
		    field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_FNUMBER);
		    exifInfomationTable.put(field.getTagName(), field.getValueDescription());
		    
		    field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_ISO);
		    exifInfomationTable.put(field.getTagName(), field.getValueDescription());
		    
		    field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_EXPOSURE_TIME);
		    exifInfomationTable.put(field.getTagName(), field.getValueDescription());
		    
		    field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_EXIF_IMAGE_WIDTH);
		    exifInfomationTable.put(field.getTagName(), field.getValueDescription());
		    
		    field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_EXIF_IMAGE_LENGTH);
		    exifInfomationTable.put(field.getTagName(), field.getValueDescription());
		  }
		  
		  return exifInfomationTable;
		 }
	
}
