package in.sekhar.service;

import java.util.List;

import in.sekhar.payload.CategoryDto;

public interface CategoryService {

	CategoryDto addCategory(CategoryDto categoryDto);
	
	CategoryDto getCategory(Long id);
	
	List<CategoryDto> getAllCategories();
	
	CategoryDto updateCategory(CategoryDto categoryDto,Long id);
	
	void deleteCategory(Long id);
}
