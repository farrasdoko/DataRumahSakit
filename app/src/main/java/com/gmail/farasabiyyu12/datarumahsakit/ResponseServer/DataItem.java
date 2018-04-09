package com.gmail.farasabiyyu12.datarumahsakit.ResponseServer;

public class DataItem{
	private String nama;
	private String id;
	private String alamat;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"nama = '" + nama + '\'' + 
			",id = '" + id + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}
