<?xml version='1.0' encoding='UTF-8'?>

<server xmlns="urn:jboss:domain:1.2">
	<subsystem xmlns="urn:jboss:domain:datasources:1.0">
		<datasources>
			<xa-datasource jta="true" jndi-name="java:/RACDS-SYS" pool-name="RACDS-SYS" enabled="true" use-java-context="true" use-ccm="true">
				<xa-datasource-property name="ServerName">
					${db_sys_host}
				</xa-datasource-property>
				<xa-datasource-property name="PortNumber">
					1433
				</xa-datasource-property>
				<xa-datasource-property name="DatabaseName">
					MFSD_SYS
				</xa-datasource-property>
				<xa-datasource-property name="Instance">
					sqlexpress
				</xa-datasource-property>
				<driver>jtds</driver>
				<xa-pool>
					<min-pool-size>5</min-pool-size>
					<max-pool-size>40</max-pool-size>
					<prefill>true</prefill>
				</xa-pool>
				<security>
					<security-domain>encrypted-ds-sys</security-domain>
				</security>
			</xa-datasource>
			<xa-datasource jta="true" jndi-name="java:/RACDS-DATA" pool-name="RACDS-DATA" enabled="true" use-java-context="true" use-ccm="true">
				<xa-datasource-property name="ServerName">
					172.20.30.173
				</xa-datasource-property>
				<xa-datasource-property name="PortNumber">
					1433
				</xa-datasource-property>
				<xa-datasource-property name="DatabaseName">
					MFSD_DATA
				</xa-datasource-property>
				<xa-datasource-property name="Instance">
					sqlexpress
				</xa-datasource-property>
				<driver>jtds</driver>
				<xa-pool>
					<min-pool-size>5</min-pool-size>
					<max-pool-size>40</max-pool-size>
					<prefill>true</prefill>
				</xa-pool>
				<security>
					<security-domain>encrypted-ds-data</security-domain>
				</security>
			</xa-datasource>
			<drivers>
				<driver name="jtds" module="net.sourceforge.jtds">
					<driver-class>net.sourceforge.jtds.jdbc.Driver</driver-class>
					<xa-datasource-class>net.sourceforge.jtds.jdbcx.JtdsDataSource</xa-datasource-class>
				</driver>
			</drivers>
		</datasources>
	</subsystem>
	<subsystem xmlns="urn:jboss:domain:security:1.1">
		<security-domains>
			<security-domain name="encrypted-ds-sys" cache-type="default">
				<authentication>
					<login-module code="org.picketbox.datasource.security.SecureIdentityLoginModule" flag="required">
						<module-option name="username" value="sa" />
						<module-option name="password" value="5dfc52b51bd35553df8592078de921bc" />
					</login-module>
				</authentication>
			</security-domain>
			<security-domain name="encrypted-ds-data" cache-type="default">
				<authentication>
					<login-module code="org.picketbox.datasource.security.SecureIdentityLoginModule" flag="required">
						<module-option name="username" value="sa" />
						<module-option name="password" value="5dfc52b51bd35553df8592078de921bc" />
					</login-module>
				</authentication>
			</security-domain>
		</security-domains>
	</subsystem>
</server>