<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xml:space="preserve" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:s="http://www.w3.org/2001/XMLSchema">
  <xsl:output indent="yes" method="xml" />
  
  <xsl:template match="node() | @*">
    <xsl:copy><xsl:apply-templates select="node() | @*"  /></xsl:copy>    
  </xsl:template>
  
  <xsl:template match="s:include">
    <xsl:variable name="currentInclude" select="." />
 <xsl:if test="count(./preceding::s:include[@schemaLocation=($currentInclude/@schemaLocation)])=0" >
     <xsl:for-each select="document($currentInclude/@schemaLocation)/s:schema/s:include">
       <xsl:copy-of select="."/>
      </xsl:for-each>
      <xsl:for-each select="document($currentInclude/@schemaLocation)/s:schema/s:element">
        <xsl:variable name="elName" select="./@name" />
       <xsl:if test="count($currentInclude/ancestor::*/descendant::s:element[@name=$elName])=0">
       <xsl:copy><xsl:apply-templates select="node() | @*"  /></xsl:copy>
         </xsl:if>
      </xsl:for-each>
    </xsl:if>
  </xsl:template>
 
  
   <xsl:template match="node() | @*">
    <xsl:copy><xsl:apply-templates select="node() | @*"  /></xsl:copy>    
  </xsl:template>
  
  <xsl:template match="@ref">
    <xsl:attribute name="ref">
      <xsl:choose>
        <xsl:when test="starts-with(.,'tns:')"><xsl:value-of select="."/></xsl:when>
        <xsl:otherwise> <xsl:value-of select="concat('tns:',.)"/> </xsl:otherwise>
      </xsl:choose>
      
    </xsl:attribute>
  </xsl:template>
  

 

</xsl:stylesheet>
