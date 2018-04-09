package com.gmail.farasabiyyu12.datarumahsakit.ResponseServer;

public class ResponseDeleteData{
	private boolean result;
	private String msg;

	public void setResult(boolean result){
		this.result = result;
	}

	public boolean isResult(){
		return result;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDeleteData{" + 
			"result = '" + result + '\'' + 
			",msg = '" + msg + '\'' + 
			"}";
		}
}
