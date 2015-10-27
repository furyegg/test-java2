<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:server="urn:jboss:domain:1.2" xmlns:log="urn:jboss:domain:logging:1.1">
	<xsl:output method="xml" indent="yes" />

	<xsl:param name="fileName" />
	<xsl:param name="updates" select="document($fileName)" />

	<xsl:variable name="updateItems" select="$updates/server:feed/log:entry" />

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="server:feed">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()[not(self::log:entry)] | log:entry[not(id = $updateItems/id)]" />
			<xsl:apply-templates select="$updateItems" />
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>