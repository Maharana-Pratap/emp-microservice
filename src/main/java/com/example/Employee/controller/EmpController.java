package com.example.Employee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.Employee.config.FromProperty;
import com.example.Employee.modal.Emp;
import com.example.Employee.modal.EmpAddress;
import com.example.Employee.modal.EmpDetails;

@RestController 
@RequestMapping("/emp")
public class EmpController {

	@Autowired 
	private RestTemplate restTemplate;
	
	@Autowired
	private FromProperty property;
	
	@GetMapping("/details")
	public List<EmpDetails> getEmp() {
		Emp e = new Emp();
		e.setId(1);
		e.setName("Kittu");
		
	 List<Emp> emps = List.of(
				new Emp(1,"kittu"),
				new Emp(2,"priya")
				);
	 List<EmpDetails> details = new ArrayList<EmpDetails>();
	 emps.stream().forEach(data -> {
			//@SuppressWarnings("unchecked")
			EmpAddress address = restTemplate.getForObject("http://address-service/address/details/"+data.getId(), EmpAddress.class);
			System.out.println(address.getId()+" : "+address.getAddress()+" : "+address.getMob());			
				EmpDetails ew = new EmpDetails();
				ew.setEmpAddress(address.getAddress());
				ew.setEmpId(data.getId());
				ew.setEmpName(data.getName());
				ew.setEmpPhone(address.getMob());
				details.add(ew);			
			
		});
			
		//EmpAddress address = restTemplate.getForObject("http://localhost:8888/address/details", EmpAddress.class);
		//System.out.println(address.getId()+" : "+address.getAddress()+" : "+address.getMob());
		
		return details;
	}
	
	@GetMapping("/property")
	public Map<String,String> getFromProperty() {
		Map<String,String> map = new HashMap<>();
		map.put("id", property.getId()+"");
		map.put("name", property.getName());
		map.put("age", property.getAge()+"");
		map.put("address", property.getAddress());
		
		return map;
	}
}
