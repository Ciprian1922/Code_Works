<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <title><xsl:value-of select="/library/@name"/></title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 20px;
                        background-color: #f9f9f9;
                    }
                    h1 {
                        color: #3f5efb;
                    }
                    h2 {
                        color: #fc466b;
                    }
                    category {
                        border-bottom: 2px solid #3f5efb;
                        margin-bottom: 10px;
                        padding: 10px 0;
                    }
                    book {
                        display: block;
                        padding: 5px 0;
                    }
                    book::before {
                        content: "• ";
                        color: #3f5efb;
                        font-weight: bold;
                    }
                </style>
            </head>
            <body>
                <h1><xsl:value-of select="/library/@name"/></h1>
                <xsl:for-each select="/library/category">
                    <category>
                        <h2><xsl:value-of select="@name"/></h2>
                        <xsl:for-each select="book">
                            <book>
                                <strong><xsl:value-of select="@title"/></strong> by <xsl:value-of select="@author"/>
                                (ISBN: <xsl:value-of select="@isbn"/>, Copies: <xsl:value-of select="@copies"/>)
                            </book>
                        </xsl:for-each>
                    </category>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
