package photosync.models;

public class PhotoSyncCoreModel {
	private String inputDirectory;
	private String outputDirectory;

	public final String getInputDirectory() {
		return inputDirectory;
	}

	public final void setInputDirectory(final String iInputDirectory) {
		this.inputDirectory = iInputDirectory;
	}

	public final String getOutputDirectory() {
		return outputDirectory;
	}

	public final void setOutputDirectory(final String iOutputDirectory) {
		this.outputDirectory = iOutputDirectory;
	}
}
