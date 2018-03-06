package xnat.plugin

/**
 * @author myyong
 */
class Session {
	int id
	String sessionID
	String sessionName
	String subjectID

	List<Scan> scans = []
}
