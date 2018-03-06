package xnat.plugin

/**
 * @author myyong
 */
class Subject implements Serializable {
	private static final long serialVersionUID = 1

	String transmartSubjectId
	String xnatProject
	String xnatSubjectId

	List<Session> sessions = []

	static mapping = {
		table 'SEARCHAPP.XNAT_SUBJECT'
		id generator: 'increment'
		version false

		transmartSubjectId column: 'TSMART_SUBJECTID'
		xnatSubjectId column: 'XNAT_SUBJECTID'
	}
}
