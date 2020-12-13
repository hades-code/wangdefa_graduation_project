package org.lhq.common;




public enum ActionType {
	DELETE("DELETE","删除"),
	OK("OK","正常"),
	COPY("COPY","复制"),
	ILLEGAL("ILLEGAL","违规");


	ActionType(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
	public String code;
	private String msg;
}
