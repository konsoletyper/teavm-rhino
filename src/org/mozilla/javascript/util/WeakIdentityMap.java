/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public final class WeakIdentityMap<K, V> {
  private static final float FILL_RATE = 0.6f;
  private Entry[] table = new Entry[64];
  private int threshold;
  private ReferenceQueue<Object> queue = new ReferenceQueue<>();
  private int filledCells;
  private int cleanupCount;

  public WeakIdentityMap() {
    updateThreshold();
  }

  private void updateThreshold() {
    threshold = (int) (table.length * FILL_RATE);
  }

  @SuppressWarnings("unchecked")
  public V get(K key) {
    cleanup();
    int hash = Math.abs(System.identityHashCode(key));
    int index = hash % table.length;
    Entry entry = table[index];
    while (entry != null) {
      if (entry.get() == key) {
        return (V) entry.value;
      }
      entry = entry.next;
    }
    return null;
  }

  public boolean containsKey(K key) {
    cleanup();
    int hash = Math.abs(System.identityHashCode(key));
    int index = hash % table.length;
    Entry entry = table[index];
    while (entry != null) {
      if (entry.get() == key) {
        return true;
      }
      entry = entry.next;
    }
    return false;
  }

  @SuppressWarnings("unchecked")
  public V remove(K key) {
    cleanup();
    int hash = Math.abs(System.identityHashCode(key));
    int index = hash % table.length;
    Entry entry = table[index];
    Entry previous = null;
    while (entry != null) {
      if (entry.get() == key) {
        if (previous == null) {
          table[index] = entry.next;
          if (entry.next == null) {
            filledCells--;
          }
        } else {
          previous.next = entry;
        }
        return (V) entry.value;
      }
      previous = entry;
      entry = entry.next;
    }
    return null;
  }

  public void put(K key, V value) {
    cleanup();
    int hash = Math.abs(System.identityHashCode(key));
    int index = hash % table.length;

    Entry entry = table[index];
    if (entry != null) {
      insertAt(index, key, value);
    } else {
      if (++filledCells > threshold) {
        grow();
        insertAt(hash % table.length, key, value);
        return;
      }
      insertAt(index, key, value);
    }
  }

  private void insertAt(int index, K key, V value) {
    Entry entry = table[index];
    while (entry != null) {
      if (entry.get() == key) {
        entry.value = value;
        return;
      }
      entry = entry.next;
    }
    table[index] = new Entry(table[index], key, value, index, queue);
  }

  @SuppressWarnings("unchecked")
  private void grow() {
    Entry[] oldTable = table;
    table = new Entry[table.length * 2];
    updateThreshold();
    filledCells = 0;
    for (Entry entry : oldTable) {
      while (entry != null) {
        Object key = entry.get();
        if (key != null) {
          int hash = Math.abs(System.identityHashCode(key));
          int index = hash % table.length;
          if (table[index] == null) {
            ++filledCells;
          }
          insertAt(index, (K) key, (V) entry.value);
        }
        entry = entry.next;
      }
    }
  }

  private void cleanup() {
    if (++cleanupCount <= 100) {
      return;
    }
    cleanupCount = 0;

    while (true) {
      Entry entry = (Entry) queue.poll();
      if (entry == null) {
        break;
      }
      if (entry.removed) {
        continue;
      }
      int index = entry.index;

      Entry current = table[index];
      Entry previous = null;
      boolean skipped = false;
      while (current != null) {
        if (current.get() != null) {
          if (skipped) {
            if (previous == null) {
              table[index] = current;
            } else {
              previous.next = current;
            }
            skipped = false;
          }
          previous = current;
        } else {
          skipped = true;
          current.removed = true;
        }
        current = current.next;
      }

      if (skipped) {
        if (previous == null) {
          table[index] = null;
          --filledCells;
        } else {
          previous.next = null;
        }
      }

      current = table[index];
      Entry start = current;
      while (current != null) {
        current = current.next;
        if (current == start) {
          throw new IllegalStateException();
        }
      }
    }
  }

  static class Entry extends WeakReference<Object> {
    Entry next;
    Object value;
    final int index;
    boolean removed;

    Entry(Entry next, Object key, Object value, int index, ReferenceQueue<Object> queue) {
      super(key, queue);
      this.next = next;
      this.value = value;
      this.index = index;
    }
  }
}
