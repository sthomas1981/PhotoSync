package photosync.views;

public interface IStatusReportable {
	void setStatus(final int iStatus);
	void setFileStatus(long l, long m);
	void open();
	void close();
}
