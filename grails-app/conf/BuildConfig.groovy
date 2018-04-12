grails.project.work.dir = 'target'

grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'
	legacyResolve true

	repositories {
		mavenLocal()
		grailsCentral()
		mavenCentral()
		mavenRepo 'http://ec2-35-170-59-132.compute-1.amazonaws.com:8080/artifactory/libs-snapshots'
		mavenRepo 'http://ec2-35-170-59-132.compute-1.amazonaws.com:8080/artifactory/libs-releases'
		mavenRepo 'http://ec2-35-170-59-132.compute-1.amazonaws.com:8080/artifactory/plugins-releases'
		mavenRepo 'http://ec2-35-170-59-132.compute-1.amazonaws.com:8080/artifactory/plugins-snapshots'
		mavenRepo 'https://repo.transmartfoundation.org/content/repositories/public/'
	}

	dependencies {
		compile 'org.apache.httpcomponents:httpclient:4.4.1'
		compile 'org.apache.httpcomponents:httpcore:4.4.1'
		compile 'org.apache.httpcomponents:httpcore:4.4.1'
		compile 'org.transmartproject:transmart-core-api:18.1-SNAPSHOT'
		test 'org.grails:grails-datastore-test-support:1.0.2-grails-2.4'
	}

	plugins {
		compile ':hibernate:3.6.10.19', { export = false }
		compile ':search-domain:18.1-SNAPSHOT'
		compile ':transmart-core:18.1-SNAPSHOT'

		build ':release:3.1.2', ':rest-client-builder:2.1.1', {
			export = false
		}
	}
}
