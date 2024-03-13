package com.example.demo.Dto;



import jakarta.validation.constraints.*;



public class ProductDto {
	
	@NotEmpty(message="The Name is Required")
	private String name;
	@NotEmpty(message="The Company Name is Required")
	private String company;
	@NotEmpty(message="The Type is Required")
	private String type;
	@NotNull
    @Min(value = 0, message = "Price must be greater than or equal to zero")
	private int price;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
