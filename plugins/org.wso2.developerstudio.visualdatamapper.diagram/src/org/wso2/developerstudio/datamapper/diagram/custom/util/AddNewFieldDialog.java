/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.developerstudio.datamapper.diagram.custom.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddNewFieldDialog extends Dialog {

	private Text textRootName;
	private Combo schemaTypeCombo;
	private Text textDoc;
	private Text textAliases;
	private Text textDefault;
	private Combo orderCombo;
	private Composite compositeField;

	private String name;
	private String doc;
	private String order;
	private Set<String> aliases;
	private JsonNode defaultValue;
	private String schemaType;

	private String[] DATA_TYPES = { "STRING", "INT", "ARRAY", "BOOLEAN", "BYTES", "DOUBLE", "ENUM", "FIXED", "FLOAT",
			"INT", "LONG", "MAP", "NULL", "RECORD", "UNION" };
	private String[] ORDER_TYPES = { "ASCENDING", "DESCENDING", "IGNORE" };
	
	private static final String DIALOG_TITLE = "Add new Attribute";
	private static final String LABEL_NAME = "Name :";
	private static final String LABEL_SCHEMATYPE = "Schema Data Type :";
	private static final String LABEL_DOC = "Doc :";
	private static final String LABEL_ALIASES = "Aliases :";
	private static final String LABEL_DEFAULT = "Default :";
	private static final String LABEL_ORDER = "Order :";
	private static final String NEW_ROOT_RECORD_ID = "NewAttribute";
	private static final String DEFAULT = "default";

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public AddNewFieldDialog(Shell parentShell, Class<?>[] type) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.TITLE | SWT.BORDER | SWT.OK | SWT.APPLICATION_MODAL);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		getShell().setText(DIALOG_TITLE);

		compositeField = new Composite(container, SWT.NONE);
		GridData gd_composite_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_2.widthHint = 575;
		compositeField.setLayoutData(gd_composite_2);
		compositeField.setLayout(new GridLayout(5, false));

		Label lblRootNameLabel = new Label(compositeField, SWT.NONE);
		lblRootNameLabel.setText(LABEL_NAME);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);

		textRootName = new Text(compositeField, SWT.BORDER);
		textRootName.setText(NEW_ROOT_RECORD_ID);

		textRootName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {

			}
		});
		textRootName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblSchemaTypeLabel = new Label(compositeField, SWT.NONE);
		lblSchemaTypeLabel.setText(LABEL_SCHEMATYPE);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);

		schemaTypeCombo = new Combo(compositeField, SWT.DROP_DOWN | SWT.READ_ONLY);
		schemaTypeCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			}
		});

		schemaTypeCombo.setItems(DATA_TYPES);
		schemaTypeCombo.select(0);
		schemaTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lbldocLabel = new Label(compositeField, SWT.NONE);
		lbldocLabel.setText(LABEL_DOC);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);

		textDoc = new Text(compositeField, SWT.BORDER);
		textDoc.setText("");

		textDoc.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			}
		});

		textDoc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblAliasesLabel = new Label(compositeField, SWT.NONE);
		lblAliasesLabel.setText(LABEL_ALIASES);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);

		textAliases = new Text(compositeField, SWT.BORDER);
		textAliases.setText("");

		textAliases.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			}
		});

		textAliases.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lbldefaultLabel = new Label(compositeField, SWT.NONE);
		lbldefaultLabel.setText(LABEL_DEFAULT);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);

		textDefault = new Text(compositeField, SWT.BORDER);
		textDefault.setText("");

		textDefault.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			}
		});

		textDefault.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblOrderLabel = new Label(compositeField, SWT.NONE);
		lblOrderLabel.setText(LABEL_ORDER);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);
		new Label(compositeField, SWT.NONE);

		orderCombo = new Combo(compositeField, SWT.DROP_DOWN | SWT.READ_ONLY);
		orderCombo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			}
		});

		orderCombo.setItems(ORDER_TYPES);
		orderCombo.select(0);
		orderCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(620, 300);
	}

	@Override
	protected void okPressed() {
		setName(textRootName.getText());
		setSchemaType(schemaTypeCombo.getText());
		setDoc(textDoc.getText());
		JsonNode defValue;
		try {
			defValue = getDefaultValue(textDefault.getText());
			setDefault(defValue);
		} catch (JSONException e) {
			e.printStackTrace();
		}catch (NumberFormatException nfe) {
		MessageDialog.openError(getShell(), "Error", "Invalid value for type " + schemaTypeCombo.getText());
		return;
		}
		setOrder(orderCombo.getText());
		Set<String> aliasesSet = getAliasesSet();
		setAliases(aliasesSet);
		super.okPressed();
	}

	/**
	 * @throws JSONException 
	 * Use to get the default value
	 * @param defaultValue 
	 * @throws  
	 */
	private JsonNode getDefaultValue(String defaultValue) throws JSONException, NumberFormatException {
		JsonNode defaultValueNode = null;
		if (StringUtils.isNotEmpty(defaultValue)) {
			defaultValueNode = getJsonNode(defaultValue);
		}
		return defaultValueNode;
	}

	/**
	 * Gets the Json Object based on the data type
	 * @param defaultValue
	 * @param jsonObj
	 * @param mapper 
	 * @throws JSONException
	 */
	private JsonNode getJsonNode(String defaultValue) throws JSONException {
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonObj = new JSONObject();
		JsonNode defNode= null;
		
		switch (schemaTypeCombo.getText()) {

		case "BOOLEAN":
			jsonObj.append(DEFAULT, Boolean.parseBoolean(defaultValue));
			break;
		case "BYTES":
			jsonObj.append(DEFAULT, defaultValue);
			break;
		case "DOUBLE":
			jsonObj.append(DEFAULT, Double.parseDouble(defaultValue));
			break;
		case "FLOAT":
			jsonObj.append(DEFAULT, Float.parseFloat(defaultValue));
			break;
		case "INT":
			jsonObj.append(DEFAULT, Integer.parseInt(defaultValue));
			break;
		case "LONG":
			jsonObj.append(DEFAULT, Long.parseLong(defaultValue));
			break;
		case "STRING":
			jsonObj.append(DEFAULT, defaultValue);
			break;
		case "NULL":
			break;
		case "UNION":
			break;
		default:
			break;

		}
		
		JsonNode arrNode = null;
		try {
			arrNode = mapper.readTree(jsonObj.toString()).get(DEFAULT);
			if (arrNode.isArray()) {
			    for (final JsonNode objNode : arrNode) {
			        defNode = objNode;
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defNode;
	}

	/**
	 * Use to get the Aliases as a set
	 * 
	 * @return
	 */
	private Set<String> getAliasesSet() {
		Set<String> aliasesSet = null;
		String aliases = textAliases.getText();
		if(StringUtils.isNotEmpty(aliases)){
			String[] aliasesArray = aliases.split(",");
			aliasesSet = new HashSet<String>(Arrays.asList(aliasesArray));
		}
		
		return aliasesSet;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSchemaType(String schemaType) {
		this.schemaType = schemaType;
	}

	public String getName() {
		return name;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public void setAliases(Set<String> aliases) {
		this.aliases = aliases;
	}

	public void setDefault(JsonNode defaultValue){
		this.defaultValue = defaultValue;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}

	public String getSchemaType() {
		return schemaType;
	}

	public String getDoc() {
		return doc;
	}

	public JsonNode getDefault() {
		return this.defaultValue;
	}
	
	public String getOrder() {
		return order;
	}

	public Set<String> getAliases() {
		return aliases;
	}
}
