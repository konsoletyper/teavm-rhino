package org.teavm.classlib.java.beans;

import java.util.EventObject;

public class TPropertyChangeEvent extends EventObject {
  private final Object propertyName;
  private final Object oldValue;
  private final Object newValue;
  private Object propagationId;

  public TPropertyChangeEvent(Object source, String propertyName, Object oldValue, Object newValue) {
    super(source);
    this.propertyName = propertyName;
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  public Object getPropertyName() {
    return propertyName;
  }

  public Object getOldValue() {
    return oldValue;
  }

  public Object getNewValue() {
    return newValue;
  }

  public Object getPropagationId() {
    return propagationId;
  }

  public void setPropagationId(Object propagationId) {
    this.propagationId = propagationId;
  }
}
