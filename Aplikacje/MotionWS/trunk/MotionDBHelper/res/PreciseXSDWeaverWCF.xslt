<?xml version="1.0" ?>
<xsl:stylesheet version="1.0" xml:space="preserve" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
  <xsl:output indent="yes" method="xml" />
  
  <xsl:template match="node() | @*">
    <xsl:copy><xsl:apply-templates select="node() | @*"  /></xsl:copy>    
  </xsl:template>
  
  <xsl:template match="xsd:schema">
    <xsl:copy>
      <xsl:apply-templates select="@*" /> 
      <xsl:for-each select="//xsd:any">
          <xsl:variable name="operName" select="ancestor-or-self::xsd:element[contains(@name,'Result') and not(contains(@name,'Response'))]/@name" />
          <xsl:variable name="fileName" select="concat(substring($operName,0,(string-length($operName)-5)),'.xsd')" />
          <xsd:include schemaLocation="{$fileName}" />
    </xsl:for-each>
      <xsl:apply-templates select="node()" /> 
    </xsl:copy>
  </xsl:template>
  
  <xsl:template match="xsd:any">
  <xsl:variable name="operName" select="ancestor-or-self::xsd:element[contains(@name,'Result') and not(contains(@name,'Response'))]/@name" />
  <xsl:variable name="fileName" select="concat(substring($operName,0,(string-length($operName)-5)),'.xsd')" />
  <xsl:variable name="elementName" select="document($fileName)/xsd:schema/xsd:element/@name" />
                                    
    <xsl:variable name="implicitElementName" select="substring-before(document($fileName)/xsd:schema[count(xsd:element)=0]/xsd:include/@schemaLocation,'Element')" />
    <xsd:element ref="tns:{concat($elementName,$implicitElementName)}" />
    
    
    <!--<xsl:value-of select="concat(substring-before(ancestor-or-self::s:element[contains(@name,'Result')]/@name,'Result'),'.xsd')" />-->
  </xsl:template>
  

</xsl:stylesheet>

