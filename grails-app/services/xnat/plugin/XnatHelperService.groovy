package xnat.plugin

import com.recomdata.export.ExportColumn
import com.recomdata.export.ExportRowNew
import com.recomdata.export.ExportTableNew
import grails.util.Metadata
import groovy.sql.Sql
import org.springframework.beans.factory.InitializingBean

import javax.sql.DataSource

class XnatHelperService implements InitializingBean {

	private String appName

	DataSource dataSource
	SubjectService subjectService

	/**  plugin
	 * Fills the main demographic data in an export table for the grid
	 */
	ExportTableNew addAllPatientDemographicDataForSubsetToTable(ExportTableNew tablein, String result_instance_id, String subset) {
		Sql sql = new Sql(dataSource)
		String sqlt = '''
            SELECT
                I.*
            FROM (
                SELECT
                    p.*,
                    t.trial
                FROM
                    I2B2DEMODATA.patient_dimension p
                INNER JOIN I2B2DEMODATA.patient_trial t ON p.patient_num = t.patient_num
                WHERE
                    p.PATIENT_NUM IN (
                        SELECT
                            DISTINCT patient_num
                        FROM
                            I2B2DEMODATA.qt_patient_set_collection
                        WHERE
                            result_instance_id = ? ) )
                I
            ORDER BY
                I.PATIENT_NUM'''

		log.debug "Initial grid query: $sqlt, riid: $result_instance_id"

		//If I have an empty table structure so far
		if (!tablein.columns) {
			tablein.putColumn("study_id", new ExportColumn("study_id", "Study ID", "", "String"))
			tablein.putColumn("subject", new ExportColumn("subject", "Subject ID", "", "String"))
			tablein.putColumn("patient", new ExportColumn("patient", "Patient", "", "String"))
			tablein.putColumn("XNAT_image_download", new ExportColumn("XNAT_image_download", "XNAT", "", "String"))
			tablein.putColumn("subset", new ExportColumn("subset", "Subset", "", "String"))
		}

		int i = 0
		sql.eachRow sqlt, [result_instance_id], { row ->
			/*If I already have this subject mark it in the subset column as belonging to both subsets*/
			String subject = row.PATIENT_NUM
			if (tablein.containsRow(subject)) {
				String s = tablein.getRow(subject).get("subset")
				s = s + "," + subset
				tablein.getRow(subject).put("subset", s)
			}
			else {
				// fill the row
				def arr = row.SOURCESYSTEM_CD?.split(":")
				String patient = arr?.length == 2 ? arr[1] : ""

				ExportRowNew newrow = new ExportRowNew()
				i++

				newrow.put("study_id", row.TRIAL)
				newrow.put("subject", subject)
				newrow.put("patient", patient)

				String subjectID = row.TRIAL + ':' + patient
				if (subjectService.subjectExists(subjectID)) {
					newrow.put("XNAT_image_download", "<a href='/" + appName + "/scan?subjectID=" +
							subjectID + "' target = '_blank' style='color:blue'>View sessions</a>")
				}
				else {
					newrow.put("XNAT_image_download", "")
				}

				newrow.put("subset", subset)

				tablein.putRow(subject, newrow)
			}
		}

		return tablein
	}

	void afterPropertiesSet() {
		appName = Metadata.current.'app.name'
	}
}
