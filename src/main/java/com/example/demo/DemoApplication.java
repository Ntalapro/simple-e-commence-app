package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class DemoApplication {
	private final CustomerRepository customerRepository;

	public DemoApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("")
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	record NewCustomerRequest(
			String name,
			String email,
			Integer age
	){}

	@PostMapping("")
	public void addCustomer(@RequestBody NewCustomerRequest request){
		Customer customer = new Customer();

		customer.setName(request.name);
		customer.setEmail(request.email);
		customer.setAge(request.age);

		customerRepository.save(customer);


	}

	@DeleteMapping("/{customerId}")
	public  void deleteCustomer( @PathVariable("customerId") Integer id){
		customerRepository.deleteById(id);
	}

	@PutMapping("/{customerId}")
	public  void updateCustomer( @PathVariable("customerId") Integer id,@RequestBody NewCustomerRequest request){

		Optional<Customer> optionalCustomer = customerRepository.findById(id);

		if (optionalCustomer.isPresent()) {

			Customer existingCustomer = optionalCustomer.get();
			existingCustomer.setName(request.name);
			existingCustomer.setEmail(request.email);
			existingCustomer.setAge(request.age);


			customerRepository.save(existingCustomer);
		} else {
			// Handle the case where the customer with the given ID is not found
			// You may throw an exception, return an error response, etc.
		}


	}
}
