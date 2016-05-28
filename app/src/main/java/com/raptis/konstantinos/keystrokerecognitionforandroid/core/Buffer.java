package com.raptis.konstantinos.keystrokerecognitionforandroid.core;

import android.util.Log;

import com.raptis.konstantinos.keystrokerecognitionforandroid.util.Helper;

import java.util.Arrays;
import java.util.List;

public class Buffer {
	
	private final int BUFFER_SIZE;
	private int index = 0;
	private KeyObject[] bufferArray;
	private KeyObject current;
	private KeyObject previous;
	
	public Buffer(int size) {
		BUFFER_SIZE = size;
		bufferArray = new KeyObject[size];
	}
	
	public void add(KeyObject keyObject) {
		if (current == null && previous == null) {
			current = keyObject;
		} else {
			previous = current;
			current = keyObject;
		}
		
		if (index >= BUFFER_SIZE) {
			Log.i(Helper.BUFFER_LOG, "Buffer is full");
			return;
        }
		bufferArray[index++] = keyObject;
    }

	// removing last object from buffer
	public void remove() {
		bufferArray[--index] = null;
	}

	public KeyObject getCurrent() {
		return current;
	}
	
	public KeyObject getPrevious() {
		return previous;
	}

	public List<KeyObject> getAsList() {
		return Arrays.asList(bufferArray);
	}

	public int getSize() {
		return BUFFER_SIZE;
	}
	 
	public String display() {
		return Arrays.toString(bufferArray);
	}

	public KeyObject getFirst() {
		return bufferArray[0];
	}

	public KeyObject getLast() {
		return bufferArray[bufferArray.length - 1];
	}

	public int getIndex() {
		return index;
	}

	public KeyObject getElement(int position) {
		if(position >= 0 && position < bufferArray.length) {
			return bufferArray[position];
		} else {
			return null;
		}
	}

}
