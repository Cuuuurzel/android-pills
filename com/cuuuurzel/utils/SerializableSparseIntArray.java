package com.cuuuurzel.utils;

import java.io.Serializable;

import android.util.SparseIntArray;

/**
 * Just a workaround to make SparseIntArray be serializable.
 */
public class SerializableSparseIntArray 
	extends SparseIntArray 
	implements Serializable {
}
