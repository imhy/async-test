package model;

import java.util.List;
import java.util.Map;

public class WithMapReq {
	private String hello;
	private List<Map<String,Object>> additionalServices;

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}

	public List<Map<String, Object>> getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(List<Map<String, Object>> additionalServices) {
		this.additionalServices = additionalServices;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("hello: ");
		sb.append(hello);
		sb.append(",additionalServices: ");
		sb.append(additionalServices);
		return sb.toString();
	}
}
