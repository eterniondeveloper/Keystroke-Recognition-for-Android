package com.raptis.konstantinos.keystrokerecognitionforandroid.core;

import com.raptis.konstantinos.keystrokerecognitionforandroid.util.KeyObject;

import java.util.Arrays;

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
			// Any pre - empty buffer operations here
			//--------------------------------------------------------------------------------------

			//--------------------------------------------------------------------------------------
			// Empty buffer here
			bufferArray = new KeyObject[BUFFER_SIZE];
			// Initialize index
            index = 0;
        }
		bufferArray[index] = keyObject;
        index++;
    }
	
	public KeyObject getCurrent() {
		return current;
	}
	
	public KeyObject getPrevious() {
		return previous;
	}
	
	public int getSize() {
		return BUFFER_SIZE;
	}
	 
	public String display() {
		return Arrays.toString(bufferArray);
	}
	
}
