package com.mvp4g.util.test_tools;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.configuration.tree.DefaultConfigurationNode;

/**
 * Utility class to help in the construction of test XML configurations.
 * 
 * @author javier
 * 
 */
public class XMLConfigurationBuilder {

	private String tagName;

	public XMLConfigurationBuilder( String tagName ) {
		this.tagName = tagName;
	}

	public XMLConfiguration getEmptyConf() {
		return new XMLConfiguration();
	}

	public XMLConfiguration getConfigAttribute( List<String> attList, List<String> multiVal, List<String> parentAttList, int nbNodes,
			boolean different, boolean singleNode ) {
		XMLConfiguration xml = new XMLConfiguration();
		List<ConfigurationNode> nodes = new ArrayList<ConfigurationNode>();
		for ( int i = 0; i < nbNodes; i++ ) {
			nodes.add( createNode( tagName, attList, multiVal, ( different ) ? Integer.toString( i ) : "" ) );
		}

		if ( singleNode ) {
			xml.addNodes( "", nodes );
		} else {
			xml.addNodes( rootTag(), nodes );

			if ( nbNodes > 0 ) {
				ConfigurationNode parent = xml.getRoot().getChild( 0 );
				for ( String parentAtt : parentAttList ) {
					parent.addAttribute( new DefaultConfigurationNode( parentAtt, parentAtt ) );
				}
			}
		}

		return xml;
	}

	private String rootTag() {
		return tagName + "s";
	}

	private ConfigurationNode createNode( String nodeName, List<String> attList, List<String> multiVal, String end ) {
		ConfigurationNode node = new DefaultConfigurationNode( nodeName );

		ConfigurationNode attNode = null;
		for ( String att : attList ) {
			node.addAttribute( new DefaultConfigurationNode( att, att + end ) );
		}

		for ( String multi : multiVal ) {
			attNode = new DefaultConfigurationNode( multi );
			attNode.setValue( multi + end );
			node.addAttribute( attNode );
		}

		return node;
	}
}
