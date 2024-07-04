package com.franko.jasper_example01;

import com.franko.jasper_example01.model.Address;
import com.franko.jasper_example01.repository.AddressRepository;
import com.franko.jasper_example01.service.JReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@RestController
public class JasperExample01Application {

	@Autowired
	private AddressRepository repository;
	@Autowired
	private JReportService service;

	@GetMapping("/getAddress")
	public List<Address> getAddress() {
		List<Address> address = (List<Address>) repository.findAll();
		return address;
	}


	@GetMapping("/jasperpdf/export")
	public void createPDF(HttpServletResponse response) throws IOException, JRException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		service.exportJasperReport(response);
	}

	public static void main(String[] args) {
		SpringApplication.run(JasperExample01Application.class, args);
	}

}
