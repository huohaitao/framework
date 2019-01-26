package com.hht.factory;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

public class InjectField {
	
	private String dependName;
	
	private boolean byName;
	
	private boolean required;
	
	private Field field;

	public InjectField(String dependName, Field field, boolean required) {
		this.dependName = dependName;
		this.field = field;
		this.required = required;
		if(StringUtils.isNotBlank(this.dependName)) {
			this.byName = true;
		}
	}

	public String getDependName() {
		return dependName;
	}

	public void setDependName(String dependName) {
		this.dependName = dependName;
	}

	public Field getField() {
		return field;
	}

	public boolean isByName() {
		return byName;
	}

	public boolean isRequired() {
		return required;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InjectField [");
		if (dependName != null) {
			builder.append("dependName=");
			builder.append(dependName);
			builder.append(", ");
		}
		builder.append("byName=");
		builder.append(byName);
		builder.append(", ");
		if (field != null) {
			builder.append("field=");
			builder.append(field);
		}
		builder.append("]");
		return builder.toString();
	}
}
