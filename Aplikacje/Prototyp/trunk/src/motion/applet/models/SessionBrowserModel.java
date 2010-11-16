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
 * This software is provided "AS IS," without attribute warranty of any
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

import java.util.Vector;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;
import javax.swing.treetable.AbstractTreeTableModel;
import javax.swing.treetable.TreeTableModel;

import motion.database.DatabaseConnection;
import motion.database.model.DatabaseFile;
import motion.database.model.DatabaseFileStaticAttributes;
import motion.database.model.EntityAttribute;
import motion.database.model.EntityAttributeGroup;
import motion.database.model.EntityKind;
import motion.database.model.GenericDescription;
import motion.database.model.Performer;
import motion.database.model.PerformerStaticAttributes;
import motion.database.model.Session;
import motion.database.model.Trial;
import motion.database.model.TrialStaticAttributes;

public class SessionBrowserModel extends AbstractTreeTableModel implements
		TreeTableModel {

	interface ModelElementView {
		public abstract int getChildCount();

		public abstract Object getChild(int i);

		public abstract boolean isLeaf();

		public abstract Object getValueAt(int column);
	}

	@SuppressWarnings("serial")
	static class NamedVector<T extends ModelElementView> extends Vector implements
			ModelElementView {
		String name;

		public NamedVector(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

		@Override
		public Object getChild(int i) {
			return this.elementAt(i);
		}

		@Override
		public int getChildCount() {
			return this.size();
		}

		@Override
		public Object getValueAt(int column) {
			return "";
		}

		@Override
		public boolean isLeaf() {
			return false;
		}
	}

	@SuppressWarnings("serial")
	public static 	class AttributedVectorView<T extends ModelElementView> extends NamedVector<T> {
		
		public GenericDescription<?> entity;

		private NamedVector<T> attributeVector = new NamedVector<T>(
				"<html><b>Attributes</b></html>");

		public AttributedVectorView(GenericDescription<?> entity)
				throws Exception {
			super(entity.entityKind.getGUIName() + " (" + entity.getId() + ")");
			this.entity = entity;

			// create attributes View
			for (EntityAttributeGroup g : entity.groups.values())
				if (g.name.equals(EntityKind.STATIC_ATTRIBUTE_GROUP))
					for (EntityAttribute a : g)
						attributeVector.add(new AttributeView(a));
				else
					attributeVector.add(new AttributeGroupView(g));

			this.add(attributeVector);
		}
	}

	@SuppressWarnings("serial")
	static class EntityFileView<T extends ModelElementView> extends
			AttributedVectorView<T> {
		
		private NamedVector<T> fileVector = new NamedVector<T>(
				"<html><b>Files</b></html>");

		public EntityFileView(GenericDescription<?> session) throws Exception {
			super(session);

			// create files view
			for (DatabaseFile f : DatabaseConnection.getInstance().listFiles(
					session.getId(), session.entityKind))
				fileVector.add(new SessionBrowserModel.FileView(f));

			this.add(fileVector);
		}
	}

	@SuppressWarnings("serial")
	static class SessionView extends EntityFileView {
		
		private NamedVector<PerformerView> performersVector = new NamedVector<PerformerView>(
				"<html><b>Performers</b></html>");
		private NamedVector<TrialView> trialVector = new NamedVector<TrialView>(
				"<html><b>Trials</b></html>");

		public SessionView(Session session) throws Exception {
			super(session);

			// create performers view
			for (Performer p : DatabaseConnection.getInstance()
					.listSessionPerformersWithAttributes(session.getId()))
				performersVector.add(new PerformerView(p));

			// create trials view
			for (Trial t : DatabaseConnection.getInstance()
					.listSessionTrialsWithAttributes(session.getId()))
				trialVector.add(new TrialView(t));

			this.add(performersVector);
			this.add(trialVector);
		}
	}

	static class SessionSetView extends NamedVector<SessionView> {
		public SessionSetView(Session[] sessions) throws Exception {
			super("Sessions:");
			for (Session i : sessions)
				this.add(new SessionView(i));
		}
	}

	public static class AttributeView implements ModelElementView {
		
		public EntityAttribute attribute;

		public AttributeView(EntityAttribute a) {
			this.attribute = a;
		}

		@Override
		public Object getChild(int i) {
			return null;
		}

		@Override
		public int getChildCount() {
			return 0;
		}

		@Override
		public Object getValueAt(int column) {
			if (attribute.value!= null && column == 1)
				return attribute.value.toString();
			else if (column == 2)
				return attribute.type;
			else
				return "";
		}

		public String toString() {
			return attribute.name;
		}

		@Override
		public boolean isLeaf() {
			return true;
		}
	}

	public static class FileView extends AttributedVectorView {
		public FileView(DatabaseFile a) throws Exception {
			super(a);
		}

		public String toString() {
			return (((DatabaseFile) entity)
					.getValue(DatabaseFileStaticAttributes.FileName))
					.toString();
		}
	}

	static class PerformerView extends AttributedVectorView {
		public PerformerView(Performer a) throws Exception {
			super(a);
		}

		@Override
		public Object getValueAt(int column) {
			if (column == 1)
				return ((Performer) entity).getValue(
						PerformerStaticAttributes.FirstName).toString();
			else if (column == 2)
				return ((Performer) entity).getValue(
						PerformerStaticAttributes.LastName).toString();
			else
				return "";
		}
	}

	static class TrialView extends EntityFileView {
		public TrialView(Trial a) throws Exception {
			super(a);
		}
	}

	static class AttributeGroupView extends NamedVector<AttributeView> {
		private EntityAttributeGroup g;

		public AttributeGroupView(EntityAttributeGroup g) {
			super(g.name);
			this.g = g;

			for (EntityAttribute a : g)
				add(new AttributeView(a));
		}
	}

	// Names of the columnNames.
	static protected String[] cNames = { "Name", "Value", "Type" };

	// Types of the columnNames.
	static protected Class[] cTypes = { TreeTableModel.class, String.class,
			String.class };

	public SessionBrowserModel(Session s) throws Exception { 
    	super( new SessionView(s) );
    }

	public SessionBrowserModel(Session[] s) throws Exception {
		super(new SessionSetView(s));
	}

	//
	// The TreeModel interface
	//

	public int getChildCount(Object node) {
		return ((ModelElementView) node).getChildCount();
	}

	public Object getChild(Object node, int i) {
		return ((ModelElementView) node).getChild(i);
	}

	// The superclass's implementation would work, but this is more efficient.
	public boolean isLeaf(Object node) {
		return ((ModelElementView) node).isLeaf();
	}

	//
	// The TreeTableNode interface.
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

	public Object getValueAt(Object node, int column) {
		return ((ModelElementView) node).getValueAt(column);
	}

	public boolean isCellEditable(Object node, int column) {
		if (column == 0)
			return true;
		else
			return false;
	}

	/**
	 * Sets the value for node <code>node</code>, at column number
	 * <code>column</code>.
	 */
	public void setValueAt(Object aValue, Object node, int column) {
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		super.addTreeModelListener(l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		super.removeTreeModelListener(l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub

	}
}
