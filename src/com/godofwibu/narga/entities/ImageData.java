package com.godofwibu.narga.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "image_data")
public class ImageData {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Expose
	@Column(name = "url", nullable = false)
	private String url;
	
	@Column(name = "file_location", nullable = true)
	private String fileLocation;

	public ImageData(String url, String fileLocation) {
		super();
		this.url = url;
		this.fileLocation = fileLocation;
	}

	public ImageData(String url) {
		super();
		this.url = url;
	}
	
	
}
