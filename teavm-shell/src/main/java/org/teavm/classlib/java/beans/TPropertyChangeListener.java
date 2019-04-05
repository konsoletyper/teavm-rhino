package org.teavm.classlib.java.beans;

import java.util.EventListener;

public interface TPropertyChangeListener extends EventListener {
  void propertyChange(TPropertyChangeEvent evt);
}
