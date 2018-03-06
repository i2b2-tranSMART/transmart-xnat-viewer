package xnat.plugin

import groovy.transform.CompileStatic

/**
 * @author myyong
 */
@CompileStatic
class Snapshot {
	int id
	String fileName
	String resourceID
	String scanID
	String sessionID
}
