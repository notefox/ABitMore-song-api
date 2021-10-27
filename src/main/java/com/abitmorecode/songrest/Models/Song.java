package com.abitmorecode.songrest.Models;

import org.springframework.lang.NonNull;

/**
 * Song Class
 */
public class Song {

	private int id;
	private String title;
	private String artist;
	private String label;
	private int released;

	/**
	 * Song constructor
	 * @param id id int
	 * @param title Song title String
	 * @param artist Artist Name String
	 * @param label Label Name String
	 * @param released Release Year int
	 */
	public Song(@NonNull int id, @NonNull String title, @NonNull String artist, @NonNull String label, @NonNull int released) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.label = label;
		this.released = released;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getReleased() {
		return released;
	}

	public void setReleased(int released) {
		this.released = released;
	}

	public boolean anyNull() {
		return id == 0 || title == null || artist == null || label == null || released == 0 ;
	}

	/**
	 * custom to String
	 * @return String
	 */
	@Override
	public String toString() {
		return "Song{" + "id=" + id + ", title='" + title + '\'' + ", artist='" + artist + '\'' + ", label='" + label + '\'' + ", released=" + released + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Song song = (Song) o;

		if (id != song.id)
			return false;
		if (released != song.released)
			return false;
		if (!title.equals(song.title))
			return false;
		if (!artist.equals(song.artist))
			return false;
		return label.equals(song.label);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + title.hashCode();
		result = 31 * result + artist.hashCode();
		result = 31 * result + label.hashCode();
		result = 31 * result + released;
		return result;
	}
}
