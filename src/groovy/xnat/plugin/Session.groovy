package xnat.plugin

import groovy.transform.CompileStatic

/**
 * @author myyong
 */
@CompileStatic
class Session {
	int id
	String sessionID
	String sessionName
	String subjectID

	List<Scan> scans = []
}
