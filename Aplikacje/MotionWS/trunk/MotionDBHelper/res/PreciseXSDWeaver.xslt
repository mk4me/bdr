<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xml:space="preserve" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:s="http://www.w3.org/2001/XMLSchema">
  <xsl:output indent="yes" method="xml" />
  
  <xsl:template match="node() | @*">
    <xsl:copy><xsl:apply-templates select="node() | @*"  /></xsl:copy>    
  </xsl:template>
  
  <xsl:template match="s:schema">
    <xsl:copy>
      <xsl:apply-templates select="@*" /> 
      <xsl:for-each select="//s:any">
        <s:include schemaLocation="{concat(substring-before(./ancestor-or-self::s:element[contains(@name,'Result')]/@name,'Result'),'.xsd')}" />
    </xsl:for-each>
      <xsl:apply-templates select="node()" /> 
    </xsl:copy>
  </xsl:template>
  
  <xsl:template match="s:any">
   <xsl:variable name="fileName" select="concat(substring-before(ancestor-or-self::s:element[contains(@name,'Result')]/@name,'Result'),'.xsd')" />
    <xsl:variable name="elementName" select="document($fileName)/s:schema/s:element/@name | document($fileName)/s:schema/s:element/@name" />
    <xsl:variable name="implicitElementName" select="substring-before(document($fileName)/s:schema[count(s:element)=0]/s:include/@schemaLocation,'Element')" />
    <s:element ref="tns:{concat($elementName,$implicitElementName)}" />
    
    
    <!--<xsl:value-of select="concat(substring-before(ancestor-or-self::s:element[contains(@name,'Result')]/@name,'Result'),'.xsd')" />-->
  </xsl:template>
  

</xsl:stylesheet>
