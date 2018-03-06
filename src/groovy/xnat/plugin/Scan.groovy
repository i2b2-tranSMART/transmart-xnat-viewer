package xnat.plugin

import groovy.transform.CompileStatic

/**
 * @author myyong
 */
@CompileStatic
class Scan {
	int id
	String scanID
	String seriesDesc
	String sessionID

	List<Snapshot> snapshots = []
}
