package com.mvp4g.example.client.bean;

public class NavStatus {

	private int startIndex, endIndex, numberOfElements;
	
	
	public NavStatus() {
		
	}

	public NavStatus(int startIndex, int endIndex, int numberOfElements) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.numberOfElements = numberOfElements;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex
	 *            the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * @param endIndex
	 *            the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * @return the numberOfElements
	 */
	public int getNumberOfElements() {
		return numberOfElements;
	}

	/**
	 * @param numberOfElements
	 *            the numberOfElements to set
	 */
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

}
