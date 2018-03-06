package xnat.plugin

/**
 * @author myyong
 */
class Scan {
	int id
	String scanID
	String seriesDesc
	String sessionID

	List<Snapshot> snapshots = []
}
