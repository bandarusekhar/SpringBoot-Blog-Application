package in.sekhar.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.sekhar.payload.CategoryDto;
import in.sekhar.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(
		name="CRUD REST APIs for CATEGORY Resource"
  		
)
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Operation(
			summary = "Create Category REST API",
			description = "This REST API is used to create a category and store it to database"
	)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 CREATED"
	)
	@SecurityRequirement(
			name="Bearer Authentication"
	)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
		CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
	}

	@Operation(
			summary = "Fetch Category REST API",
			description = "This REST API is used to fetch a perticulat category from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping("{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long id) {
		CategoryDto categoryDto = categoryService.getCategory(id);
		return ResponseEntity.ok(categoryDto);
	}

	@Operation(
			summary = "Fetch Categories REST API",
			description = "This REST API is used to fetch all categories from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		List<CategoryDto> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}

	@Operation(
			summary = "Update Category REST API",
			description = "This REST API is used to update a perticulat category in database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name="Bearer Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long id) {
		CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, id);
		return ResponseEntity.ok(updatedCategory);
	}

	@Operation(
			summary = "Delete Category REST API",
			description = "This REST API is used to delete a perticulat category from database"
	)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 SUCCESS"
	)
	@SecurityRequirement(
			name="Bearer Authentication"
	)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.ok("Deleted");
	}
}
