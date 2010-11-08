package motion.applet.models;
/*
 * %W% %E%
 *
 * Copyright 1997, 1998 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer. 
 *   
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution. 
 *   
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.  
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,   
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER  
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS 
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

import java.io.File;
import java.util.Date;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import javax.swing.treetable.AbstractTreeTableModel;
import javax.swing.treetable.TreeTableModel;

import motion.database.DbElementsList;
import motion.database.model.DatabaseFile;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.GenericDescription;
import motion.database.model.Performer;
import motion.database.model.Session;
import motion.database.model.SessionStaticAttributes;
import motion.database.model.Trial;

public class SessionBrowserModel extends AbstractTreeTableModel 
                             implements TreeTableModel {

    // Names of the columnNames.
    static protected String[]  cNames = {"Name", "Value", "Type"};

    // Types of the columnNames.
    static protected Class[]  cTypes = {TreeTableModel.class, String.class, String.class};

    GenericDescription<?> entity;

    String sessionComponentsNames[] = { "Attributes", "Files", "Performers", "Trials" };
    DbElementsList sessionComponents[] = { new DbElementsList<DatabaseFile>(), new DbElementsList<Performer>(), new DbElementsList<Trial>() };
    
    
    public SessionBrowserModel(GenericDescription<?> s) { 
//    	super( s.groups.values().toArray()[1] ); //s.entityKind.name() + " (" + s.getId() +")"); 
    	super( s ); 
    	this.entity = s;
    }

    //
    // The TreeModel interface
    //

    public int getChildCount(Object node) { 
    	
    	if (node instanceof Session)
    		return 4;
    	else if (node instanceof EntityAttribute)
    		return 0;
    	else if (node instanceof String)
    		return entity.groups.values().size() - 2 + entity.groups.get("_static").size();  	
    	else
    		return ((EntityAttributeGroup)node).size();
    }

    public Object getChild(Object node, int i) { 
    	
    	if (node instanceof Session)
    	{
    		return sessionComponentsNames[i]; 
    	}
    	else if (node instanceof EntityAttributeGroup)
    		return ((EntityAttributeGroup)node).get( i );
    	else if (node instanceof String )
    	{ 	
    		if (node ==  sessionComponentsNames[0])
    		{
    	    		if (i<entity.groups.get("_static").size()-1)
    	    			return entity.groups.get("_static").elementAt( i+1 );
    	    		else
    	    			return entity.groups.get( entity.groups.keySet().toArray()[i-entity.groups.get("_static").size()+2] ); 			
    		}
    		else 
    			return null;
    	}
    	return null;
    }

    // The superclass's implementation would work, but this is more efficient. 
    public boolean isLeaf(Object node) 
    { 
    	return node instanceof EntityAttribute || (node instanceof String && node!=sessionComponentsNames[0]); 
    }

    //
    //  The TreeTableNode interface. 
    //

    public int getColumnCount() {
	return cNames.length;
    }

    public String getColumnName(int column) {
	return cNames[column];
    }

    public Class getColumnClass(int column) {
	return cTypes[column];
    }
 
    public Object getValueAt(Object node, int column) 
    {
    	if (node instanceof String)
	    	switch (column){
    		case 0: return node;
    		default: return null;
    	}
    	else if (node instanceof EntityAttributeGroup)
	    	switch (column){
	    		case 0: return ((EntityAttributeGroup)node).name;
	    		default: return null;
	    	}
	    else if (node instanceof Session)
	    {
	    	if (column==0)
	    		return "Session (" + ((Session)node).getId() + ")";
	    	else
	    		return "";
	    }
	    else
	    {
	    	EntityAttribute a = (EntityAttribute) node; 
		    switch(column) {
		    	case 0:
		    		return a.name;
		    	case 1:
		    		return a.value;
		    	case 2:
		    		return a.type;
		    }
		}
	    return null;
    }

    public boolean isCellEditable(Object node, int column)
    {
    	if (column == 0)
    		return true;
    	else
    		return false;
    }

    /**
     * Sets the value for node <code>node</code>, 
     * at column number <code>column</code>.
     */
    public void setValueAt(Object aValue, Object node, int column)
    {
    	EntityAttribute a = (EntityAttribute) node;
    	try{
    	 a.setValueFromString( aValue );
    	}catch( Exception e)
    	{
    		
    	}
    }

    
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}
}




