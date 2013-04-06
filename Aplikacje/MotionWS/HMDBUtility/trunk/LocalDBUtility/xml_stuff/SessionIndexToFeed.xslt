<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:msxsl="urn:schemas-microsoft-com:xslt" exclude-result-prefixes="msxsl"
>
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="measurement">
      <session name="{concat(@date,'-',@id,'-',@session)}" date="{@date}" subjectId="{@id}" shortName="{@session}" >
        <xsl:for-each select="index">
          <trial name="{@trial}">
            <ExerciseNo>
              <xsl:value-of select="@exercise"/>
            </ExerciseNo>
          </trial>
        </xsl:for-each>
      </session>
    </xsl:template>
</xsl:stylesheet>
