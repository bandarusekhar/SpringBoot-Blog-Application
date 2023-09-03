package in.sekhar.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorDetails {

	private Date LocalDate;
	private String message;
	private String details;
	
}
